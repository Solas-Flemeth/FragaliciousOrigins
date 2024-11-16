package org.originsreborn.fragaliciousorigins.origins.phantom;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.abilities.GhostMaker;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PlayerUtils;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.enums.DayCycle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * Primary Ability - Phase - Shifts through a wall infront of you X blocks until the next safe teleport location. No hunger cost (will fail if no safe spot)
 * Secondary Ability - toggle shadowstep
 * - While sprinting, player is in shadowstep
 * - Shadowstep lets players walk through walls
 * - Shadowstep consumes hunger when using at a rate of 0.x hunger / second.
 * - Can exit by stop sprinting or hitting 3 hunger
 * - Phantom has mining fatigue and weakness in direct sunlight.
 * - Phantoms health, mining speed, and size ranges in light level. (0.75x - 1.25x)
 * - Phantom has a dodge chance from 0% to 32% based on light level.
 * - Phantom have night vision in the dark as well as regen.
 * - Phantom is immune to sharpness. Takes bonus from smite.
 * - Phantom takes bonus damage from fire and burns 2x longer.
 */
public class Phantom extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.PHANTOM);
    public static final PhantomConfig PHANTOM_CONFIG = new PhantomConfig();
    private int food;
    private float saturation;
    private int coyoteTime = -1;
    private final String KEY_COYOTE;
    private final String KEY_FOOD_DRAIN;
    private boolean isNightVisionActive = false;
    private double veilwalkEfficency = 0.0;
    public Phantom(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.PHANTOM, state, customDataString);
        getPlayer().setGameMode(GameMode.SURVIVAL);
        if (coyoteTime == -1) {
            coyoteTime = PHANTOM_CONFIG.getCoyoteMaxCharge();
        }
        if (food == 0) {
            updateFood();
        }
        getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
        KEY_COYOTE = getUUID().toString() + "_COYOTE";
        KEY_FOOD_DRAIN = getUUID().toString() + "_FOOD_DRAIN";
        PotionsUtil.addEffect(getPlayer(), PotionEffectType.INVISIBILITY,2);
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        PHANTOM_CONFIG.loadConfig();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            spectatorManagement(tickNum);
            ParticleUtil.generateCircleParticle(Particle.SOUL,getPlayer().getLocation(),2,.8);
            ParticleUtil.generateCircleParticle(Particle.SMOKE,getPlayer().getLocation(),3,0.5);
            ParticleUtil.generateCircleParticle(Particle.PORTAL,getPlayer().getLocation(),2,1.25);
        } else {
            if (tickNum % 10 == 0) {
                coyoteTimeManagement();
                if(!player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                    PotionsUtil.addEffect(getPlayer(), PotionEffectType.INVISIBILITY,2);
                }
            }
            updateAttributes();
        }
    }

    /**
     * @param event
     */
    @Override
    public void onTeleport(PlayerTeleportEvent event) {
        super.onTeleport(event);
        if(getPlayer().getGameMode().equals(GameMode.SPECTATOR)){
            event.setCancelled(true);
        }
    }

    /**
     * @param map
     * @throws Exception
     */
    @Override
    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {
        coyoteTime = (int) map.get("coyoteTime");
        super.additionalDeserialization(map);

    }

    /**
     * @param map
     * @return
     */
    @Override
    public @NotNull HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        map.put("coyoteTime",getCoyoteTime());
        return super.additionalSerializationOfCustomData(map);
    }

    /**
     * @param tickNum
     */
    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateSphereParticle(Particle.SOUL,getPlayer().getLocation(),1,1.5);
    }

    /**
     * @param event
     */
    @Override
    public void clickEvent(PlayerInteractEvent event) {
        super.clickEvent(event);
        if (getPlayer().getGameMode().equals(GameMode.SPECTATOR) && this.getPrimaryCooldown() == 0) {
            getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }

    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        if (player.getGameMode().equals(GameMode.SURVIVAL) && player.getFoodLevel() > PHANTOM_CONFIG.getMinimumHunger() && !player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @Override
    public void secondaryAbilityLogic() {
        Player phantom = getPlayer();
        phantom.getWorld().playSound(phantom.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1f, 0.5f);
        for(Entity entity : phantom.getNearbyEntities(PHANTOM_CONFIG.getSecondaryRange(), PHANTOM_CONFIG.getSecondaryRange(), PHANTOM_CONFIG.getSecondaryRange())){
            if(entity instanceof Player target){
                phantom.addPotionEffects(target.getActivePotionEffects());
                ParticleUtil.generateSphereParticle(Particle.SMOKE, target.getLocation(), 20, 2.5);
            }
        }

    }

    /**
     * @param event
     */
    @Override
    public void onRespawn(PlayerRespawnEvent event) {
        super.onRespawn(event);
        if(getPlayer().getGameMode().equals(GameMode.SPECTATOR)){
            getPlayer().setGameMode(GameMode.SURVIVAL);
            setCoyoteTime(PHANTOM_CONFIG.getCoyoteMaxCharge());
        }
        PotionsUtil.addEffect(getPlayer(), PotionEffectType.INVISIBILITY,2);
    }

    /**
     * @param event
     */
    @Override
    public void changeGamemode(PlayerGameModeChangeEvent event) {
        super.changeGamemode(event);
        Player player = event.getPlayer();
        GameMode newMode = event.getNewGameMode();
        GameMode currentMode = player.getGameMode();
        veilwalkEfficency = 0.0;
        if (newMode == GameMode.SURVIVAL && currentMode == GameMode.SPECTATOR) {
            List<Player> players = player.getWorld().getPlayers();
            for (Player otherPlayer : players) {
                player.showPlayer(FragaliciousOrigins.INSTANCE, otherPlayer);
            }
            player.setFoodLevel(food);
            player.setSaturation(saturation);
            player.setFreezeTicks(0);
            FragaliciousOrigins.BOSS_BARS.removeBossBar(KEY_FOOD_DRAIN);
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        } else if (newMode == GameMode.SPECTATOR && currentMode == GameMode.SURVIVAL) {
            PotionsUtil.addEffect(player, PotionEffectType.NIGHT_VISION, 0);
            updateFood();
            if(saturation > 0f){
                setSaturation(getSaturation()-1f);
                player.setSaturation(saturation);
            }else if(food > PHANTOM_CONFIG.getMinimumHunger()){
                setFood(food-1);
                player.setFoodLevel(food);
            }
            List<Player> players = player.getWorld().getPlayers();
            for (Player otherPlayer : players) {
                player.hidePlayer(FragaliciousOrigins.INSTANCE, otherPlayer);
            }
            FragaliciousOrigins.BOSS_BARS.createBossBar(KEY_FOOD_DRAIN, getFoodDrainComponent(), getFoodDrainPercentage(), BossBar.Color.BLUE, BossBar.Overlay.PROGRESS, player);
            player.setFreezeTicks(Integer.MAX_VALUE);
        }
    }

    /**
     *
     */
    @Override
    public void onRemoveOrigin() {
        super.onRemoveOrigin();
        getPlayer().setGameMode(GameMode.SURVIVAL);
        getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        FragaliciousOrigins.BOSS_BARS.removeBossBar(KEY_FOOD_DRAIN);
        FragaliciousOrigins.BOSS_BARS.removeBossBar(KEY_COYOTE);
    }

    public int getFood() {
        return food;
    }

    /**
     * @return
     */
    @Override
    public int getSecondaryMaxCooldown() {
        if(getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL)){
            return super.getSecondaryMaxCooldown();
        }else{
            return (int) (super.getSecondaryMaxCooldown()* PHANTOM_CONFIG.getSecondaryCooldownMultiplier());
        }

    }

    public void setFood(int food) {
        if (food < 0) {
            food = 0;
        }
        this.food = food;
    }

    public float getSaturation() {
        if (saturation < 0) {
            saturation = 0;
        }
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public int getCoyoteTime() {
        return coyoteTime;
    }

    public void setCoyoteTime(int coyoteTime) {
        if (coyoteTime < 0) {
            coyoteTime = 0;
        }
        this.coyoteTime = coyoteTime;
    }

    private void updateFood() {
        food = getPlayer().getFoodLevel();
        saturation = getPlayer().getSaturation();
    }

    private void coyoteTimeManagement() {
        Player player = getPlayer();
        Block headblock = player.getEyeLocation().getBlock();
        if (headblock.getLightFromSky() == 15 && headblock.getLightLevel() == 15 && DayCycle.getCurrentTime(player.getWorld()) == DayCycle.DAY) {
            if(!FragaliciousOrigins.BOSS_BARS.containsBossBar(KEY_COYOTE)){
                FragaliciousOrigins.BOSS_BARS.createBossBar(KEY_COYOTE, getCoyoteComponent(), getCoyotePercentage(), BossBar.Color.RED, BossBar.Overlay.PROGRESS, getPlayer());
            }
            if (coyoteTime == 0) {
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 0.7f);
                if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                    player.damage(2.0, DamageSource.builder(DamageType.OUT_OF_WORLD).build());
                } else {
                    player.damage(2.0 / PHANTOM_CONFIG.getFireResistanceDamageReduction(), DamageSource.builder(DamageType.OUT_OF_WORLD).build());
                }
                ParticleUtil.generateSphereParticle(Particle.FLAME, player.getLocation(), 30, 1.5);
                ParticleUtil.generateSphereParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 15, 0.5);
            } else if(!player.isDead()){
                setCoyoteTime(coyoteTime - PHANTOM_CONFIG.getCoyoteDrain());
            }else{
                setCoyoteTime(PHANTOM_CONFIG.getCoyoteMaxCharge()/2);
            }
            updateCoyoteBar();
        } else if (coyoteTime < PHANTOM_CONFIG.getCoyoteMaxCharge()) {
            coyoteTime++;
            updateCoyoteBar();
            if (coyoteTime >= PHANTOM_CONFIG.getCoyoteMaxCharge()){
                FragaliciousOrigins.BOSS_BARS.removeBossBar(KEY_COYOTE);
            }
        }
    }
    private void spectatorManagement(int tickNum){
        Player player = getPlayer();
        player.setFlySpeed((float) MAIN_ORIGIN_CONFIG.getFlyingSpeed());
        playEtherealSound();
        if((player.getWorld().getEnvironment() == World.Environment.NORMAL && tickNum% PHANTOM_CONFIG.getTicksPerHunger() == 0)
                || (player.getWorld().getEnvironment() != World.Environment.NORMAL && tickNum% PHANTOM_CONFIG.getTicksPerHunger() == 0)){
            if(veilwalkEfficency > Math.random()){

            } else if(saturation > 0f){
                setSaturation(getSaturation()-1f);
                player.setSaturation(saturation);
            }else if(food > PHANTOM_CONFIG.getMinimumHunger()){
                setFood(food-1);
                player.setFoodLevel(food);
            }else{
                player.setGameMode(GameMode.SURVIVAL);
            }
            if(veilwalkEfficency < PHANTOM_CONFIG.getChanceToNotDrainMax()){
                veilwalkEfficency += PHANTOM_CONFIG.getChanceToNotDrainInterval();
            }
            updateFoodDrainBar();
        }
    }

    /**
     * @return
     */
    @Override
    public double getDodgeChance() {
        return super.getDodgeChance() + PHANTOM_CONFIG.getDodgeChance() * (double) (15 - getPlayer().getEyeLocation().getBlock().getLightLevel());
    }
    private void playEtherealSound() {
        if (Math.random() < 0.1) {
            Player player = getPlayer();
            Location location = player.getLocation();
            float pitch = (float) Math.random()/3.0f;
            switch ((int) (Math.random() * 10)) {
                case 0 ->player.playSound(location, Sound.PARTICLE_SOUL_ESCAPE, 3f, pitch);
                case 1 -> player.playSound(location, Sound.AMBIENT_WARPED_FOREST_MOOD, 1f,pitch);
                case 2 -> player.playSound(location, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE, 2f, pitch);
                case 3 -> player.playSound(location, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, 4f, pitch);
                case 4 -> player.playSound(location, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, 1f, pitch);
                case 5 -> player.playSound(location, Sound.AMBIENT_BASALT_DELTAS_MOOD, 1f, pitch);
                case 6 -> player.playSound(location, Sound.AMBIENT_WARPED_FOREST_LOOP, 1f, pitch);
                case 7 -> player.playSound(location, Sound.ENTITY_ENDERMAN_STARE, 1f, pitch);
                case 8 -> player.playSound(location, Sound.PARTICLE_SOUL_ESCAPE, 1f, pitch);
                default -> player.playSound(location, Sound.AMBIENT_CAVE, 1f, pitch);
            }
        }
    }
    private float getFoodDrainPercentage (){
        float amount = food + saturation;
        return Origin.calculatePercentage(amount, 37f);
    }
    private float getCoyotePercentage (){
        return Origin.calculatePercentage(coyoteTime, PHANTOM_CONFIG.getCoyoteMaxCharge());
    }

    private Component getFoodDrainComponent() {
        return Component.text("Energy: " + formatFloatPercentage(getFoodDrainPercentage())).color(TextColor.color(0x8BE6F5));
    }
    private Component getCoyoteComponent() {
        return Component.text("Sunlight Exposure: " + formatFloatPercentage(getCoyotePercentage())).color(TextColor.color(0xF59300));
    }
    private void updateCoyoteBar(){
        FragaliciousOrigins.BOSS_BARS.updateBossBar(KEY_COYOTE, getCoyoteComponent(), getCoyotePercentage());
    }
    private void updateFoodDrainBar(){
        FragaliciousOrigins.BOSS_BARS.updateBossBar(KEY_FOOD_DRAIN, getFoodDrainComponent(), getFoodDrainPercentage());
    }

    private void updateAttributes(){
        Player player = getPlayer();
        boolean isOverworld = getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL);
        double lightLevelMultiplier;
        if(isOverworld){
            lightLevelMultiplier= 15 - ((double) player.getEyeLocation().getBlock().getLightLevel());
        }else{
            lightLevelMultiplier = 16;
        }
        double miningSpeed;
        double movementSpeed = MAIN_ORIGIN_CONFIG.getMovementSpeed() + (lightLevelMultiplier * PHANTOM_CONFIG.getSpeedLightMultiplier());
        if(player.getWorld().getEnvironment() == World.Environment.NORMAL){
            miningSpeed = MAIN_ORIGIN_CONFIG.getBlockBreakSpeed()  + (lightLevelMultiplier * PHANTOM_CONFIG.getMiningMultiplier());
        }else{
            miningSpeed = PHANTOM_CONFIG.getMiningSpeed() + (lightLevelMultiplier * PHANTOM_CONFIG.getMiningMultiplier());
        }
        PlayerUtils.setAttribute(player, Attribute.PLAYER_BLOCK_BREAK_SPEED, miningSpeed);
        PlayerUtils.setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, movementSpeed);
        if(!isOverworld && player.getFireTicks() > 0){
            player.setFireTicks(0);
        }
        if(!isOverworld ||  lightLevelMultiplier  >=  PHANTOM_CONFIG.getRegenLightLevel()){
            PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, 1, 40);
        }
        if(isNightVisionActive && lightLevelMultiplier <= PHANTOM_CONFIG.getDisableNightVisionLevel()){ //if nightVision is active and above the light level, shut it off
            isNightVisionActive = false;
            PotionsUtil.removeEffect(player, PotionEffectType.NIGHT_VISION);
        }else if(!isNightVisionActive && lightLevelMultiplier >= PHANTOM_CONFIG.getNightVisionLevel()){ //
            isNightVisionActive = true;
        }else if(isNightVisionActive){
            PotionsUtil.addEffect(player, PotionEffectType.NIGHT_VISION, 0, 600);
        }
    }

}
