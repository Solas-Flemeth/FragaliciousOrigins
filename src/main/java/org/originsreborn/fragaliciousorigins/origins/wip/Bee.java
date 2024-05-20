package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * - Primary Ability - Pollinate -  have a chance to drop honey in a bottle or honey combs, or random flowers from nearby plants. Nearbly plants may be bonemealed.
 *  - Nearby phytokin will get refilled on saturation, and have a chance to drop some sort of wood material or leaves
 * - Secondary Ability - Toggle levitation and gravity.
 * - Can only drink Honey for food.
 * - Hive Mind - Bees gain regeneration when near other bees up to regen II and passive food regeneration when near phytokin.
 * - Bees have only 7 Hearts
 * - Bees have a 1% chance on hit to deal 10x damage, but set their Health to 1/2 heart
 * - Bees have 40% gravity
 * - Bees get Levitation when crouching
 * - Bees are 50% height
 * - Bees are +10% speed
 */
public class Bee extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.BEE);
    public Bee(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.BEE, state, customDataString);
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
        return OriginDifficulty.MEDIUM;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
