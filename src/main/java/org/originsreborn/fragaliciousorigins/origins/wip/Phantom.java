package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability - Phase - Shifts through a wall infront of you X blocks until the next safe teleport location. No hunger cost (will fail if no safe spot)
 * Secondary Ability - toggle shadowstep
 * - While sprinting, player is in shadowstep
 *  - Shadowstep lets players walk through walls
 *  - Shadowstep consumes hunger when using at a rate of 0.x hunger / second.
 *  - Can exit by stop sprinting or hitting 3 hunger
 * - Phantom has mining fatigue and weakness in direct sunlight.
 * - Phantoms health, mining speed, and size ranges in light level. (0.75x - 1.25x)
 * - Phantom has a dodge chance from 0% to 32% based on light level.
 * - Phantom have night vision in the dark as well as regen.
 * - Phantom is immune to sharpness. Takes bonus from smite.
 * - Phantom takes bonus damage from fire and burns 2x longer.
 */
public class Phantom extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.PHANTOM);
    public Phantom(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.PHANTOM, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(long tickNum) {

    }

    @Override
    public void originParticle(long tickNum) {

    }

    @Override
    public String serializeCustomData() {
        return "";
    }

    @Override
    public void deserializeCustomData(String customData) {

    }

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.HARD;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
