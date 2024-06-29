package org.originsreborn.fragaliciousorigins.origins.feline;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.enums.Food;

import java.util.UUID;

/**
 *  Primary Ability: Leaps a distance if looking above crosshair - gains damage boost temporarily
 *  Secondary ability: Dashes
 *  - Insane speed
 *  - 2.1x Jump Height
 *  - 0.66x player height
 *  - Doesn't alert Shriekers + shulk sensors
 *  - Faster attack speed
 *  - Nightvision
 *  - Carnivore
 *  - 15% slower mine speed
 *  - Being wet slows them down
 *  - Minimal fall damage
 *  - Double step height
 *  - 1/8 chance to avoid death
 *  - 1.5x gravity
 */
public class Feline extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.FELINE);
    public static final FelineConfig FELINE_CONFIG = new FelineConfig();
    private double lastX, lastZ, lastX2, lastZ2;
    private int dashCooldown;
    public Feline(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.FELINE, state, customDataString);
        dashCooldown = 0;
        lastX2 = getPlayer().getX();
        lastZ2 = getPlayer().getZ();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if(player.isInWaterOrRain() && !player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)){
            PotionsUtil.addEffect(player, PotionEffectType.WEAKNESS, FELINE_CONFIG.getWeaknessAmplifier(), FELINE_CONFIG.getWeaknessDuration());
        }
        if(dashCooldown > 0){
            setDashCooldown(dashCooldown-1);
        }
        if(tickNum%20 == 0){
            PotionsUtil.addEffect(player, PotionEffectType.NIGHT_VISION, 2, 400);
        }
        lastX = lastX2;
        lastZ = lastZ2;
        lastX2 = player.getX();
        lastZ2 = player.getZ();


    }

    @Override
    public void originParticle(int tickNum) {
        double math = Math.random();
        if(math > 0.77){
            ParticleUtil.generateParticleAtLocation(Material.WHITE_WOOL, getPlayer().getLocation(), 1);
        } else if(math > 0.55){
            ParticleUtil.generateParticleAtLocation(Material.BROWN_WOOL, getPlayer().getLocation(), 1);
        }else if (math > 0.35) {
            ParticleUtil.generateParticleAtLocation(Material.BLACK_WOOL, getPlayer().getLocation(), 1);
        }else{
            ParticleUtil.generateParticleAtLocation(Material.ORANGE_WOOL, getPlayer().getLocation(), 2);
        }
    }

    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        Vector lookDirection = player.getEyeLocation().getDirection().normalize();
        Vector leapVelocity = lookDirection.multiply(FELINE_CONFIG.getLeapVelocity());
        leapVelocity.setY(leapVelocity.getY() + 0.1);
        player.setVelocity(leapVelocity);
        PotionsUtil.addEffect(player, PotionEffectType.SPEED, FELINE_CONFIG.getSpeedAmplifier(), FELINE_CONFIG.getSpeedDuration());
        PotionsUtil.addEffect(player, PotionEffectType.STRENGTH, FELINE_CONFIG.getStrengthAmplifier(), FELINE_CONFIG.getStrengthDuration());
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_HURT, 3f, 0.3f);
    }

    /**
     * @return
     */
    @Override
    public boolean primaryConditionCheck() {
        return getPlayer().getLocation().getPitch() < -5;
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
        super.onDeath(event);
        if(!event.isCancelled() && Math.random() < FELINE_CONFIG.getNineLivesChance()){
            Player player = event.getPlayer();
            event.setCancelled(true);
            event.deathMessage(player.displayName().append(Component.text("Avoided death using one of their nine lives").color(TextColor.color(0xffffff))));
            player.sendActionBar(Component.text("You used one of your nine lives to survive a fatal hit").color(Origin.enableColor()));
            player.setHealth(player.getHealthScale()/2.0);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_DEATH, 3f, 0.3f);

        }
    }
    @Override
    public void consume(PlayerItemConsumeEvent event){
        super.consume(event);
        Food food = Food.getFood(event.getItem().getType());
        if(food != null && !food.isMeat()){
            event.getPlayer().sendActionBar(Component.text("Yuck! You can only eat meat").color(errorColor()));
            event.setCancelled(true);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onJump(PlayerJumpEvent event) {
        super.onJump(event);
        if(dashCooldown > (FELINE_CONFIG.getDashCooldown() * 2/3)){
            event.setCancelled(true);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        super.onToggleSneak(event);
        Player player = event.getPlayer();
        if (isSecondaryEnabled() && dashCooldown == 0 && !player.isSwimming() && !player.isGliding() && player.isOnGround() && !player.isSneaking()) {
            double dashX = player.getX() - lastX;
            double dashZ = player.getZ() - lastZ;
            Location location = player.getLocation();
            if(dashX== 0.0 && dashZ == 0.0){
                return;
            }
            Vector direction = new Vector(dashX, 0.00001, dashZ).normalize();
            direction.setY(-5.0);
            player.setVelocity(direction.multiply(FELINE_CONFIG.getDashVelocity()));
            setDashCooldown(FELINE_CONFIG.getDashCooldown());
            player.getWorld().playSound(location, Sound.ENTITY_HORSE_BREATHE, 0.9f, 1.7f);
            ParticleUtil.generateSphereParticle(Particle.SMALL_GUST, location, 35, 0.7);
        }
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        FELINE_CONFIG.loadConfig();
    }

    public void setDashCooldown(int dashCooldown) {
        if(dashCooldown == FELINE_CONFIG.getDashCooldown()/2){
            ParticleUtil.generateSphereParticle(Particle.SMALL_GUST, getPlayer().getLocation(), 15, 0.7);
        }
        if(dashCooldown < 0){
            dashCooldown = 0;
        }
        this.dashCooldown = dashCooldown;
    }
}
