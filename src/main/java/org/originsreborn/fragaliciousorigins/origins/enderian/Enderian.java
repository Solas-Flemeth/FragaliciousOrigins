package org.originsreborn.fragaliciousorigins.origins.enderian;

import io.papermc.paper.entity.LookAnchor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

import java.io.Serializable;
import java.util.*;

import static org.originsreborn.fragaliciousorigins.util.PlayerUtils.setAttribute;

/**
 * Primary ability - teleport on demand (15s/5s)
 * Secondary ability - Teleport on damage (5s/2s). has a chance to dodge damage on hit (25% range, 10% all others)
 * - Using enderpearls speeds up teleportation recharge (No damage). (8s/15s seconds)
 * - has teleport bar. (60s)
 * - 1.20x attack range (1.5x in end)
 * - 1.20x build range  (1.5x in end)
 * - Teleporting grants minor damage resistance for 2 seconds
 * - Gains 4 hearts (total 28 HP) in end.
 * - Water deals rapid damage
 * - Teleports to bed if entering void
 * - 1.5x size
 */
public class Enderian extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ENDERIAN);
    public static final EnderianConfig ENDERIAN_CONFIG = new EnderianConfig();
    private static final Random random = new Random();
    private static final BossBar.Color FLUX_COLOR = BossBar.Color.PURPLE;
    private static final BossBar.Overlay FLUX_OVERLAY = BossBar.Overlay.PROGRESS;
    private int invulnFrames = 0;
    private final String FLUX_KEY;
    private final Set<Material> TRANSPARENT_BLOCKS = transparentMaterials();
    private Integer capacity;

    public Enderian(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ENDERIAN, state, customDataString);
        if (capacity == null) {
            capacity = ENDERIAN_CONFIG.getChargeCapacity();
        }
        FLUX_KEY = getUUID().toString() + "_FLUX";
        FragaliciousOrigins.BOSS_BARS.createBossBar(FLUX_KEY, getFluxComponent(), getFluxPercentage(), FLUX_COLOR, FLUX_OVERLAY, getPlayer());
    }
    @Override
    public void onRemoveOrigin() {
        FragaliciousOrigins.BOSS_BARS.removeBossBar(FLUX_KEY);
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        ENDERIAN_CONFIG.loadConfig();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if(invulnFrames > 0){
            invulnFrames--;
        }
        if (tickNum % 10 == 0) {
            //deal water damage
            if (player.isInWaterOrRain() && !player.hasPotionEffect(PotionEffectType.CONDUIT_POWER) && !player.isDead()) {
                player.damage(4, DamageSource.builder(DamageType.OUTSIDE_BORDER).build());
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 1f, 1.5f);
            }
            if (capacity < ENDERIAN_CONFIG.getChargeCapacity()) {
                if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                    setCapacity(getCapacity() + 1);
                } else {
                    setCapacity(getCapacity() + 3);
                }
            }
            FragaliciousOrigins.BOSS_BARS.updateBossBar(FLUX_KEY, getFluxComponent(), getFluxPercentage(), FLUX_COLOR);
        }
        boolean isOverworld = getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL);
        if(!isOverworld && player.getFireTicks() > 0){
            player.setFireTicks(0);
        }
    }
    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateSphereParticle(Particle.PORTAL, getPlayer().getLocation(), 1, 2);
    }

    /**
     * @param map
     * @return
     */
    @Override
    public @NotNull HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        map.put("capacity", capacity);
        return map;
    }

    /**
     * @param map
     * @throws Exception
     */
    @Override
    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {
        capacity = (Integer) map.get("capacity");
    }

    @Override
    public void primaryAbilityLogic() {
        Player player = getPlayer();
        World world = player.getWorld();
        int cost, range;
        if (world.getEnvironment() == World.Environment.NORMAL) {
            cost = ENDERIAN_CONFIG.getPrimaryOverworldCost();
            range = ENDERIAN_CONFIG.getPrimaryOverworldRange();
        } else {
            cost = ENDERIAN_CONFIG.getPrimaryEndCost();
            range = ENDERIAN_CONFIG.getPrimaryEndRange();
        }
        PotionEffect weakness = player.getPotionEffect(PotionEffectType.WEAKNESS);
        if(weakness !=  null){
            int divider = 2 +  weakness.getAmplifier();
            range = range / divider;
        }
        if (cost > getCapacity()) {
            sendLackOfFlux(player);
            return;
        }
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();
        Location targetLocation = player.getTargetBlock(TRANSPARENT_BLOCKS, range).getLocation();
        targetLocation = findRelativelySafeLocation(targetLocation, -2, 5);
        if(targetLocation != null){
            world.playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1.5f);
            world.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.8f);
            ParticleUtil.generateSphereParticle(Particle.PORTAL, targetLocation, 30, 3.0);
            ParticleUtil.generateSphereParticle(Particle.PORTAL, player.getLocation(), 30, 3.0);
            setCapacity(getCapacity() - cost);
            targetLocation.setYaw(yaw);
            targetLocation.setPitch(pitch);
            getPlayer().teleportAsync(targetLocation, PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT);
            FragaliciousOrigins.BOSS_BARS.updateBossBar(FLUX_KEY, getFluxComponent(), getFluxPercentage(), FLUX_COLOR);
        }
    }

    @Override
    public void secondaryAbilityLogic() {
        secondaryToggle();
    }

    /**
     * @param event
     */
    @Override
    public void onDamage(EntityDamageEvent event) {
        if(invulnFrames > 0){
            event.setCancelled(true);
        }
        if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            Location teleportLocation = getPlayer().getRespawnLocation();
            if (teleportLocation == null) {
                teleportLocation = getPlayer().getServer().getWorlds().get(0).getSpawnLocation();
            }
            getPlayer().teleportAsync(teleportLocation, PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT);
        }
        super.onDamage(event);
        if (isSecondaryEnabled()) {
            Player player = getPlayer();
            Location location = player.getLocation();
            World world = player.getWorld();
            int cost, range;
            if (world.getEnvironment() == World.Environment.NORMAL) {
                cost = ENDERIAN_CONFIG.getSecondaryOverworldCost();
                range = ENDERIAN_CONFIG.getSecondaryOverworldRange();
            } else {
                cost = ENDERIAN_CONFIG.getSecondaryEndCost();
                range = ENDERIAN_CONFIG.getSecondaryEndRange();
            }
            PotionEffect weakness = player.getPotionEffect(PotionEffectType.WEAKNESS);
            if(weakness !=  null){
                int divider = 2 +  weakness.getAmplifier();
                range = range / divider;
            }
            if (cost > capacity) {
                sendLackOfFlux(player);
                return;
            }
            Location targetLocation = null;
            int attempts = 0;
            while (targetLocation == null && attempts < 20) {
                targetLocation = findSafeTeleportLocation(player, range);
                attempts++;
            }
            if (isSafeTeleportLocation(targetLocation)) {
                updateLocationPitchAndYaw(location, targetLocation);
                world.playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1.5f);
                world.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.8f);
                ParticleUtil.generateSphereParticle(Particle.PORTAL, targetLocation, 30, 3.0);
                ParticleUtil.generateSphereParticle(Particle.PORTAL, player.getLocation(), 30, 3.0);
                setCapacity(capacity - cost);
                player.teleportAsync(location, PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT).thenAccept(result -> {
                    if (result) {
                        player.lookAt(location, LookAnchor.EYES);
                    }
                });
            }
        }
        FragaliciousOrigins.BOSS_BARS.updateBossBar(FLUX_KEY, getFluxComponent(), getFluxPercentage(), FLUX_COLOR);
    }

    /**
     * @return
     */
    @Override
    public double getDodgeChance() {
        if (isSecondaryEnabled()) {
            if (getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                return ENDERIAN_CONFIG.getSecondaryOverworldDodgeChance();
            } else {
                return ENDERIAN_CONFIG.getSecondaryEndDodgeChance();
            }
        }
        return super.getDodgeChance();
    }

    /**
     * @param event
     */
    @Override
    public void onIgnite(EntityCombustEvent event) {
        super.onIgnite(event);
        if(event.getEntity().getLocation().getWorld().getEnvironment() != World.Environment.NORMAL){
            event.setCancelled(true);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onTeleport(PlayerTeleportEvent event) {
        super.onTeleport(event);
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        PotionsUtil.addEffect(player, PotionEffectType.RESISTANCE, 0, 30);
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                setCapacity(capacity + ENDERIAN_CONFIG.getEnderpearlOverworldRefill());
            } else {
                setCapacity(capacity + ENDERIAN_CONFIG.getEnderpearlEndRefill());
            }
            FragaliciousOrigins.BOSS_BARS.updateBossBar(FLUX_KEY, getFluxComponent(), getFluxPercentage(), FLUX_COLOR);
        }
        invulnFrames = ENDERIAN_CONFIG.getInvulnFrames();
    }

    /**
     * @param event
     */
    @Override
    public void onWorldChange(PlayerChangedWorldEvent event) {
        super.onWorldChange(event);
        if (event.getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            enterOverworld();
        } else {
            enterOtherworld();
        }
    }

    public void enterOverworld() {
        Player player = getPlayer();
        setAttribute(player, Attribute.GENERIC_MAX_HEALTH, MAIN_ORIGIN_CONFIG.getMaxHealth());
        setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getBlockInteractRange());
        setAttribute(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, MAIN_ORIGIN_CONFIG.getPlayerEntityInteractRange());
    }

    public void enterOtherworld() {
        Player player = getPlayer();
        setAttribute(player, Attribute.GENERIC_MAX_HEALTH, ENDERIAN_CONFIG.getHealth());
        setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, ENDERIAN_CONFIG.getBlockInteractionRange());
        setAttribute(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, ENDERIAN_CONFIG.getEntityInteractionRange());
    }

    private boolean isSolidBlock(Location location) {
        // Check if the block is solid and the player can suffocate in it
        return location.getBlock().getType().isSolid();
    }


    private boolean isSafeTeleportLocation(Location targetLocation) {
        if (targetLocation == null) {
            return false;
        }
        Location feetLocation = targetLocation.clone().add(0, 1, 0);
        Location torsoLocation = targetLocation.clone().add(0, 2, 0);
        Location headLocation = targetLocation.clone().add(0, 3, 0);
        return !isSolidBlock(targetLocation) &&
                !isSolidBlock(feetLocation) &&
                !isSolidBlock(torsoLocation) &&
                !isSolidBlock(headLocation);
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        if (capacity < 0) {
            capacity = 0;
        }
        this.capacity = capacity;
    }
    private Location findRelativelySafeLocation(Location targetLocation, double yOffset, int attempts) {
        Location relativeLocation = targetLocation.clone();
        double ySpot = targetLocation.getY();
        for(int i = 0; i < attempts; i++){
            relativeLocation.setY(ySpot -yOffset + i);
            if(isSafeTeleportLocation(relativeLocation)){
                return relativeLocation;
            }
        }
        return null;
    }
    private Location findSafeTeleportLocation(Player player, int range) {
        Location targetLocation = player.getTargetBlock(TRANSPARENT_BLOCKS, range).getLocation();
        if (targetLocation != null) {
            int randomXOffset = random.nextInt(range) - range / 2;
            int randomZOffset = random.nextInt(range) - range / 2;

            for (int i = 1; i <= range; i++) {
                int offsetX = (i + randomXOffset) % range;
                int offsetZ = (i + randomZOffset) % range;

                Location location = targetLocation.clone().add(offsetX, 0, offsetZ);
                if (isSafeTeleportLocation(location)) {
                    return location;
                }

                location = targetLocation.clone().add(offsetX, 1, offsetZ);
                if (isSafeTeleportLocation(location)) {
                    return location;
                }

                location = targetLocation.clone().add(offsetX, 2, offsetZ);
                if (isSafeTeleportLocation(location)) {
                    return location;
                }
                // Calculate the next offsetX and offsetZ values
                randomXOffset = random.nextInt(range) - range / 2;
                randomZOffset = random.nextInt(range) - range / 2;
            }
        }
        return null;
    }
    private float getFluxPercentage() {
        float current = getCapacity();
        float max = ENDERIAN_CONFIG.getChargeCapacity();
        float percentage = current / max;
        if (percentage < 0.0) {
            return 0;
        } else if (percentage > 1.0f) {
            return 1.0f;
        } else {
            return percentage;
        }
    }

    private Component getFluxComponent() {
        return Component.text("Flux Charge " + formatFloatPercentage(getFluxPercentage())).color(TextColor.color(0x9D38FF));
    }
    private void sendLackOfFlux(Player player){
        player.sendActionBar(Component.text("You do not have enough flux to teleport").color(errorColor()));
        player.playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
    }
    private Location updateLocationPitchAndYaw(Location startLocation, Location finalLocation) {
        // Calculate the differences in the coordinates
        double deltaX = startLocation.getX() - finalLocation.getX();
        double deltaY = startLocation.getY() - finalLocation.getY();
        double deltaZ = startLocation.getZ() - finalLocation.getZ();

        // Calculate the yaw
        double yaw = Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90;

        // Calculate the distance on the XZ plane
        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        // Calculate the pitch
        double pitch = Math.toDegrees(Math.atan(deltaY / distanceXZ));

        // Update the final location with the calculated pitch and yaw
        finalLocation.setYaw(startLocation.getYaw());
        finalLocation.setPitch((float) pitch);

        return finalLocation;
    }
    private static Set<Material> transparentMaterials(){
        return Set.of(Material.SHORT_GRASS, Material.AIR, Material.CAVE_AIR, Material.VINE,
                Material.CAVE_VINES, Material.SPORE_BLOSSOM, Material.SUGAR_CANE,
                Material.TALL_GRASS, Material.DANDELION, Material.SNOW, Material.MOSS_CARPET,
                Material.BLACK_CARPET, Material.BLUE_CARPET, Material.CYAN_CARPET, Material.BROWN_CARPET,
                Material.GRAY_CARPET, Material.GREEN_CARPET, Material.LIGHT_BLUE_CARPET, Material.LIGHT_GRAY_CARPET,
                Material.LIME_CARPET, Material.MAGENTA_CARPET, Material.ORANGE_CARPET, Material.PINK_CARPET,
                Material.PURPLE_CARPET, Material.RED_CARPET, Material.WHITE_CARPET, Material.YELLOW_CARPET);
    }
}
