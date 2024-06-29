package org.originsreborn.fragaliciousorigins.origins.merling;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import static org.originsreborn.fragaliciousorigins.util.PlayerUtils.setAttribute;

/**
 * Primary Ability - Toggle Swim Speed
 * Secondary Ability - Toggle gravity in water
 * - Slower mine speed on surface but faster mine speed in water
 * - Being wet grants a 20% chance to avoid death and restore health to full on a killing blow
 * - Tridents deal bonus damage
 * - Takes bonus damage from fire and burns 2x longer
 * - Must be in water or will suffocate. Waterbreathing and respiration increase duration without water.
 * - Can refill water with water bottles, rain, or stepping in water.
 * - Nearby players get water breathing if in water and the merling is in water for 5 seconds (ticks every 3 seconds)
 * - Bonus Luck
 */
public class Merling extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.MERLING);
    public static final MerlingConfig MERLING_CONFIG = new MerlingConfig();
    private static final BossBar.Color HYDRATION_COLOR = BossBar.Color.BLUE;
    private static final BossBar.Overlay HYDRATION_OVERLAY = BossBar.Overlay.PROGRESS;
    private final String HYDRATION_KEY;
    private Integer hydration;
    private boolean dehydrationDeath = false;

    public Merling(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.MERLING, state, customDataString);
        HYDRATION_KEY = getUUID().toString() + "_HYDRATION";
        if (hydration == null) {
            hydration = MERLING_CONFIG.getHydrationDuration();
        }
        FragaliciousOrigins.BOSS_BARS.createBossBar(HYDRATION_KEY, getHydrationComponent(), getHydrationPercentage(), HYDRATION_COLOR, HYDRATION_OVERLAY, getPlayer());

    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        MERLING_CONFIG.loadConfig();
    }

    /**
     * @param map
     * @return
     */
    @Override
    public @NotNull HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        map.put("hydration", getHydration());
        return map;
    }

    /**
     * @param map
     * @throws Exception
     */
    @Override
    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {
        setHydration((Integer) map.get("hydration"));
    }

    /**
     * @param event
     */
    @Override
    public void onRespawn(PlayerRespawnEvent event) {
        super.onRespawn(event);
        setHydration(MERLING_CONFIG.getHydrationDuration());
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if (player.isInWater()) {
            if (isSecondaryEnabled()) {
                PotionsUtil.addEffect(player, PotionEffectType.DOLPHINS_GRACE, MERLING_CONFIG.getWaterAttributesDolphinsGraceAmplifier(), 19);
            }
            PotionsUtil.addEffect(player, PotionEffectType.CONDUIT_POWER, 2, 19);
            if (player.hasPotionEffect(PotionEffectType.MINING_FATIGUE)) {
                player.removePotionEffect(PotionEffectType.MINING_FATIGUE);
            }
            enterWater();
        } else if (!player.isInWater()) {
            exitWater();
        }
        if (tickNum % 10 == 0) { //every second
            //passive
            if (getPlayer().isInWaterOrRain()) {
                setHydration(MERLING_CONFIG.getHydrationDuration());
            } else {
                double breathCancelChance = 0.0;
                ItemStack helmet = player.getEquipment().getHelmet();
                if (helmet != null && helmet.containsEnchantment(Enchantment.RESPIRATION)) {
                    breathCancelChance += (MERLING_CONFIG.getRespirationChance() * ((double) helmet.getEnchantmentLevel(Enchantment.RESPIRATION)));
                }
                if (player.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
                    breathCancelChance += MERLING_CONFIG.getWaterBreathingPotionChance();
                }
                if (Math.random() > breathCancelChance) {
                    setHydration(getHydration() - 1);
                }
            }
            if (player.getFireTicks() > 1) {
                setHydration(getHydration() - 3);
            }
            if (getHydration() <= 0) {
                player.damage(0.01); //make the player flinch
                if (player.getHealth() < 1.0) {
                    player.setHealth(0.0);
                    dehydrationDeath = true;
                } else {
                    player.setHealth(player.getHealth() - 1);
                }
                player.playSound(player, Sound.BLOCK_FIRE_EXTINGUISH, 1, 0.8f);
            }
            FragaliciousOrigins.BOSS_BARS.updateBossBar(HYDRATION_KEY, getHydrationComponent(), getHydrationPercentage());
        }
        if (tickNum % 30 == 0) { //3 seconds
            if (isSecondaryEnabled() && player.isInWater()) {
                int range = MERLING_CONFIG.getSecondaryAbilityRange();
                for (Entity entity : player.getNearbyEntities(range, range, range)) {
                    if (entity instanceof Player nearbyPlayer && nearbyPlayer.isInWaterOrRain()) {
                        PotionsUtil.addEffect(nearbyPlayer, PotionEffectType.WATER_BREATHING, 0, MERLING_CONFIG.getSecondaryAbilityPotionDuration());
                        PotionsUtil.addEffect(nearbyPlayer, PotionEffectType.DOLPHINS_GRACE, MERLING_CONFIG.getSecondaryAbilityDolphinsGraceAmplifier(), MERLING_CONFIG.getSecondaryAbilityPotionDuration());
                    }
                }
            }
        }
    }

    /**
     *
     */
    @Override
    public void onRemoveOrigin() {
        FragaliciousOrigins.BOSS_BARS.removeBossBar(HYDRATION_KEY);
    }

    /**
     * @param event
     */
    @Override
    public void onMove(PlayerMoveEvent event) {
        super.onMove(event);
        Player player = event.getPlayer();
        if (isPrimaryEnabled() && player.isSwimming() && player.isInWater()) {
            player.setVelocity(player.getLocation().getDirection().multiply(MERLING_CONFIG.getSwimMultiplier()));
        }
    }

    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateParticleAtLocation(Particle.BUBBLE_COLUMN_UP, getPlayer().getLocation(), 1);
    }

    @Override
    public void primaryAbilityLogic() {
        primaryToggle();
    }

    @Override
    public void secondaryAbilityLogic() {
        secondaryToggle();
    }

    /**
     * @param event
     */
    @Override
    public void onDeath(PlayerDeathEvent event) {
        Player player = getPlayer();
        if (player.isInWaterOrRain() && Math.random() < MERLING_CONFIG.getDeathAvoidChance()) {
            event.setCancelled(true);
            PotionsUtil.addEffect(player, PotionEffectType.GLOWING, 0, 40);
            PotionsUtil.addEffect(player, PotionEffectType.WEAKNESS, 1, 100);
            PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, 2, 100);
            PotionsUtil.addEffect(player, PotionEffectType.SLOWNESS, 1, 100);
            PotionsUtil.addEffect(player, PotionEffectType.SATURATION, 0, 100);
            PotionsUtil.addEffect(player, PotionEffectType.INSTANT_HEALTH, 0, 10);
            player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 2, 1.5f);
            ParticleUtil.generateSphereParticle(Particle.BUBBLE, player.getLocation(), 50, 2);
            player.sendActionBar(Component.text("The water has blessed you with another chance at life").color(enableColor()));
        } else {
            if (dehydrationDeath) {
                dehydrationDeath = false;
                event.deathMessage(player.displayName().append(Component.text(" shriveled up due to no water")));
            }
            super.onDeath(event);
        }

    }

    /**
     * @param event
     */
    @Override
    public void onTridentLaunch(ProjectileLaunchEvent event) {
        Vector velocity = event.getEntity().getVelocity().multiply(MERLING_CONFIG.getTridentVelocityMultiplier());
        event.getEntity().setVelocity(velocity);
    }

    /**
     * @param event
     */
    @Override
    public void onAttackEntity(EntityDamageByEntityEvent event) {
        Player player = getPlayer();
        ItemStack handItem = player.getEquipment().getItemInMainHand();
        if (handItem.getType().equals(Material.TRIDENT)) {
            event.setDamage(event.getDamage() * MERLING_CONFIG.getTridentMeleeMultiplier());
        }
        super.onAttackEntity(event);
    }

    /**
     * @return
     */
    @Override
    public double getDodgeChance() {
        if (getPlayer().isInWaterOrRain()) {
            return MERLING_CONFIG.getWaterAttributesDodgeChance();
        }
        return super.getDodgeChance();
    }

    /**
     * @param event
     */
    @Override
    public void consume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.POTION)) {
            setHydration(MERLING_CONFIG.getHydrationDuration());
        }
        super.consume(event);
    }

    public int getHydration() {
        return hydration;
    }

    public void setHydration(int hydration) {
        if (hydration < 0) {
            hydration = 0;
        } else if (hydration > MERLING_CONFIG.getHydrationDuration()) {
            hydration = MERLING_CONFIG.getHydrationDuration();
        }
        this.hydration = hydration;
    }

    public void enterWater() {
        Player player = getPlayer();
        player.setGravity(!isSecondaryEnabled());
        setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, MERLING_CONFIG.getBlockInteractRange());
        setAttribute(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, MERLING_CONFIG.getEntityInteractRange());
    }

    public void exitWater() {
        Player player = getPlayer();
        player.setGravity(true);
        setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getBlockInteractRange());
        setAttribute(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getPlayerEntityInteractRange());
    }

    private float getHydrationPercentage() {
        float current = getHydration();
        float max = MERLING_CONFIG.getHydrationDuration();
        float percentage = current / max;
        if (percentage < 0.0) {
            return 0;
        } else if (percentage > 1.0f) {
            return 1.0f;
        } else {
            return percentage;
        }
    }

    private Component getHydrationComponent() {
        return Component.text("Hydration " + formatFloatPercentage(getHydrationPercentage())).color(TextColor.color(0x9CF5));
    }


}
