package org.originsreborn.fragaliciousorigins.origins.giant;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

import java.util.List;
import java.util.UUID;

public class Giant extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.GIANT);
    public static final MainOriginConfig GIANT_ENALRGED = new MainOriginConfig(OriginType.GIANT, "enlarged");
    public static final GiantConfig GIANT_CONFIG = new GiantConfig();
    public Giant(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.GIANT, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        if(isSecondaryEnabled()){
            return GIANT_ENALRGED;
        }
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        if(tickNum%10 == 0){
            Player player = getPlayer();
            if(isSecondaryEnabled()){
            }else{
                if(Math.random() < GIANT_CONFIG.getHungerLossChance()){
                    PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, 0, 20);
                }
            }
            if (player.getName().startsWith(".")) {
                PotionsUtil.addEffect(player, PotionEffectType.JUMP_BOOST, 1, 130);
            }
        }
    }

    @Override
    public void originParticle(int tickNum) {

    }
    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        int radius = GIANT_CONFIG.getPrimaryAbilityRadius();
        ParticleUtil.generateSphereParticle(Particle.LARGE_SMOKE, player.getLocation(), 500, radius);
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                primaryAbilityOnPlayer((Player) entity);
            } else if (entity instanceof Enemy) {
                primaryAbilityOnEnemy((Enemy) entity);
            }
        }
        primaryAbilityOnPlayer(getPlayer());
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_IMITATE_ENDER_DRAGON, 1, 1.7f);
    }

    @Override
    public void secondaryAbilityLogic() {
        secondaryToggle();
        setDefaultStats();
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        GIANT_CONFIG.loadConfig();
        GIANT_ENALRGED.loadConfig();
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
