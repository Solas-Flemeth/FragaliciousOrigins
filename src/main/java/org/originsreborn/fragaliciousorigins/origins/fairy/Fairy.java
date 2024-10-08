package org.originsreborn.fragaliciousorigins.origins.fairy;

import net.kyori.adventure.text.Component;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.enums.Food;

import java.util.List;
import java.util.UUID;

public class Fairy extends Origin {
    public static MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.FAIRY);
    public static FairyConfig FAIRY_CONFIG = new FairyConfig();

    public Fairy(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.FAIRY, state, customDataString);
        Player player = getPlayer();
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        FAIRY_CONFIG.loadConfig();
    }

    /**
     *
     */
    @Override
    public void setDefaultStats() {
        super.setDefaultStats();
        getPlayer().setAllowFlight(true);
        getPlayer().setFlying(true);
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

    }

    /**
     * @param event
     */
    @Override
    public void onAttackEntity(EntityDamageByEntityEvent event) {
        Player player = getPlayer();
        player.sendActionBar(Component.text("I don't want to hurt people").color(errorColor()));
        event.setCancelled(true);
        playNegativeSound(player);
    }

    /**
     *
     */
    @Override
    public void onRemoveOrigin() {
        super.onRemoveOrigin();
        Player player = getPlayer();
        player.setFlying(false);
        player.setAllowFlight(false);
    }


    /**
     * @param event
     */
    @Override
    public void onRocketLaunch(ProjectileLaunchEvent event) {
        Player player = getPlayer();
        player.sendActionBar(Component.text("I don't want to hurt people").color(errorColor()));
        event.setCancelled(true);
        playNegativeSound(player);
    }

    /**
     * @param event
     */
    @Override
    public void onArrowLaunch(ProjectileLaunchEvent event) {
        Player player = getPlayer();
        player.sendActionBar(Component.text("I don't want to hurt people").color(errorColor()));
        event.setCancelled(true);
        playNegativeSound(player);
    }


    /**
     * @param event
     */
    @Override
    public void onTridentLaunch(ProjectileLaunchEvent event) {
        Player player = getPlayer();
        player.sendActionBar(Component.text("I don't want to hurt people").color(errorColor()));
        event.setCancelled(true);
        playNegativeSound(player);
    }

    /**
     * @param event
     */
    @Override
    public void onBowShoot(EntityShootBowEvent event) {
        Player player = getPlayer();
        player.sendActionBar(Component.text("I don't want to hurt people").color(errorColor()));
        event.setCancelled(true);
        playNegativeSound(player);
    }

    /**
     * @param tickNum
     */
    @Override
    public void originParticle(int tickNum) {
        if(tickNum%3 == 0){
            ParticleUtil.generateSphereParticle(Particle.CHERRY_LEAVES, getPlayer().getLocation(), 1, 0.75);
        }
    }
    @Override
    public void consume(PlayerItemConsumeEvent event){
        super.consume(event);
        Player player = event.getPlayer();
        Food food = Food.getFood(event.getItem().getType());
        if(food != null && !food.isSweet()){
            player.sendActionBar(Component.text("Yuck! This is too bitter").color(errorColor()));
            event.setCancelled(true);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ALLAY_ITEM_TAKEN, 1, 1.5f);
        }else{
            PotionsUtil.addEffect(player, PotionEffectType.SPEED, FAIRY_CONFIG.getSpeedAmplifier(), FAIRY_CONFIG.getSpeedDuration());
            PotionsUtil.addEffect(player, PotionEffectType.SPEED, FAIRY_CONFIG.getSpeedAmplifier(), FAIRY_CONFIG.getSpeedDuration());
        }
    }

    /**
     *
     */
    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        List<Entity> entityList = player.getNearbyEntities(FAIRY_CONFIG.getPrimaryRange(), FAIRY_CONFIG.getPrimaryRange(), FAIRY_CONFIG.getPrimaryRange());
        for (Entity entity : entityList) {
            if (entity instanceof Player target) {
                Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(target.getUniqueId());
                if(!origin.getType().equals(OriginType.FAIRY)){
                    try{
                        if (origin.getPrimaryCooldown() > 1) {
                            int primaryCooldown = (int) (1 + (((float)origin.getPrimaryCooldown()) * FAIRY_CONFIG.getPrimaryAbilityReduction()));
                            origin.setPrimaryCooldown( primaryCooldown);
                        }
                        if (origin.getSecondaryCooldown() > 1) {
                            int secondaryCooldown = (int) (1 + (((float)  origin.getSecondaryCooldown()) * FAIRY_CONFIG.getPrimaryAbilityReduction()));
                            origin.setSecondaryCooldown( secondaryCooldown);
                        }
                    }catch (NullPointerException e){
                        System.err.println(target.getName() + " does not have an origin when used with fairy primary");
                    }
                }
                target.sendActionBar(player.displayName().append(Component.text(" makes you feel refreshed and ready").color(Origin.enableColor())));
                PotionsUtil.addEffect(target, PotionEffectType.REGENERATION, FAIRY_CONFIG.getPrimaryAmplifier(),FAIRY_CONFIG.getPrimaryRegenDuration());
                target.setExhaustion(0f);
                ParticleUtil.generateSphereParticle(Particle.HEART, target.getLocation(), 25, 2.5f);
            }
            ParticleUtil.generateCircleParticle(Particle.HAPPY_VILLAGER, player.getLocation(), 50, FAIRY_CONFIG.getPrimaryRange());
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 1, 1.5f);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ALLAY_ITEM_GIVEN, 2, 1.5f);
        }
    }

    /**
     *
     */
    @Override
    public void secondaryAbilityLogic() {

    }

}
