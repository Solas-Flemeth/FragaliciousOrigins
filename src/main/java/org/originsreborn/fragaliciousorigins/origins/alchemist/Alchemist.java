package org.originsreborn.fragaliciousorigins.origins.alchemist;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static org.bukkit.potion.PotionEffectType.*;

/**
 *  - primary - enchant XP Boost + reroll enchant KEY  <-- Needs tested
 *  - Secondary - Shift potion type  <-- Needs tested
 *    - Redstone - Increase potion duration
 *    - Glowstone - Increase potion amplifier
 *    - Spider Eye - Changes all negative effects to positive
 *  - Keeps XP on death <-- needs tested
 *  - Romeo & Juliet - cannot trade with villagers <-- needs tested
 *  - Chance to keep XP on enchanting <-- Needs tested
 *  - 15% damage boost with explosives (crossbows)  <-- Needs tested
 *  - Potion arrows now splash cause lingering AOEs <-- needs tested
 *  - Splash potions are higher intensity <-- needs Tested
 *  - Lingering Potions last longer and are bigger <-- needs tested
 *  - chance for 2x xp
 */
public class Alchemist extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ALCHEMIST);
    public CatalystMode mode;
    public static final AlchemistConfig ALCHEMIST_CONFIG = new AlchemistConfig();
    public Alchemist(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ALCHEMIST, state, customDataString);
        if(mode == null){
            mode = CatalystMode.SPIDER_EYE;
        }
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {

    }

    /**
     * @param map
     * @return
     */
    @Override
    public @NotNull HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        map.put("catalystMode", mode.getMode());
        return map;
    }

    /**
     * @param map
     * @throws Exception
     */
    @Override
    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {
        mode = CatalystMode.getMode((String) map.get("catalystMode"));
    }

    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateParticleAtLocation(Particle.ENCHANT, getPlayer().getLocation(),2);
    }

    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        player.setEnchantmentSeed(new Random().nextInt(1000000000));
        player.giveExp(ALCHEMIST_CONFIG.getExpierenceGained());
        ParticleUtil.generateSphereParticle(Particle.ENCHANT, getPlayer().getLocation(),50, 2.5);
        ParticleUtil.generateSphereParticle(Particle.END_ROD, player.getLocation(), 20, 2.0);
        ParticleUtil.generateCircleParticle(Particle.PORTAL, player.getLocation(), 20, 1.5);
        PotionsUtil.addEffect(player, GLOWING, 0, 60);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 0.5f, 2);
    }

    @Override
    public void secondaryAbilityLogic() {
        mode = CatalystMode.getNextMode(mode);
        Player player = getPlayer();
        player.sendActionBar(Component.text("All your effects will now use the catalyst ").color(Origin.enableColor())
                .append(Component.text(mode.getMode()).color(mode.getColor())));
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1f, 1.5f);
    }

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.HARD;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        ALCHEMIST_CONFIG.loadConfig();
    }

    /**
     * @param event
     */
    @Override
    public void onDeath(PlayerDeathEvent event) {
        super.onDeath(event);
        event.setKeepLevel(true);
        event.setDroppedExp(0);
    }

    /**
     * @param event
     */
    @Override
    public void onOpenInventory(InventoryOpenEvent event) {
        super.onOpenInventory(event);
        if(event.getInventory().getType().equals(InventoryType.MERCHANT)){
            event.setCancelled(true);
            getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 2f, 1);
            getPlayer().sendActionBar(Component.text("They do not want to trade with you").color(Origin.errorColor()));
        }
    }

    /**
     * @param event
     */
    @Override
    public void experienceChange(PlayerExpChangeEvent event) {
        super.experienceChange(event);
        if(Math.random() < ALCHEMIST_CONFIG.getDoubleXPChance()){
            event.setAmount(event.getAmount() * 2);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onShootProjectileHit(ProjectileHitEvent event) {
        super.onShootProjectileHit(event);
        if(event.getEntity() instanceof Arrow arrow){
            System.out.println("SOLAS SHOT AN ARROW");
            if(arrow.getBasePotionType() != null){
                System.out.println("SOLAS SHOT AN ARROW WITH POTION EFFECTS");
                createAreaEffectCloudAtSpot(arrow);
            }
        }
    }

    /**
     * @param event
     */
    @Override
    public void onAnvilClick(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.ANVIL
                || event.getSlotType() != InventoryType.SlotType.RESULT
                || !event.isLeftClick()
                || !(event.getWhoClicked() instanceof Player player)
        ){return;}
        if (Math.random() < ALCHEMIST_CONFIG.getRepairFreeChance()) {
            AnvilInventory anvilInventory = (AnvilInventory) event.getInventory();
            player.setExp(player.getExp() + anvilInventory.getRepairCost());
            // Optionally, send a message to the player
            ParticleUtil.generateSphereParticle(Particle.END_ROD, player.getLocation(), 20, 2.0);
            ParticleUtil.generateParticleAtLocation(Particle.ENCHANT, player.getLocation(), 20);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2);
            player.sendActionBar(Component.text("Your alchemical background allowed you to use the anvil without using experience").color(Origin.enableColor()));
        }
    }
    public void createAreaEffectCloudAtSpot(Arrow arrow){
        // Create an AreaEffectCloud at the specified location
        AreaEffectCloud cloud = arrow.getLocation().getWorld().spawn(arrow.getLocation(), AreaEffectCloud.class);
        // Apply the PotionEffect to the AreaEffectCloud
        cloud.clearCustomEffects();
        for(PotionEffect effect : arrow.getBasePotionType().getPotionEffects()){
            cloud.addCustomEffect(effect, false);
        }
        for(PotionEffect effect :arrow.getCustomEffects()){
            cloud.addCustomEffect(effect, false);
        }
        // Set the duration of the cloud (in ticks)
        cloud.setDuration(ALCHEMIST_CONFIG.getArrowDuration()); // Adjust as needed
        if(arrow.getColor() != null){
            cloud.setColor(arrow.getColor());
        }
        // Set the radius of the effect cloud
        cloud.setRadius(ALCHEMIST_CONFIG.getArrowRadius()); // Adjust as needed
        cloud.setSource(arrow.getShooter());
    }


    /**
     * @param event
     */
    @Override
    public void onEnchant(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        if (Math.random() < ALCHEMIST_CONFIG.getEnchantmentFreeChance()) {
            int expCost = event.getExpLevelCost();
            // Refund the experience to the player
            player.giveExpLevels(expCost);
            // Optionally, send a message to the player
            ParticleUtil.generateSphereParticle(Particle.END_ROD, player.getLocation(), 20, 2.0);
            ParticleUtil.generateParticleAtLocation(Particle.ENCHANT, player.getLocation(), 20);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2);
            player.sendActionBar(Component.text("Your alchemical background allowed you to enchant without using experience").color(Origin.enableColor()));
        }
    }

    /**
     * @param event
     */
    @Override
    public void onBowShoot(EntityShootBowEvent event) {
        super.onBowShoot(event);
        if(event.getProjectile() instanceof Arrow){
            Vector modifiedVector = event.getProjectile().getVelocity().multiply(ALCHEMIST_CONFIG.getArrowVelocity());
            event.getProjectile().setVelocity(modifiedVector);
        }else if(event.getProjectile() instanceof Firework firework){
            Vector modifiedVector = firework.getVelocity().multiply(ALCHEMIST_CONFIG.getExplosionVelocity());
            firework.setVelocity(modifiedVector);
            firework.setGlowing(true);
            firework.setTicksToDetonate((int) (firework.getTicksToDetonate()/ALCHEMIST_CONFIG.getExplosionVelocity()));
        }


    }

    /**
     * @param event
     */
    @Override
    public void onPotionLaunch(ProjectileLaunchEvent event) {
        Entity entity = event.getEntity();
        entity.setVelocity(entity.getVelocity().multiply(ALCHEMIST_CONFIG.getPotionVelocityMultiplier()));
    }

    /**
     * @param event
     */
    @Override
    public void onPotionEffect(EntityPotionEffectEvent event) {
        if(event.getEntity().hasMetadata("alchemistPotion")){
            event.getEntity().removeMetadata("alchemistPotion", FragaliciousOrigins.INSTANCE);
            return;
        }
        if(event.getAction().equals(EntityPotionEffectEvent.Action.ADDED)){
            PotionEffect potionEffect = event.getNewEffect();
            PotionEffectType effectType = potionEffect.getType();
            int duration = potionEffect.getDuration();
            int amplifier = potionEffect.getAmplifier();
            switch (mode){
                case GLOWSTONE:
                    duration = (int) (duration * ALCHEMIST_CONFIG.getGlowstoneDurationMultiplier());
                    amplifier = amplifier + ALCHEMIST_CONFIG.getGlowstoneAmplifierChange();
                    break;
                case REDSTONE:
                    duration = (int) (duration * ALCHEMIST_CONFIG.getRedstoneDurationMultiplier());
                    amplifier = amplifier + ALCHEMIST_CONFIG.getRedstoneAmplifierChange();
                    break;
                case SPIDER_EYE:
                    duration = (int) (duration * ALCHEMIST_CONFIG.getSpiderEyeDurationMultiplier());
                    amplifier = amplifier + ALCHEMIST_CONFIG.getSpiderEyeAmplifierChange();
                    effectType = getPositiveOpposite(effectType);
                    break;
            }
            PotionEffect newPotionEffect = potionEffect.withType(effectType);
            newPotionEffect = newPotionEffect.withDuration(duration);
            newPotionEffect = newPotionEffect.withAmplifier(amplifier);
            event.getEntity().setMetadata("alchemistPotion",new FixedMetadataValue(FragaliciousOrigins.INSTANCE,potionEffect.toString()));
            getPlayer().addPotionEffect(newPotionEffect);
            event.setCancelled(true);
        }
    }



    /**
     * @param event
     */
    @Override
    public void onPotionSplash(PotionSplashEvent event) {
        float multiplier = ALCHEMIST_CONFIG.getSplashRadiusModifier();
        for(LivingEntity entity : event.getAffectedEntities()){
            event.setIntensity(entity, event.getIntensity(entity) * multiplier);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onPotionLingering(LingeringPotionSplashEvent event) {
        AreaEffectCloud cloud = event.getAreaEffectCloud();
        float modifer = ALCHEMIST_CONFIG.getLingeringRadiusModifier();
        float radius = cloud.getRadius() * modifer;
        float radiusOnUse = cloud.getRadiusOnUse() * modifer;
        float radiusPerTick = cloud.getRadiusPerTick() * modifer;
        int duration = (int) (cloud.getDuration() * ALCHEMIST_CONFIG.getLingeringDurationModifier());
        int durationOnUse = (int) (cloud.getDurationOnUse() * ALCHEMIST_CONFIG.getLingeringDurationModifier());
        event.getAreaEffectCloud().setRadius(radius);
        event.getAreaEffectCloud().setRadiusPerTick(radiusPerTick);
        event.getAreaEffectCloud().setRadiusOnUse(radiusOnUse);
        event.getAreaEffectCloud().setDuration(duration);
        event.getAreaEffectCloud().setDurationOnUse(durationOnUse);
    }


    private static PotionEffectType getPositiveOpposite(PotionEffectType type){
        if (type.equals(BAD_OMEN)) {
            return HERO_OF_THE_VILLAGE;
        } else if (type.equals(WITHER) || type.equals(POISON)) {
            return REGENERATION;
        } else if (type.equals(SLOWNESS)) {
            return SPEED;
        } else if (type.equals(INSTANT_DAMAGE)) {
            return INSTANT_HEALTH;
        } else if (type.equals(WEAKNESS)) {
            return STRENGTH;
        } else if (type.equals(HUNGER)) {
            return SATURATION;
        } else if (type.equals(MINING_FATIGUE)) {
            return HASTE;
        } else if (type.equals(BLINDNESS)) {
            return NIGHT_VISION;
        } else if (type.equals(DARKNESS)) {
            return GLOWING;
        }
        return type;
    }
}
