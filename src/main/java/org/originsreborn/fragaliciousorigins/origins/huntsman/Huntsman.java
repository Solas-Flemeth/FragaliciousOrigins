package org.originsreborn.fragaliciousorigins.origins.huntsman;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.SerializationUtils;
import org.originsreborn.fragaliciousorigins.util.enums.Food;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Primary Ability - Fade - Turns invisible and next shot deals 3.5x Damage  and inflicts wither for 2 seconds - will uninvis afterward. Last 6 seconds.
 * Secondary Ability - Toggle arrow Type
 * - Standard - 1.5x Damage
 * - Tracking - 1.1x Damage + Applies glow (10 second)
 * - Stun Arrow - 0.7x Damage - applies slowness II (4 second) +  darkness (1.5s)
 * - Bows - Gains 1.5x Effective range.
 * - Crossbows - penetration - deals bonus toughness damage
 * - Swings slow with melee weapons and deals less damage
 * - Gains a speed boost on taking damage
 * - Cannot use explosive crossbows
 * - Crouching gives invis, slowness, and slowfall
 * - Carnivore
 * - Deals 1.25x Damage to targets in the air and 2.5x to gliding
 * - Bonus archery xp
 */
public class Huntsman extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.HUNTSMAN);
    public static final HuntsmanConfig HUNTSMAN_CONFIG = new HuntsmanConfig();
    private int invisDuration = 0;
    private HuntsmanMode mode = HuntsmanMode.STANDARD;

    public Huntsman(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.HUNTSMAN, state, customDataString);
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        HUNTSMAN_CONFIG.loadConfig();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if (getPlayer().isSneaking()) {
            PotionsUtil.addEffect(player, PotionEffectType.INVISIBILITY, 0, HUNTSMAN_CONFIG.getCrouchDuration());
            PotionsUtil.addEffect(player, PotionEffectType.SLOW_FALLING, 0, HUNTSMAN_CONFIG.getCrouchDuration());
            PotionsUtil.addEffect(player, PotionEffectType.SLOWNESS, 1, HUNTSMAN_CONFIG.getCrouchDuration());
        }
        if (tickNum % 10 == 0) {
            if (invisDuration != 0) {
                if(invisDuration == 1){
                    primaryAbilityAnimation();
                }
                setInvisDuration(invisDuration - 1);
                PotionsUtil.addEffect(player, PotionEffectType.INVISIBILITY, 0, 25);
            }
        }
    }

    @Override
    public void originParticle(int tickNum) {
        //ParticleUtil.generateParticleAtLocation(Particle.WARPED_SPORE, getPlayer().getLocation(), 1);
        if(!getPlayer().isSneaking()){
            ParticleUtil.generateParticleAtLocation(Particle.SPORE_BLOSSOM_AIR, getPlayer().getLocation(), 2);
        }

    }


    @Override
    public void primaryAbilityLogic() {
        primaryAbilityAnimation();
        setInvisDuration(HUNTSMAN_CONFIG.getPrimaryAbilityDuration());
    }

    @Override
    public void secondaryAbilityLogic() {
        toggleMode();
        getPlayer().sendMessage("Arrow Type:" + mode.getMode());
    }


    @Override
    public String serializeCustomData() {
        HashMap<String, Serializable> hashMap = new HashMap<>();
        hashMap.put("PrimaryCooldown", getPrimaryCooldown());
        hashMap.put("SecondaryCooldown", getSecondaryCooldown());
        hashMap.put("InvisDuration", getInvisDuration());
        hashMap.put("Mode", mode.getMode());
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
            setInvisDuration((Integer) hashMap.get("InvisDuration"));
            setMode(HuntsmanMode.getMode((String) hashMap.get("Mode")));
        } catch (Exception ignored) {
            //will use default values
        }
    }

    @Override
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getProjectile().getType().equals(EntityType.FIREWORK_ROCKET)) {
            event.getEntity().sendMessage("You cannot shoot rockets as " + OriginType.HUNTSMAN.name());
            event.setCancelled(true);
            return;
        }
        if(getInvisDuration() > 0){
            setInvisDuration(0);
            primaryAbilityAnimation();
            PotionsUtil.removeEffect(getPlayer(), PotionEffectType.INVISIBILITY);
            event.getProjectile().setMetadata("huntsman_damage_multiplier", new FixedMetadataValue(FragaliciousOrigins.INSTANCE, "Ability"));
        }else{
            event.getProjectile().setMetadata("huntsman_damage_multiplier", new FixedMetadataValue(FragaliciousOrigins.INSTANCE, mode.getMode()));
            if (event.getBow().getType().equals(Material.BOW)) {
                Vector modifiedVector = event.getProjectile().getVelocity().multiply(HUNTSMAN_CONFIG.getOtherBowVelocityMultiplier());
                event.getProjectile().setVelocity(modifiedVector);
            } else if (event.getBow().getType().equals(Material.CROSSBOW)) {
                event.getProjectile().setMetadata("huntsman_toughness_Multiplier", new FixedMetadataValue(FragaliciousOrigins.INSTANCE, true));
            }
        }
    }
    @Override
    public void onDamage(EntityDamageEvent event){
        if(!event.isCancelled()){
            if(event.getFinalDamage() != 0.0){
                PotionsUtil.addEffect(getPlayer(), PotionEffectType.SPEED, HUNTSMAN_CONFIG.getSpeedOnDamageAmplifier(), HUNTSMAN_CONFIG.getSpeedOnDamageDuration());
            }
        }
    }

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.MEDIUM;
    }
    @Override
    public void consume(PlayerItemConsumeEvent event){
        Food food = Food.getFood(event.getItem().getType());
        if(food != null && !food.isMeat()){
            event.getPlayer().sendMessage("You can only eat meat based foods.");
            event.setCancelled(true);
        }
    }
    public int getInvisDuration() {
        return invisDuration;
    }

    public void setInvisDuration(int invisDuration) {
        if (invisDuration < 0) {
            invisDuration = 0;
        }
        this.invisDuration = invisDuration;
    }


    public void setMode(HuntsmanMode mode) {
        this.mode = mode;
    }

    public void toggleMode() {
        switch (mode) {
            case STUN -> setMode(HuntsmanMode.STANDARD);
            case STANDARD -> setMode(HuntsmanMode.TRACING);
            default -> setMode(HuntsmanMode.STUN);
        }
    }
    public static double getDamageMultiplier(String mode){
        return switch (mode) {
            case "Tracking" -> HUNTSMAN_CONFIG.getTrackingArrowDuration();
            case "Stun" -> HUNTSMAN_CONFIG.getStunArrowDamageMultiplier();
            case "Ability" -> HUNTSMAN_CONFIG.getPrimaryAbilityDamageMultiplier();
            default -> HUNTSMAN_CONFIG.getBroadArrowDamageMultiplier();
        };
    }
    private void primaryAbilityAnimation(){
        getPlayer().getWorld().playSound(getPlayer().getLocation(),Sound.ENTITY_HORSE_BREATHE, 1, 0.5f);
        ParticleUtil.generateSphereParticle(Particle.CAMPFIRE_COSY_SMOKE, getPlayer().getEyeLocation(),60,1.5);
    }
}
