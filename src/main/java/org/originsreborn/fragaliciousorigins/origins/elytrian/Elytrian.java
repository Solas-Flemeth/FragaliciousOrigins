package org.originsreborn.fragaliciousorigins.origins.elytrian;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PlayerUtils;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;
//todo: Make checstpalte disappear on removal
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

    public Elytrian(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ELYTRIAN, state, customDataString);
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if (PlayerUtils.isUnderRoof(player, ELYTRIAN_CONFIG.getCeilingPenaltyHeight())) {
            PotionsUtil.addEffect(player, PotionEffectType.SLOWNESS, ELYTRIAN_CONFIG.getCeilingPenaltySlownessAmplifier(), 4);
        }
    }

    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateParticleAtLocation(Particle.WHITE_ASH, getPlayer().getLocation(), 2);
    }

    @Override
    public String serializeCustomData() {
        HashMap<String, Serializable> hashMap = new HashMap<>();
        hashMap.put("PrimaryCooldown", getPrimaryCooldown());
        hashMap.put("SecondaryCooldown", getSecondaryCooldown());
        try {
            return SerializationUtils.serializeHashMapToString(hashMap);
        } catch (IOException ignored) {
            return "";
        }
    }

    @Override
    public void deserializeCustomData(String customData) {
        try {
            HashMap<String, Serializable> hashMap = SerializationUtils.unserializeStringToHashMap(customData);
            setPrimaryCooldown((Integer) hashMap.get("PrimaryCooldown"));
            setSecondaryCooldown((Integer) hashMap.get("SecondaryCooldown"));
        } catch (Exception ignored) {
            //will use default values
        }
    }

    @Override
    public void setDefaultStats() {
        super.setDefaultStats();
        getPlayer().getInventory().setChestplate(getElytra());
        updateArmor();
    }

    @Override
    public void onToggleGlide(EntityToggleGlideEvent event) {
        updateGlideAttributes(!event.isGliding());
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
            locationOffset.setY(location.getY() + velocityMultiplier*2.0);
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

    }

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.MEDIUM;
    }

    @Override
    public double getDodgeChance() {
        if (getPlayer().isGliding()) {
            return ELYTRIAN_CONFIG.getGlidingDodgeChance();
        } else {
            return MAIN_ORIGIN_CONFIG.getDodgeChance();
        }
    }

    private void updateGlideAttributes(boolean gliding) {
        Player player = getPlayer();
        if (gliding) {
            PlayerUtils.setAttribute(player, Attribute.PLAYER_BLOCK_BREAK_SPEED, ELYTRIAN_CONFIG.getGlidingBlockBreakSpeed());
            PlayerUtils.setAttribute(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, ELYTRIAN_CONFIG.getGlidingEntityInteractRange());
            PlayerUtils.setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, ELYTRIAN_CONFIG.getGlidingBlockInteractRange());
            PlayerUtils.setAttribute(player, Attribute.GENERIC_ATTACK_SPEED, ELYTRIAN_CONFIG.getGlidingAttackSpeed());
            PlayerUtils.setAttribute(player, Attribute.GENERIC_ATTACK_DAMAGE, ELYTRIAN_CONFIG.getGlidingAttackDamage());
        } else {
            PlayerUtils.setAttribute(player, Attribute.PLAYER_BLOCK_BREAK_SPEED, MAIN_ORIGIN_CONFIG.getBlockBreakSpeed());
            PlayerUtils.setAttribute(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getPlayerEntityInteractRange());
            PlayerUtils.setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getBlockInteractRange());
            PlayerUtils.setAttribute(player, Attribute.GENERIC_ATTACK_SPEED, MAIN_ORIGIN_CONFIG.getAttackSpeed());
            PlayerUtils.setAttribute(player, Attribute.GENERIC_ATTACK_DAMAGE, MAIN_ORIGIN_CONFIG.getAttackDamage());
        }
    }

    private void updateArmor() {
        Player player = getPlayer();
        double armor = PlayerUtils.getAttribute(player, Attribute.GENERIC_ARMOR);
        armor -= ELYTRIAN_CONFIG.getArmorPenaltyMinArmorToApply();
        if (armor > 0.0) {
            PlayerUtils.setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, 1.0 - (armor * ELYTRIAN_CONFIG.getArmorPenaltySlownessPerArmor()));
        } else {
            PlayerUtils.setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, 1.0);
        }
    }

    private ItemStack getElytra() {
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta elytraMeta = elytra.getItemMeta();
        elytraMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("elytra_armor", ELYTRIAN_CONFIG.getElytraArmor(), AttributeModifier.Operation.ADD_NUMBER));
        elytraMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("elytra_toughness", ELYTRIAN_CONFIG.getElytraToughness(), AttributeModifier.Operation.ADD_NUMBER));
        elytraMeta.setHideTooltip(true);
        elytraMeta.setUnbreakable(true);
        elytraMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        elytraMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
        elytraMeta.setCustomModelData(ELYTRIAN_CONFIG.getElytraModelData());
        elytra.setItemMeta(elytraMeta);
        return elytra;
    }
}
