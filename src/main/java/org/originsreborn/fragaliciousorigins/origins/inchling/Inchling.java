package org.originsreborn.fragaliciousorigins.origins.inchling;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import java.util.UUID;

import static org.originsreborn.fragaliciousorigins.util.PlayerUtils.setAttribute;

/**
 * Primary Ability - Toggle - Flee - Gains +50% speed and + 50% jump height and a 40% chance to dodge, but cannot attack. Size becomes 0.2.
 * - 0.24 x Scale
 * - 50% gravity
 * - 6 hearts
 * - 20% base chance to dodge
 * - 0.7x attack range
 * - 0.7x build range
 * - Better Food Managment
 */
public class Inchling extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG =  new MainOriginConfig(OriginType.INCHLING);
    public static final InchlingConfig INCHLING_CONFIG = new InchlingConfig();
    public Inchling(UUID uuid, OriginState state, String customData) {
        super(uuid, OriginType.INCHLING, state, customData);

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
    public void primaryAbilityLogic() {
        primaryToggle();
        if(isPrimaryEnabled()){
            enableFlee();
        }else{
            disableFlee();
        }
    }

    @Override
    public void secondaryAbilityLogic() {

    }

    /**
     * @param event
     */
    @Override
    public void onTridentLaunch(ProjectileLaunchEvent event) {
        if(isPrimaryEnabled()){
            event.setCancelled(true);
        }else{
            super.onTridentLaunch(event);
        }

    }

    /**
     * @param event
     */
    @Override
    public void onSnowballLaunch(ProjectileLaunchEvent event) {
        if(isPrimaryEnabled()){
            event.setCancelled(true);
        }else{
            super.onSnowballLaunch(event);
        }

    }

    /**
     * @param event
     */
    @Override
    public void onArrowLaunch(ProjectileLaunchEvent event) {
        if(isPrimaryEnabled()){
            event.setCancelled(true);
            getPlayer().sendMessage("You cannot use rockets while fleeing");
        }else{
            super.onArrowLaunch(event);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onRocketLaunch(ProjectileLaunchEvent event) {
        if(isPrimaryEnabled()){
            event.setCancelled(true);
            getPlayer().sendMessage("You cannot use rockets while fleeing");
        }else{
            super.onRocketLaunch(event);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onBowShoot(EntityShootBowEvent event) {
        if(isPrimaryEnabled()){
            event.setCancelled(true);
        }else{
            super.onBowShoot(event);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onAttackEntity(EntityDamageByEntityEvent event) {
        if(isPrimaryEnabled()){
            event.setCancelled(true);
        }else{
            super.onAttackEntity(event);
        }

    }

    /**
     * @return
     */
    @Override
    public double getDodgeChance() {
        if(isPrimaryEnabled()){
            return INCHLING_CONFIG.getDodgeChance();
        }else{
            return super.getDodgeChance();
        }
    }

    /**
     * @param event
     */
    @Override
    public void onHungerChange(FoodLevelChangeEvent event) {
        if(event.getFoodLevel() < getPlayer().getFoodLevel() && Math.random() < INCHLING_CONFIG.getHungerLossCancelChance()){
            event.setCancelled(true);
        }else{
            super.onHungerChange(event);
        }

    }

    /**
     * Prevent inchling from taking damage if dodging or touching thorns or cactus
     * @param event
     */
    @Override
    public void onDamage(EntityDamageEvent event) {
        if(!event.isCancelled()){
            switch (event.getCause()){
                case CONTACT:
                case THORNS:
                    event.setCancelled(true);
                    return;
                default:
                    super.onDamage(event);
            }
        }
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        INCHLING_CONFIG.loadConfig();
    }

    private void enableFlee(){
        Player player = getPlayer();
        setAttribute(player, Attribute.GENERIC_SCALE, INCHLING_CONFIG.getSizeModifier());
        setAttribute(player, Attribute.GENERIC_JUMP_STRENGTH, INCHLING_CONFIG.getJumpHeightModifier());
        setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, INCHLING_CONFIG.getSpeedModifier());
    }
    private void disableFlee(){
        Player player = getPlayer();
        setAttribute(player, Attribute.GENERIC_SCALE, MAIN_ORIGIN_CONFIG.getScale());
        setAttribute(player, Attribute.GENERIC_JUMP_STRENGTH, MAIN_ORIGIN_CONFIG.getJumpStrength());
        setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, MAIN_ORIGIN_CONFIG.getMovementSpeed());
    }
}
