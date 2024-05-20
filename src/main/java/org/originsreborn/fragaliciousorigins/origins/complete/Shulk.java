package org.originsreborn.fragaliciousorigins.origins.complete;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.configs.ShulkConfig;
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

/**
 * SHULK
 * - primary - enderchest
 * - secondary - Makes all nearby hostile mobs gain levitate.Nearby players get slowfall
 * - Overall damage reduction for all except poison
 * - Keep inventory on death
 * - Slightly Larger
 */
public class Shulk extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.SHULK);
    public static final ShulkConfig SHULK_CONFIG = new ShulkConfig();

    public Shulk(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.SHULK, state, customDataString);
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        SHULK_CONFIG.loadConfig();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(long tickNum) {

    }

    @Override
    public void originParticle(long tickNum) {
        int tick = (int) tickNum;
        if(tick%3 == 0){
            int remainder = (int) (tickNum % 2L)+1;
            ParticleUtil.generateParticleAtLocation(Particle.DRIPPING_OBSIDIAN_TEAR, getPlayer().getLocation(), remainder);
        }

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
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.EASY;
    }

    @Override
    public void primaryAbility() {
        if (getPrimaryCooldown() > 0) {
            primaryAbilityTimerCooldownMsg();
        } else {
            getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 0.8f);
            getPlayer().openInventory(getPlayer().getEnderChest());
            setPrimaryCooldown(getPrimaryMaxCooldown());
        }
    }

    @Override
    public void secondaryAbility() {
        if (getSecondaryCooldown() > 0) {
            secondaryAbilityTimerCooldownMsg();
        } else {
            Player player = getPlayer();
            int radius = SHULK_CONFIG.getSecondaryRadius();
            ParticleUtil.generateSphereParticle(Particle.END_ROD, player.getLocation(), 500, radius);
            ParticleUtil.generateSphereParticle(Particle.SCRAPE, player.getLocation(), 500, radius);
            List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
            for (Entity entity : nearbyEntities) {
                if (entity instanceof Player) {
                    secondaryAbilityOnPlayer((Player) entity);
                } else if (entity instanceof Enemy) {
                    secondaryAbilityOnEnemy((Enemy) entity);
                    entity.setVelocity(new Vector(0, 2, 0));
                }
            }
            secondaryAbilityOnPlayer(getPlayer());
            setSecondaryCooldown(getSecondaryMaxCooldown());
            player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1, 1.5f);
        }
    }

    @Override
    public void onDeath(PlayerDeathEvent event) {
        super.onDeath(event);
        for (ItemStack itemStack : event.getDrops()) {
            event.getItemsToKeep().add(itemStack);
        }
        event.getDrops().clear();
    }

    public void secondaryAbilityOnPlayer(Player player) {
        PotionsUtil.addEffect((LivingEntity) player, PotionEffectType.SLOW_FALLING, 0, SHULK_CONFIG.getSecondaryPlayerDuration());
        ParticleUtil.generateCircleParticle(Particle.ENCHANT, player.getLocation(), 40, 1.5);
        ParticleUtil.generateCircleParticle(Particle.GLOW_SQUID_INK, player.getLocation(), 20, 1.2);
    }

    public void secondaryAbilityOnEnemy(Enemy livingEntity) {
        PotionsUtil.addEffect(livingEntity, PotionEffectType.LEVITATION, 2, SHULK_CONFIG.getSecondaryEnemyDuration());
        PotionsUtil.addEffect(livingEntity, PotionEffectType.SLOWNESS, 5, SHULK_CONFIG.getSecondaryEnemyDuration());
        ParticleUtil.generateCircleParticle(Particle.END_ROD, livingEntity.getLocation(), 35, 1.2);
    }
}
