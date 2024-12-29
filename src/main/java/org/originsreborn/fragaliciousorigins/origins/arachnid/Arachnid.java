package org.originsreborn.fragaliciousorigins.origins.arachnid;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousanomaly.objects.enums.MoonState;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PlayerUtils;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.enums.Food;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class  Arachnid extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ARACHNID);
    public static final ArachnidConfig ARACHNID_CONFIG = new ArachnidConfig();
    private static final ConcurrentHashMap<Location, Integer> cobwebMap;
    public Arachnid(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ARACHNID, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        onClimb();
        if(tickNum%20 == 0){
            PotionsUtil.addEffect(getPlayer(), PotionEffectType.NIGHT_VISION, 0, 300);
        }
        if(getPlayer().hasPotionEffect(PotionEffectType.POISON)){
            getPlayer().removePotionEffect(PotionEffectType.POISON);
        }
    }

    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateSphereParticle(Particle.POOF, getPlayer().getLocation(), 1, 1);
    }

    /**
     * @param map
     * @return
     */
    @Override
    public @NotNull HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        return map;
    }

    /**
     * @param map
     * @throws Exception
     */
    @Override
    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {

    }

    @Override
    public void primaryAbilityLogic() {

    }

    @Override
    public void secondaryAbilityLogic() {
        secondaryToggle();
        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_PARROT_IMITATE_SPIDER, 2, 0.5f);
    }
    @Override
    public void consume(PlayerItemConsumeEvent event){
        super.consume(event);
        Food food = Food.getFood(event.getItem().getType());
        Player player = getPlayer();
        if(food != null && !food.isMeat()){
            player.sendActionBar(Component.text("Yuck! You can only eat meat").color(errorColor()));
            event.setCancelled(true);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPIDER_HURT, 2, 0.5f);
        }
    }


    /**
     * @param moonCycle
     */
    @Override
    public void onSunset(MoonState moonCycle) {
        super.onSunset(moonCycle);
        Player player = getPlayer();
        double moonCycleValue = MoonState.getPercentageTillFull(moonCycle);
        int potionID = (int) (Math.random()*7.0);
        int amplifier = (int) (Math.random()/1.5 + moonCycleValue*1.25);
        if(moonCycleValue > Math.random()-0.05){
            switch (potionID){
                case 0:
                    PotionsUtil.addEffect(player, PotionEffectType.STRENGTH, amplifier, 12000);
                    break;
                case 1:
                    PotionsUtil.addEffect(player, PotionEffectType.SPEED, amplifier, 12000);
                    break;
                case 2:
                    PotionsUtil.addEffect(player, PotionEffectType.HASTE, amplifier, 12000);
                    break;
                case 3:
                    PotionsUtil.addEffect(player, PotionEffectType.RESISTANCE, amplifier, 12000);
                    break;
                case 4:
                    PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, amplifier, 12000);
                    break;
                case 5:
                    PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, amplifier, 12000);
                    PotionsUtil.addEffect(player, PotionEffectType.RESISTANCE, amplifier, 12000);
                    PotionsUtil.addEffect(player, PotionEffectType.HASTE, amplifier, 12000);
                    PotionsUtil.addEffect(player, PotionEffectType.SPEED, amplifier, 12000);
                    PotionsUtil.addEffect(player, PotionEffectType.STRENGTH, amplifier, 12000);
                    break;
            }
        }
    }

    private void onClimb() {
        Player player = getPlayer();
        if (isSecondaryEnabled()) {
            // Check if there are solid blocks in all cardinal directions around the player
            boolean hasWallNorth = !player.getLocation().getBlock().getRelative(0, 0, -1).isPassable();
            boolean hasWallSouth = !player.getLocation().getBlock().getRelative(0, 0, 1).isPassable();
            boolean hasWallEast = !player.getLocation().getBlock().getRelative(1, 0, 0).isPassable();
            boolean hasWallWest = !player.getLocation().getBlock().getRelative(-1, 0, 0).isPassable();
            boolean hasUpperNorth = !player.getLocation().getBlock().getRelative(0, 1, -1).isPassable();
            boolean hasUpperSouth = !player.getLocation().getBlock().getRelative(0, 1, 1).isPassable();
            boolean hasUpperEast = !player.getLocation().getBlock().getRelative(1, 1, 0).isPassable();
            boolean hasUpperWest = !player.getLocation().getBlock().getRelative(-1, 1, 0).isPassable();
            boolean hasRoof = !player.getLocation().getBlock().getRelative(0, 2, 0).isPassable();
            boolean hasFloor = !player.getLocation().getBlock().getRelative(0, -1, 0).isPassable();
            boolean onGround = player.isOnGround();
            if (((hasWallNorth || hasWallSouth || hasWallEast || hasWallWest) && !hasFloor && !onGround)
                    || (hasUpperEast || hasUpperWest || hasUpperNorth || hasUpperSouth && !onGround)
                    || (hasRoof && !hasFloor && !onGround)) {
                if (!player.isFlying()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    PlayerUtils.setFlySpeed(player, (float) MAIN_ORIGIN_CONFIG.getFlyingSpeed());
                }
            } else if (player.isFlying()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                PlayerUtils.resetFlySpeed(player);
            }
        }else if(player.isFlying()){
            player.setFlying(false);
        }
    }
    private void attemptCreatingCobWeb(Location location, boolean isRange, LivingEntity entity){
        if(location.getBlock().isEmpty()){
            if(isRange){
                System.out.println("Is Ranged");
                if(ARACHNID_CONFIG.getWebRangeChance() > Math.random()){
                    System.out.println("Adding Cobweb");
                    location.getBlock().setType(Material.COBWEB);
                    cobwebMap.put(location, ARACHNID_CONFIG.getWebRangeDuration());
                    if(entity != null){
                        PotionsUtil.addEffect(entity, PotionEffectType.POISON, ARACHNID_CONFIG.getWebRangePoisonAmplifier(), ARACHNID_CONFIG.getWebRangePoisonDuration());
                    }
                }
            }else{
                if(ARACHNID_CONFIG.getWebMeleeChance() > Math.random()){
                    location.getBlock().setType(Material.COBWEB);
                    cobwebMap.put(location, ARACHNID_CONFIG.getWebMeleeDuration());
                    if(entity != null){
                        PotionsUtil.addEffect(entity, PotionEffectType.POISON, ARACHNID_CONFIG.getWebMeleePoisonAmplifier(), ARACHNID_CONFIG.getWebMeleePoisonDuration());
                    }
                }
            }
        }
    }

    /**
     * @param event
     */
    @Override
    public void onAttackEntity(EntityDamageByEntityEvent event) {
        super.onAttackEntity(event);
        if(!event.isCancelled() && event.getEntity() instanceof LivingEntity entity){
            attemptCreatingCobWeb(entity.getLocation(), false, entity);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onShootProjectileHit(ProjectileHitEvent event) {
        super.onShootProjectileHit(event);
        if(!event.isCancelled() && event.getEntity() instanceof Arrow arrow && event.getHitEntity() instanceof LivingEntity entity ){
            attemptCreatingCobWeb(entity.getLocation(), true, entity);
        }
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        ARACHNID_CONFIG.loadConfig();
    }
    static{
        cobwebMap = new ConcurrentHashMap<Location, Integer>();
        BukkitScheduler scheduler = FragaliciousOrigins.INSTANCE.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(FragaliciousOrigins.INSTANCE, () -> {
            if(!cobwebMap.isEmpty()){
                Enumeration<Location> enumeration = cobwebMap.keys();
                while(enumeration.hasMoreElements()){
                    Location location = enumeration.nextElement();
                    Integer duration = cobwebMap.get(location);
                    if(duration > 0){
                        duration--;
                        cobwebMap.put(location, duration);
                    }else{
                        Block block = location.getBlock();
                        if(block.getType() != Material.AIR){
                            block.setType(Material.AIR);
                        }
                        cobwebMap.remove(location);
                    }
                }
            }
        }, 20, 20);
    }
}
