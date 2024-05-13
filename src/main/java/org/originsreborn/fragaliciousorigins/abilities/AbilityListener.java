package org.originsreborn.fragaliciousorigins.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.jdbc.OriginsDAO;
import org.originsreborn.fragaliciousorigins.jdbc.SerializedOrigin;
import org.originsreborn.fragaliciousorigins.origins.Origin;

import java.util.UUID;

public class AbilityListener implements Listener {

    @EventHandler
    public void onJoinAsync(AsyncPlayerPreLoginEvent event){

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        System.out.println("event UUID = " + uuid);
        Origin origin = OriginsDAO.getOrigin(uuid);
        System.out.println(origin.toString());
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
    public void rightClickEvent(PlayerInteractEvent event) {
        getOrigin(event).rightClickEvent(event);
    }

    @EventHandler
    public void toolBreak(PlayerItemBreakEvent event) {
        getOrigin(event).toolBreak(event);
    }

    @EventHandler
    public void consume(PlayerItemConsumeEvent event) {
        getOrigin(event).consume(event);
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
        getOrigin(event).onMove(event);
    }

    @EventHandler
    public void pickupArrow(PlayerPickupArrowEvent event) {
        getOrigin(event).pickupArrow(event);
    }

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
        Origin origin = getOrigin(event);
        if (event.getPlayer().isSneaking()) {
            origin.secondaryAbility();
        } else {
            origin.primaryAbility();
        }
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
        if (event.getEntity() instanceof Player) {
            getOrigin((Player) event.getEntity()).onPotionEffect(event);
        }
    }

    @EventHandler
    public void onShootProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            getOrigin((Player) event.getEntity().getShooter()).onShootProjectileHit(event);
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            getOrigin((Player) event.getEntity()).onPickupItem(event);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            getOrigin((Player) event.getEntity()).onDamage(event);
        }
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            getOrigin((Player) event.getEntity()).onHurtByEntity(event);
        }
        if (event.getDamager() instanceof Player) {
            getOrigin((Player) event.getDamager()).onAttackEntity(event);
        }
    }


    @EventHandler
    public void onToggleSwim(EntityToggleSwimEvent event) {
        if (event.getEntity() instanceof Player) {
            getOrigin((Player) event.getEntity()).onToggleSwim(event);
        }
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player) {
            getOrigin((Player) event.getEntity()).onToggleGlide(event);
        }
    }

    @EventHandler
    public void onResurrectEvent(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player) {
            getOrigin((Player) event.getEntity()).onResurrectEvent(event);
        }
    }

    private Origin getOrigin(PlayerEvent event) {
        return FragaliciousOrigins.ORIGINS.getOrigin(event.getPlayer().getUniqueId());
    }

    private Origin getOrigin(Player player) {
        return FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId());
    }
}
