package org.originsreborn.fragaliciousorigins.origins.arachnid;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PlayerUtils;
import org.originsreborn.fragaliciousorigins.util.enums.DayCycle;
import org.originsreborn.fragaliciousorigins.util.enums.Food;
import org.originsreborn.fragaliciousorigins.util.enums.MoonCycle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Primary - Spider web
 *  - Beams to a wall
 *  - Webs target if hit
 * Secondary - Toggle Climb
 * - Nightvision
 * - Moon cycle - chance to receive a random positive potion effect closer to full moon
 * - 1.5x longer combat reach
 * - 3x longer build reach
 * - Bows can spawn webs on arrow land (Chance %)
 * - Chance to spawn web and poison target on hit
 * - Lower HP
 * - Meat Only
 * - lower fall damage
 * - Immune to sharpness - bonus damage from bane of arthapods
 */

public class  Arachnid extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ARACHNID);
    public static final ArachnidConfig ARACHNID_CONFIG = new ArachnidConfig();
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
    }

    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateParticleAtLocation(Particle.SMOKE, getPlayer().getLocation(), 1);
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
    private void onMoonCycleChance(MoonCycle moonCycle){
        Player player = getPlayer();

    }
    private void onClimb() {
        Player player = getPlayer();
        if (isSecondaryEnabled()) {
            // Get player's location and direction
            Location loc = player.getLocation();
            Vector direction = loc.getDirection();

            // Check blocks in front, above, and below the player
            Block blockAhead = loc.add(direction).getBlock();
            Block blockAbove = loc.getBlock().getRelative(BlockFace.UP);
            Block blockBelow = loc.getBlock().getRelative(BlockFace.DOWN);

            // Check if there's a solid block in any relevant direction (including diagonals)
            boolean hasWallAhead = blockAhead.getType().isSolid();
            boolean hasCeilingAbove = blockAbove.getType().isSolid();
            boolean hasFloorBelow = blockBelow.getType().isSolid();

            // Latch onto a wall or ceiling if a solid block is detected ahead or above
            if (hasWallAhead || hasCeilingAbove) {
                // Adjust player's position slightly towards the wall if needed
                if (!hasWallAhead) {
                    loc.add(direction.multiply(0.2)); // Slight nudge towards the wall
                    player.teleport(loc);
                }

                // Stop player movement to simulate latching and allow climbing
                player.setVelocity(new Vector(0, 0, 0));
                player.setAllowFlight(true);  // Allow the player to remain in place
                player.setFlying(true);  // Prevent falling

                // Optional: Trigger latching sound or particles here

            } else if (player.isFlying()) {
                // If no wall or ceiling is nearby and the player is latched, detach them
                player.setAllowFlight(false);
                player.setFlying(false);
                PlayerUtils.resetFlySpeed(player);
            }
        }
    }



    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        ARACHNID_CONFIG.loadConfig();
    }
}
