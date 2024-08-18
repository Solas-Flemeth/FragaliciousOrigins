package org.originsreborn.fragaliciousorigins.abilities;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.GameEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.GenericGameEvent;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.jdbc.OriginsDAO;
import org.originsreborn.fragaliciousorigins.jdbc.SerializedOrigin;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.util.DamageUtil;

import java.util.UUID;

public class AbilityListener implements Listener {

    @EventHandler
    public void onJoinAsync(AsyncPlayerPreLoginEvent event){

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        Origin origin = OriginsDAO.getOrigin(uuid);
        FragaliciousOrigins.ORIGINS.updateOrigin(origin);
    }

    @EventHandler
    public void onLogOut(PlayerQuitEvent event){
        Origin origin = FragaliciousOrigins.ORIGINS.removeOrigin(event.getPlayer().getUniqueId());
        OriginsDAO.saveOrigin(new SerializedOrigin(origin));
        FragaliciousOrigins.ORIGINS.updateOrigin(origin);
    }
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        getOrigin(event).onBedEnter(event);
    }

    @EventHandler
    public void onBedLeaveEvent(PlayerBedLeaveEvent event) {
        getOrigin(event).onBedLeaveEvent(event);
    }

    @EventHandler
    public void onPreCommandSend(PlayerCommandPreprocessEvent event) {
        getOrigin(event).onPreCommandSend(event);
    }
    @EventHandler
    public void openInventory(InventoryOpenEvent event){
        if(event.getPlayer() instanceof Player player && !event.isCancelled()){
            FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId()).onOpenInventory(event);
        }

    }
    @EventHandler
    public void onDropitem(PlayerDropItemEvent event) {
        getOrigin(event).onDropitem(event);
    }

    @EventHandler
    public void throwEgg(PlayerEggThrowEvent event) {
        getOrigin(event).throwEgg(event);
    }

    @EventHandler
    public void expierenceChange(PlayerExpChangeEvent event) {
        getOrigin(event).experienceChange(event);
    }

    @EventHandler
    public void playerFish(PlayerFishEvent event) {
        getOrigin(event).playerFish(event);
    }

    @EventHandler
    public void playerBurn(EntityCombustEvent event){
        if(event.getEntity() instanceof Player && !event.isCancelled()){
            getOrigin((Player) event.getEntity()).onIgnite(event);
        }
    }
    @EventHandler
    public void changeGamemode(PlayerGameModeChangeEvent event) {
        getOrigin(event).changeGamemode(event);
    }

    @EventHandler
    public void harvestBlock(PlayerHarvestBlockEvent event) {
        getOrigin(event).harvestBlock(event);
    }

    @EventHandler
    public void interactStaticEntity(PlayerInteractAtEntityEvent event) {
        getOrigin(event).interactStaticEntity(event);
    }

    @EventHandler
    public void interactEntity(PlayerInteractEntityEvent event) {
        getOrigin(event).interactEntity(event);
    }

    @EventHandler
    public void clickEvent(PlayerInteractEvent event) {
        getOrigin(event).clickEvent(event);
    }

    @EventHandler
    public void toolBreak(PlayerItemBreakEvent event) {
        getOrigin(event).toolBreak(event);
    }

    @EventHandler
    public void consume(PlayerItemConsumeEvent event) {
        getOrigin(event).consume(event);
    }
    @EventHandler (priority = EventPriority.MONITOR)
    public void consumePost(PlayerItemConsumeEvent event){
        event.getPlayer().updateInventory(); //fix fake potions
    }

    @EventHandler
    public void itemDamage(PlayerItemDamageEvent event) {
        getOrigin(event).itemDamage(event);
    }

    @EventHandler
    public void mendEvent(PlayerItemMendEvent event) {
        getOrigin(event).mendEvent(event);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(!event.isCancelled()){
            getOrigin(event).onMove(event);
        }
    }

    @EventHandler
    public void pickupArrow(PlayerPickupArrowEvent event) {
        getOrigin(event).pickupArrow(event);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        getOrigin(event.getPlayer()).onDeath(event);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        getOrigin(event).onRespawn(event);

    }

    @EventHandler
    public void onRiptide(PlayerRiptideEvent event) {
        getOrigin(event).onRiptide(event);
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        getOrigin(event).onShear(event);
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
        Origin origin = getOrigin(event);
        if (event.getPlayer().isSneaking()) {
            origin.secondaryAbility();
        } else {
            origin.primaryAbility();
        }
    }

    @EventHandler
    public void onUpdateArmor(PlayerArmorChangeEvent event){
        getOrigin(event).onUpdateArmor(event);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        getOrigin(event).onTeleport(event);
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        getOrigin(event).onToggleFlight(event);
    }

    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        getOrigin(event).onToggleSneak(event);
    }

    @EventHandler
    public void onToggleSprint(PlayerToggleSprintEvent event) {
        getOrigin(event).onToggleSprint(event);
    }

    @EventHandler
    public void velocityChange(PlayerVelocityEvent event) {
        getOrigin(event).velocityChange(event);
    }

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event) {
        if (event.getEntity() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getEntity()).onPotionEffect(event);
        }
    }

    @EventHandler
    public void onShootProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getEntity().getShooter()).onShootProjectileHit(event);
        }
    }
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event){
        if(!event.isCancelled() && event.getEntity() instanceof  Player player){
            getOrigin(player).onBowShoot(event);
        }
    }
    @EventHandler
    public void onShootProjectile(ProjectileLaunchEvent event){
        // Check if the shooter is a player
        if (event.getEntity().getShooter() instanceof Player shooter && !event.isCancelled()) {
            // Get the player who shot the projectile
            switch (event.getEntity().getType()){
                case ARROW:
                    FragaliciousOrigins.ORIGINS.getOrigin(shooter.getUniqueId()).onArrowLaunch(event);
                case TRIDENT:
                    FragaliciousOrigins.ORIGINS.getOrigin(shooter.getUniqueId()).onTridentLaunch(event);
                case SNOWBALL:
                    FragaliciousOrigins.ORIGINS.getOrigin(shooter.getUniqueId()).onSnowballLaunch(event);
                case FIREWORK_ROCKET:
                    FragaliciousOrigins.ORIGINS.getOrigin(shooter.getUniqueId()).onRocketLaunch(event);
                case POTION:
                    FragaliciousOrigins.ORIGINS.getOrigin(shooter.getUniqueId()).onPotionLaunch(event);
            }
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getEntity()).onPickupItem(event);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        getOrigin(event.getPlayer()).onWorldChange(event);
    }
    @EventHandler (priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && !event.isCancelled()){
            //check that it is not an entity attacking them, and then calculate damage
            getOrigin((Player) event.getEntity()).onDamage(event);
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        DamageUtil.onSpecialDamageEvents(event);
        if (event.getDamager() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getDamager()).onAttackEntity(event);
        }
        if (event.getEntity() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getEntity()).onHurtByEntity(event);
        }
    }


    @EventHandler
    public void onToggleSwim(EntityToggleSwimEvent event) {
        if (event.getEntity() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getEntity()).onToggleSwim(event);
        }
    }

    @EventHandler
    public void onPotionLingering(LingeringPotionSplashEvent event){
        if(!event.isCancelled() && event.getEntity().getShooter() instanceof Player player){
            getOrigin(player).onPotionLingering(event);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event){
        if(!event.isCancelled() && event.getEntity().getShooter() instanceof Player player){
            getOrigin(player).onPotionSplash(event);
        }
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getEntity()).onToggleGlide(event);
        }
    }

    @EventHandler
    public void onResurrectEvent(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player && !event.isCancelled()) {
            getOrigin((Player) event.getEntity()).onResurrectEvent(event);
        }
    }
    @EventHandler
    public void onEnchant(EnchantItemEvent event){
        if(!event.isCancelled()){
            getOrigin(event.getEnchanter()).onEnchant(event);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.isCancelled()){
            return;
        }
        if(event.getWhoClicked() instanceof Player player){
            switch (event.getInventory().getType()){
                case MERCHANT:
                    getOrigin(player).onTradeClick(event);
                break;
                case ANVIL:
                    getOrigin(player).onAnvilClick(event);
                    break;
            }
        }
    }

    @EventHandler
    public void onHungerChangeEvent(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player player && !event.isCancelled()){
            getOrigin(player).onHungerChange(event);
        }
    }
    @EventHandler
    public void genericEvents(GenericGameEvent event){
        if(!event.isCancelled() && event.getEntity() instanceof Player player){
            if(event.getEvent().equals(GameEvent.STEP)){
                getOrigin(player).onStep(event);
            }
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event){
        if(!event.isCancelled()){
            getOrigin(event).onJump(event);
        }
    }

    private Origin getOrigin(PlayerEvent event) {
        return FragaliciousOrigins.ORIGINS.getOrigin(event.getPlayer().getUniqueId());
    }

    private Origin getOrigin(Player player) {
        return FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId());
    }


}
