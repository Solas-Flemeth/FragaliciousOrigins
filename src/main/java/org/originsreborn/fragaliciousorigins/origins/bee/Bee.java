package org.originsreborn.fragaliciousorigins.origins.bee;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.AreaEffectCloud;
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
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.origins.phytokin.Phytokin;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

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
        if (player.isSneaking() && (!player.isInWaterOrRain() || player.hasPotionEffect(PotionEffectType.CONDUIT_POWER))) {
            PotionsUtil.addEffect(player, PotionEffectType.LEVITATION, 2, 4);
        }
        else if (player.getName().startsWith(".")) {
            PotionsUtil.addEffect(player, PotionEffectType.SLOW_FALLING, 0, 5);
        }
        int radius = BEE_CONFIG.getHiveMindRadius();
        if(tickNum%20 == 0){
            for (Entity entity : player.getNearbyEntities(radius * 2, radius * 2, radius * 2)) {
                if (entity instanceof Player) {
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
    }

    /**
     * @param tickNum
     */
    @Override
    public void originParticle(int tickNum) {
        if (tickNum % 5 == 0) {
            ParticleUtil.generateParticleAtLocation(Particle.LANDING_HONEY, getPlayer().getLocation(), 1);
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
        world.playSound(getPlayer().getLocation(), Sound.ENTITY_BEE_POLLINATE, 3, 0.7f);
        world.playSound(getPlayer().getLocation(), Sound.ENTITY_BEE_POLLINATE, 2, 1.5f);
        // Iterate through nearby blocks within the bounding box
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location blockLocation = new Location(world, x, y, z);
                    Material material = blockLocation.getBlock().getType();
                    boolean isFlower = isFlower(material);
                    boolean isCrop = isCrop(material);
                    if (isCrop || isFlower) {
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityFlowerChance() && isFlower) {
                            world.dropItem(blockLocation, new ItemStack(material));
                            ParticleUtil.generateParticleAtLocation(Particle.CLOUD, blockLocation, 3);
                        }
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityBonemealChance() && isCrop) {
                            blockLocation.getBlock().applyBoneMeal(BlockFace.UP);
                        }
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityHoneyCombChance()) {
                            world.dropItem(blockLocation, new ItemStack(Material.HONEYCOMB));
                            ParticleUtil.generateParticleAtLocation(Particle.LANDING_HONEY, blockLocation, 3);
                            ParticleUtil.generateParticleAtLocation(Particle.HAPPY_VILLAGER, blockLocation, 3);
                        }
                        if (Math.random() < BEE_CONFIG.getPrimaryAbilityHoneyBottleChance()) {
                            if(material.equals(Material.WITHER_ROSE)){
                                AreaEffectCloud cloud = blockLocation.getWorld().spawn(blockLocation, AreaEffectCloud.class);
                                cloud.addCustomEffect(PotionEffectType.WITHER.createEffect(BEE_CONFIG.getWitherRoseDuration()/2, 0), true);
                                cloud.setDuration(BEE_CONFIG.getWitherRoseDuration());
                                cloud.setRadius(BEE_CONFIG.getWitherRoseRadius());
                            }else{
                                world.dropItem(blockLocation, new ItemStack(Material.HONEY_BOTTLE));
                                ParticleUtil.generateParticleAtLocation(Particle.LANDING_HONEY, blockLocation, 3);
                                ParticleUtil.generateParticleAtLocation(Particle.HAPPY_VILLAGER, blockLocation, 3);
                            }
                        }
                    }
                }
            }
        }
        //effects for phytokins
        for (Entity entity : player.getNearbyEntities(radius * 2, radius * 2, radius * 2)) {
            if (entity instanceof Player nearbyPlayer) {
                Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(entity.getUniqueId());
                if (origin.getType().equals(OriginType.PHYTOKIN)) {
                    PotionsUtil.addEffect(nearbyPlayer, PotionEffectType.HEALTH_BOOST, BEE_CONFIG.getPrimaryAbilityPhytokinHealthBoostAmplifier(), BEE_CONFIG.getPrimaryAbilityPhytokinHealthBoostDuration() * 20);
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
     *
     */
    @Override
    public void consume(PlayerItemConsumeEvent event) {
        if (!event.isCancelled()) {
            Material material = event.getItem().getType();
            if (material.equals(Material.HONEY_BOTTLE)) {
                if(getPlayer().getFoodLevel() != 20){
                    getPlayer().setFoodLevel(getPlayer().getFoodLevel() + 1);
                }
                if(getPlayer().getSaturation() != 20){
                    getPlayer().setSaturation(getPlayer().getSaturation() + 2f);
                }
                super.consume(event);
            } else if(material.equals(Material.POTION)) {
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
            Player player = getPlayer();
            event.setDamage(event.getDamage() * BEE_CONFIG.getSecondaryAbilityFinalStingMultiplier());
            player.setHealth(1.0);
            player.setSaturation(0);
            player.setFoodLevel(6);
            player.getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_BEE_POLLINATE, 1, 0.9f);
        }
        super.onAttackEntity(event);

    }


    private void phytokinHiveMind(Phytokin phytokin) {
        if (Math.random() < BEE_CONFIG.getHiveMindPhytokinSaturationChance()) {
            PotionsUtil.addEffect(phytokin.getPlayer(), PotionEffectType.SATURATION, BEE_CONFIG.getHiveMindPhytokinSaturationAmplifier(), BEE_CONFIG.getHiveMindPhytokinSaturationDuration() * 20);
            PotionsUtil.addEffect(getPlayer(), PotionEffectType.SATURATION, BEE_CONFIG.getHiveMindPhytokinSaturationAmplifier(), BEE_CONFIG.getHiveMindPhytokinSaturationDuration() * 20);
            if(getPrimaryCooldown() > 1){
                setPrimaryCooldown(getPrimaryCooldown()-2);
            }
            if(phytokin.getPrimaryCooldown() > 1){
                phytokin.setPrimaryCooldown(phytokin.getPrimaryCooldown()-2);
            }
            if(phytokin.getSecondaryCooldown() > 1){
                phytokin.setSecondaryCooldown(phytokin.getSecondaryCooldown()-2);
            }
        }
    }

    private void beeHiveMind(Bee bee) {
        if (Math.random() < BEE_CONFIG.getHiveMindBeeRegenChance()) {
            PotionsUtil.addEffect(bee.getPlayer(), PotionEffectType.REGENERATION, BEE_CONFIG.getHiveMindBeeRegenAmplifier(), BEE_CONFIG.getHiveMindBeeRegenDuration() * 20);
            PotionsUtil.addEffect(getPlayer(), PotionEffectType.REGENERATION, BEE_CONFIG.getHiveMindPhytokinSaturationAmplifier(), BEE_CONFIG.getHiveMindPhytokinSaturationDuration() * 20);
            if(getPrimaryCooldown() > 1){
                setPrimaryCooldown(getPrimaryCooldown()-2);
            }
            if(bee.getPrimaryCooldown() > 1){
                bee.setPrimaryCooldown(bee.getPrimaryCooldown()-2);
            }
        }
    }
    /**
     * Determines if it is considered a flower
     *
     * @param material
     * @return
     */
    public static boolean isFlower(Material material) {
        return switch (material) {
            case DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, ORANGE_TULIP, RED_TULIP, PINK_TULIP, WHITE_TULIP,
                 OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY, LILY_PAD, TORCHFLOWER, ACACIA_SAPLING, BIRCH_SAPLING,
                 BAMBOO_SAPLING, CHERRY_SAPLING, DARK_OAK_SAPLING, JUNGLE_SAPLING, OAK_SAPLING, SPRUCE_SAPLING,
                 PITCHER_PLANT, SWEET_BERRY_BUSH, BROWN_MUSHROOM, RED_MUSHROOM, CRIMSON_FUNGUS, WARPED_FUNGUS,
                 ROSE_BUSH, LILAC, PEONY, SUNFLOWER, WITHER_ROSE, CACTUS, SUGAR_CANE -> true;
            default -> false;
        };
    }

    /**
     * Determine if it is a crop
     * @param material
     * @return
     */
    public static boolean isCrop(Material material){
        return switch (material) {
            case WHEAT, CARROTS, POTATOES, BEETROOTS, NETHER_WART, MELON_STEM, PUMPKIN_STEM, PITCHER_CROP,
                 TORCHFLOWER_CROP, BAMBOO, SWEET_BERRY_BUSH, COCOA, KELP, OAK_SAPLING, BIRCH_SAPLING, SPRUCE_SAPLING,
                 JUNGLE_SAPLING, ACACIA_SAPLING, DARK_OAK_SAPLING, RED_MUSHROOM, BROWN_MUSHROOM, SEA_PICKLE,
                 POINTED_DRIPSTONE, TWISTING_VINES, WEEPING_VINES -> true;
            default -> false;
        };
    }
}
