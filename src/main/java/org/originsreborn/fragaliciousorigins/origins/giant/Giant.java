package org.originsreborn.fragaliciousorigins.origins.giant;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Giant extends Origin {
    /**
     * Primary Ability - Roars - All nearby hostile mobs get weakness and slowness. Nearby players get Strength and resistance
     * 2.5x player size
     * 1.0x speed
     * 1.2x gravity
     * 1.5x Jump height
     * Increase food usage
     * Increase 2x fall height requirement
     * increase 2x jump height
     * 2.5x build size
     * 2.5x attack range
     * Slower attack speed
     * Increase attack damage
     * 2x Hearts
     * 10% Natural Damage Resistance
     * Bonus damage from Fire & explosions
     */
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.GIANT);
    public static final GiantConfig GIANT_CONFIG = new GiantConfig();
    public Giant(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.GIANT, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {

    }

    @Override
    public void originParticle(int tickNum) {

    }

    @Override
    public String serializeCustomData() {
        HashMap<String, Serializable> hashMap = new HashMap<>();
        hashMap.put("PrimaryCooldown", getPrimaryCooldown());
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
        } catch (Exception ignored) {
            //will use default values
        }
    }

    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        int radius = GIANT_CONFIG.getPrimaryAbilityRadius();
        ParticleUtil.generateSphereParticle(Particle.SMOKE, player.getLocation(), 400, radius);
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                primaryAbilityOnPlayer((Player) entity);
            } else if (entity instanceof Enemy) {
                primaryAbilityOnEnemy((Enemy) entity);
            }
        }
        primaryAbilityOnPlayer(getPlayer());
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 2, 0.05f);
    }

    @Override
    public void secondaryAbilityLogic() {

    }

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.MEDIUM;
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        GIANT_CONFIG.loadConfig();
    }

    public void primaryAbilityOnPlayer(Player player) {
        PotionsUtil.addEffect((LivingEntity) player, PotionEffectType.HASTE, GIANT_CONFIG.getPrimaryAbilityHasteAmplifier(), GIANT_CONFIG.getPrimaryAbilityHasteDuration());
        PotionsUtil.addEffect((LivingEntity) player, PotionEffectType.STRENGTH, GIANT_CONFIG.getPrimaryAbilityStrengthAmplifier(), GIANT_CONFIG.getPrimaryAbilityStrengthDuration());
        ParticleUtil.generateCircleParticle(Particle.HAPPY_VILLAGER, player.getLocation(), 40, 1.5);
    }

    public void primaryAbilityOnEnemy(Enemy livingEntity) {
        PotionsUtil.addEffect(livingEntity, PotionEffectType.SLOWNESS, GIANT_CONFIG.getPrimaryAbilitySlownessAmplifier(), GIANT_CONFIG.getPrimaryAbilitySlownessDuration());
        PotionsUtil.addEffect(livingEntity, PotionEffectType.WEAKNESS, GIANT_CONFIG.getPrimaryAbilityWeaknessAmplifier(), GIANT_CONFIG.getPrimaryAbilityWeaknessDuration());
        ParticleUtil.generateSphereParticle(Particle.ANGRY_VILLAGER, livingEntity.getLocation(), 40, 1.5);
    }
}
