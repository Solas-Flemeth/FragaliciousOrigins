package org.originsreborn.fragaliciousorigins.origins.bee;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.origins.wip.Phytokin;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Bee extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.BEE);
    public static final BeeConfig BEE_CONFIG = new BeeConfig();

    public Bee(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.BEE, state, customDataString);
    }


    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        BEE_CONFIG.loadConfig();
    }

    /**
     * @return
     */
    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    /**
     * @param tickNum
     */
    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if (player.isSneaking()) {
            PotionsUtil.addEffect(player, PotionEffectType.LEVITATION, 2, 4);
        }
        int radius = BEE_CONFIG.getHiveMindRadius();
        for (Entity entity : player.getNearbyEntities(radius * 2, radius * 2, radius * 2)) {
            if (entity instanceof Player nearbyPlayer) {
                Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(entity.getUniqueId());
                switch (origin.getType()) {
                    case BEE:
                        beeHiveMind((Bee) origin);
                        break;
                    case PHYTOKIN:
                        phytokinHiveMind((Phytokin) origin);
                        break;
                }
            }
        }
    }

    /**
     * @param tickNum
     */
    @Override
    public void originParticle(int tickNum) {
        if(tickNum%5 == 0){
            ParticleUtil.generateParticleAtLocation(Particle.LANDING_HONEY, getPlayer().getLocation(), 1);
        }

    }

    @Override
    public String serializeCustomData() {
        HashMap<String, Serializable> hashMap = new HashMap<>();
        hashMap.put("PrimaryCooldown", getPrimaryCooldown());
        hashMap.put("SecondaryEnabled", isSecondaryEnabled());
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
            secondaryToggle((boolean) hashMap.get("SecondaryEnabled"));
        } catch (Exception ignored) {
            //will use default values
        }
    }

    /**
     *
     */
    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        int radius = BEE_CONFIG.getPrimaryAbilityRadius();
        Location playerLocation = getPlayer().getLocation();
        // Calculate the boundaries of the bounding box
        int minX = playerLocation.getBlockX() - radius;
        int minY = playerLocation.getBlockY() - radius;
        int minZ = playerLocation.getBlockZ() - radius;
        int maxX = playerLocation.getBlockX() + radius;
        int maxY = playerLocation.getBlockY() + radius;
        int maxZ = playerLocation.getBlockZ() + radius;
        World world = playerLocation.getWorld();
        world.playSound(getPlayer().getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 2.5f);
        // Iterate through nearby blocks within the bounding box
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location blockLocation = new Location(world, x, y, z);
                    Material material = blockLocation.getBlock().getType();
                    if (isPlant(material)) {
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityFlowerChance()) {
                            world.dropItem(blockLocation, new ItemStack(material));
                            ParticleUtil.generateParticleAtLocation(Particle.CLOUD, blockLocation, 3);
                        }
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityBonemealChance()) {
                            blockLocation.getBlock().applyBoneMeal(BlockFace.UP);

                        }
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityHoneyCombChance()) {
                            world.dropItem(blockLocation, new ItemStack(Material.HONEYCOMB));
                            ParticleUtil.generateParticleAtLocation(Particle.LANDING_HONEY, blockLocation, 3);
                            ParticleUtil.generateParticleAtLocation(Particle.HAPPY_VILLAGER, blockLocation, 3);
                        }
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityHoneyBottleChance()) {
                            world.dropItem(blockLocation, new ItemStack(Material.HONEY_BOTTLE));
                            ParticleUtil.generateParticleAtLocation(Particle.LANDING_HONEY, blockLocation, 3);
                            ParticleUtil.generateParticleAtLocation(Particle.HAPPY_VILLAGER, blockLocation, 3);
                        }
                    }
                }
            }
        }
        //effects for phytokins
        for (Entity entity : player.getNearbyEntities(radius * 2, radius * 2, radius * 2)) {
            if (entity instanceof Player nearbyPlayer) {
                Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(entity.getUniqueId());
                if(origin.getType().equals(OriginType.PHYTOKIN)){
                    PotionsUtil.addEffect(nearbyPlayer, PotionEffectType.HEALTH_BOOST, BEE_CONFIG.getPrimaryAbilityPhytokinHealthBoostAmplifier(), BEE_CONFIG.getPrimaryAbilityPhytokinHealthBoostDuration()*20);
                    ParticleUtil.generateParticleLine(Particle.HEART, getPlayer().getLocation(), nearbyPlayer.getLocation(), 40);
                    ParticleUtil.generateCircleParticle(Particle.HEART, nearbyPlayer.getLocation(), 20, 1.6);
                }

            }
        }
    }

    /**
     *
     */
    @Override
    public void secondaryAbilityLogic() {
        secondaryToggle();
    }

    /**
     * @return
     */
    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.HARD;
    }

/**
*
*/
    @Override
    public void consume(PlayerItemConsumeEvent event) {
        if(!event.isCancelled()){
            Material material = event.getItem().getType();
            if(material.equals(Material.HONEY_BOTTLE) || material.equals(Material.POTION)){
                super.consume(event);
            }else{
                event.setCancelled(true);
                event.getPlayer().sendActionBar(Component.text("Yuck! You can only eat honey.").color(errorColor()));
            }

        }

    }

    /**
     *
     */
    @Override
    public void onAttackEntity(EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && Math.random() < BEE_CONFIG.getSecondaryAbilityFinalStingChance() && event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && isSecondaryEnabled()) {
            event.setDamage(event.getDamage() * BEE_CONFIG.getSecondaryAbilityFinalStingMultiplier());
            getPlayer().setHealth(1.0);
        }
        super.onAttackEntity(event);

    }

    /**
     * Determines if it is considered a valid plant
     *
     * @param material
     * @return
     */
    public boolean isPlant(Material material) {
        switch (material) {
            case DANDELION:
            case POPPY:
            case BLUE_ORCHID:
            case ALLIUM:
            case AZURE_BLUET:
            case ORANGE_TULIP:
            case RED_TULIP:
            case PINK_TULIP:
            case WHITE_TULIP:
            case OXEYE_DAISY:
            case CORNFLOWER:
            case LILY_OF_THE_VALLEY:
            case LILY_PAD:
            case TORCHFLOWER:
            case ACACIA_SAPLING:
            case BIRCH_SAPLING:
            case BAMBOO_SAPLING:
            case CHERRY_SAPLING:
            case DARK_OAK_SAPLING:
            case JUNGLE_SAPLING:
            case OAK_SAPLING:
            case SPRUCE_SAPLING:
            case PITCHER_PLANT:
            case SWEET_BERRY_BUSH:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case CRIMSON_FUNGUS:
            case WARPED_FUNGUS:
                return true;
            default:
                return false;
        }
    }

    private void phytokinHiveMind(Phytokin phytokin) {
        if (Math.random() < BEE_CONFIG.getHiveMindPhytokinSaturationChance()) {
            PotionsUtil.addEffect(phytokin.getPlayer(), PotionEffectType.SATURATION, BEE_CONFIG.getHiveMindPhytokinSaturationAmplifier(), BEE_CONFIG.getHiveMindPhytokinSaturationDuration()*20);
            PotionsUtil.addEffect(getPlayer(), PotionEffectType.SATURATION, BEE_CONFIG.getHiveMindPhytokinSaturationAmplifier(), BEE_CONFIG.getHiveMindPhytokinSaturationDuration()*20);
        }
    }

    private void beeHiveMind(Bee bee) {
        if (Math.random() < BEE_CONFIG.getHiveMindBeeRegenChance()) {
            PotionsUtil.addEffect(bee.getPlayer(), PotionEffectType.REGENERATION, BEE_CONFIG.getHiveMindBeeRegenAmplifier(), BEE_CONFIG.getHiveMindBeeRegenDuration()*20);
            PotionsUtil.addEffect(getPlayer(), PotionEffectType.REGENERATION, BEE_CONFIG.getHiveMindPhytokinSaturationAmplifier(), BEE_CONFIG.getHiveMindPhytokinSaturationDuration()*20);
        }
    }
}
