package org.originsreborn.fragaliciousorigins.origins.elytrian;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PlayerUtils;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

import java.util.List;
import java.util.UUID;

/**
 * Primary Ability - Boost - flies off the ground or boost natural speed
 * Secondary Ability - Toggle Wings - turns wings on and off (Will delete current chestplate)
 * - Slowness under low roofs
 * - slower attack speed on ground
 * - increase impact damage
 * - has unbreakable elytra (removes on origin change)
 * - reduce fall damage (20%?)
 * - Major increase to attack range, build range, attack speed and attack damage while gliding
 * - chance to dodge attacks while gliding (20%)
 * - Lower gravity (80%)
 */
public class Elytrian extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ELYTRIAN);
    public static final ElytrianConfig ELYTRIAN_CONFIG = new ElytrianConfig();
    private static final BossBar.Color CHARGE_COLOR = BossBar.Color.WHITE;
    private static final BossBar.Overlay CHARGE_OVERLAY = BossBar.Overlay.PROGRESS;
    private final String CHARGE_KEY;
    private float charge = 0;
    private float maxCharge = 0;

    public Elytrian(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ELYTRIAN, state, customDataString);
        CHARGE_KEY = getPlayer().getUniqueId() + "_CHARGE";
        generateMaxCharge();
        updateArmor();
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        ELYTRIAN_CONFIG.loadConfig();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if (PlayerUtils.isUnderRoof(player, ELYTRIAN_CONFIG.getCeilingPenaltyHeight())) {
            PotionsUtil.addEffect(player, PotionEffectType.SLOWNESS, ELYTRIAN_CONFIG.getCeilingPenaltySlownessAmplifier(), 10);
        }
        if (getPlayer().isGliding()) {
            if (player.isSneaking()) {
                updateCharge();
            } else if (charge > 0) {
                releaseCharge();
            }
        } else if (charge > 0) {
            generateMaxCharge();
            charge = 0;
            FragaliciousOrigins.BOSS_BARS.removeBossBar(CHARGE_KEY);
        }
    }

    @Override
    public void originParticle(int tickNum) {
        if(tickNum%3 == 0){
            ParticleUtil.generateSphereParticle(Particle.CLOUD, getPlayer().getLocation(), 2, MAIN_ORIGIN_CONFIG.getScale());
        }
        if(tickNum%4==0){
            ParticleUtil.generateSphereParticle(Particle.END_ROD, getPlayer().getLocation(), 2, MAIN_ORIGIN_CONFIG.getScale());
        }
    }


    @Override
    public void setDefaultStats() {
        super.setDefaultStats();
        toggleChestplate();
        updateArmor();

    }

    /**
     * @param event
     */
    @Override
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) && event.getEntity() instanceof Player player) {
            double damage = event.getFinalDamage();
            if (damage > player.getHealthScale() || player.getHealth() == 0) {
                return;
            }
            double velocityRadius = damage * ELYTRIAN_CONFIG.getKnockbackRadius();
            double velocityPower = damage * ELYTRIAN_CONFIG.getKnockbackVelocityPerDamage();
            Location playerLocation = player.getLocation();
            List<Entity> entities = player.getNearbyEntities(velocityRadius, velocityRadius, velocityRadius);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.getLocation();
                    Vector direction = getVector(playerLocation, livingEntity.getLocation().toBlockLocation());
                    livingEntity.setVelocity(direction.multiply(velocityPower));
                }
            }
            getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 0.5f);
            ParticleUtil.generateSphereParticle(Particle.EXPLOSION, getPlayer().getLocation(), 30, velocityRadius);
            ParticleUtil.generateSphereParticle(Particle.CLOUD, getPlayer().getLocation(), 3, 0.5);
        }
        super.onDamage(event);
    }

    @Override
    public void onToggleGlide(EntityToggleGlideEvent event) {
        Player player = getPlayer();
        if (event.isGliding()) {
            PlayerUtils.setAttribute(player, Attribute.BLOCK_BREAK_SPEED, ELYTRIAN_CONFIG.getGlidingBlockBreakSpeed());
            PlayerUtils.setAttribute(player, Attribute.ENTITY_INTERACTION_RANGE, ELYTRIAN_CONFIG.getGlidingEntityInteractRange());
            PlayerUtils.setAttribute(player, Attribute.BLOCK_INTERACTION_RANGE, ELYTRIAN_CONFIG.getGlidingBlockInteractRange());
            PlayerUtils.setAttribute(player, Attribute.ATTACK_SPEED, ELYTRIAN_CONFIG.getGlidingAttackSpeed());
            PlayerUtils.setAttribute(player, Attribute.ATTACK_DAMAGE, ELYTRIAN_CONFIG.getGlidingAttackDamage());
        } else {
            PlayerUtils.setAttribute(player, Attribute.BLOCK_BREAK_SPEED, MAIN_ORIGIN_CONFIG.getBlockBreakSpeed());
            PlayerUtils.setAttribute(player, Attribute.ENTITY_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getPlayerEntityInteractRange());
            PlayerUtils.setAttribute(player, Attribute.BLOCK_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getBlockInteractRange());
            PlayerUtils.setAttribute(player, Attribute.ATTACK_SPEED, MAIN_ORIGIN_CONFIG.getAttackSpeed());
            PlayerUtils.setAttribute(player, Attribute.ATTACK_DAMAGE, MAIN_ORIGIN_CONFIG.getAttackDamage());
        }
    }

    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        Location location = player.getLocation();
        double velocityMultiplier = ELYTRIAN_CONFIG.getPrimaryAbilityFlyingVelocityMultiplier();
        double minVelocity = ELYTRIAN_CONFIG.getPrimaryAbilityFlyingMinVelocity();
        if (player.isGliding()) {
            // Get the player's pitch and yaw angles
            float pitch = location.getPitch();
            float yaw = location.getYaw();
            // Calculate the direction vector based on pitch and yaw
            double x = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            double y = -Math.sin(Math.toRadians(pitch));
            double z = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            Vector direction = new Vector(x, y, z).normalize();
            Vector velocity = direction.multiply(velocityMultiplier);
            while (velocity.length() < minVelocity) {
                velocity.multiply(velocityMultiplier);
            }
            // Set the adjusted velocity to the player
            player.setVelocity(velocity);
            ParticleUtil.generateCircleParticle(Particle.CLOUD, location, 30, 1.5);
            ParticleUtil.generateCircleParticle(Particle.SMOKE, location, 30, 1.5);
        } else {
            player.setVelocity(new Vector(0, velocityMultiplier, 0));
            ParticleUtil.generateCircleParticle(Particle.SMOKE, location, 30, 1.5);
            ParticleUtil.generateCircleParticle(Particle.CLOUD, location, 30, 1.5);
            Location locationOffset = location;
            locationOffset.setY(location.getY() + velocityMultiplier * 2.0);
            ParticleUtil.generateParticleLine(Particle.CLOUD, location, locationOffset, 50);
        }
        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 0.8f);
    }

    @Override
    public void onUpdateArmor(PlayerArmorChangeEvent event) {
        updateArmor();
    }

    @Override
    public void secondaryAbilityLogic() {
        secondaryToggle();
        toggleChestplate();
    }


    @Override
    public double getDodgeChance() {
        if (getPlayer().isGliding()) {
            return ELYTRIAN_CONFIG.getGlidingDodgeChance();
        } else {
            return MAIN_ORIGIN_CONFIG.getDodgeChance();
        }
    }

    private void updateArmor() {
        Player player = getPlayer();
        double armor = PlayerUtils.getAttribute(player, Attribute.ARMOR);
        if (!isSecondaryEnabled()) { //wings not spread
            armor = armor - ELYTRIAN_CONFIG.getArmorPenaltyMinArmorToApply();
        }
        if (armor > 0.0) {
            PlayerUtils.setAttribute(player, Attribute.MOVEMENT_SPEED, MAIN_ORIGIN_CONFIG.getMovementSpeed() - (armor * ELYTRIAN_CONFIG.getArmorPenaltySlownessPerArmor()));
        } else {
            PlayerUtils.setAttribute(player, Attribute.MOVEMENT_SPEED, MAIN_ORIGIN_CONFIG.getMovementSpeed());
        }
    }

    /**
     * @param event
     */
    @Override
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        super.onToggleSneak(event);
    }

    private ItemStack getElytra() {
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta elytraMeta = elytra.getItemMeta();
        elytraMeta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(new NamespacedKey("elytra.armor","elytra.armor"), ELYTRIAN_CONFIG.getElytraArmor(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST));
        elytraMeta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, new AttributeModifier(new NamespacedKey("elytra.toughness", "elytra.toughness"), ELYTRIAN_CONFIG.getElytraToughness(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST));
        elytraMeta.setHideTooltip(true);
        elytraMeta.setUnbreakable(true);
        elytraMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        elytraMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        elytraMeta.setCustomModelData(ELYTRIAN_CONFIG.getElytraModelData());
        elytra.setItemMeta(elytraMeta);
        return elytra;
    }


    /**
     *
     */
    @Override
    public void onRemoveOrigin() {
        FragaliciousOrigins.BOSS_BARS.removeBossBar(CHARGE_KEY);
        Player player = getPlayer();
        if (player == null) {
            return;
        }
        ItemStack chestplate = player.getEquipment().getChestplate();
        if (chestplate != null && chestplate.getType().equals(Material.ELYTRA) && chestplate.getItemMeta().isUnbreakable()) {
            player.getEquipment().setChestplate(ItemStack.empty());
        }
    }

    private void toggleChestplate() {
        Player player = getPlayer();
        ItemStack chestplate = player.getEquipment().getChestplate();
        boolean isNull = chestplate == null;
        boolean isElytra = false;
        if(!isNull){
            isElytra = (chestplate.getType().equals(Material.ELYTRA) && chestplate.getItemMeta().isUnbreakable());
        }
        if (!isSecondaryEnabled()) { //giving chestplate
            if (!isNull && !isElytra) { //if wearing chesplate that isnt special elytra
                player.sendActionBar(Component.text("You try to spread you wings but sprain them due to armor constricting them"));
                player.damage(1.0);
                secondaryToggle(true);
            }else{
                player.getInventory().setChestplate(getElytra());
            }
        } else if (isSecondaryEnabled() && isElytra) {  //unequip
            player.getEquipment().setChestplate(ItemStack.empty());
        }
        updateArmor();
    }

    private void releaseCharge() {
        Player player = getPlayer();
        //when the charge is greater than 0, grab the minecraft player's speed and make them move the way their face is looking at the speed of speed * multiplier
        double percentage = Math.pow((charge / maxCharge), 2.0);
        double multiplier = 0.85 + (percentage * ELYTRIAN_CONFIG.getMultiplyPerCharge());
        Vector direction = player.getEyeLocation().getDirection().normalize();
        // Get the player's current velocity
        Vector velocity = player.getVelocity();
        // Scale the movement vector based on the new multiplier
        direction.multiply(multiplier);
        // Update the player's velocity with the new movement vector
        player.setVelocity(direction);
        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_HORSE_BREATHE, 2, 0.8f);
        ParticleUtil.generateCircleParticle(Particle.SMOKE, player.getLocation(), 15, 1.0);
        ParticleUtil.generateCircleParticle(Particle.CLOUD, player.getLocation(), 15, 1.0);
        charge = 0;
        generateMaxCharge();
        FragaliciousOrigins.BOSS_BARS.removeBossBar(CHARGE_KEY);
    }

    private Vector getVector(Location center, Location targetLocation) {
        return targetLocation.toVector().subtract(center.toVector());
    }

    private void updateCharge() {
        charge++;
        if (charge == 1) {
            FragaliciousOrigins.BOSS_BARS.createBossBar(CHARGE_KEY, getChargeComponent(), getChargePercentage(), CHARGE_COLOR, CHARGE_OVERLAY, getPlayer());
        } else if (charge >= maxCharge) {
            this.secondaryToggle(true);
            toggleChestplate();
            getPlayer().getEquipment().setChestplate(ItemStack.empty());
            this.setSecondaryCooldown(getSecondaryMaxCooldown());
            getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 2, 0.8f);
            getPlayer().sendActionBar(Component.text("You feel your wings tear and begin to fall").color(errorColor()));
        } else {
            FragaliciousOrigins.BOSS_BARS.updateBossBar(CHARGE_KEY, getChargeComponent(), getChargePercentage());
        }
    }

    private float getChargePercentage() {
        float current = charge;
        float max = maxCharge - 1f; //subtract one to make it easier for them to predict
        float percentage = current / max;
        if (percentage < 0.0) {
            return 0;
        } else if (percentage > 1.0f) {
            return 1.0f;
        } else {
            return percentage;
        }
    }

    private Component getChargeComponent() {
        return Component.text("Flight Charge " + formatFloatPercentage(getChargePercentage())).color(TextColor.color(0xEEF5CA));
    }

    public void generateMaxCharge() {
        maxCharge = 5f + ((float) (Math.random() * ELYTRIAN_CONFIG.getMaxCharge()));
    }
}
