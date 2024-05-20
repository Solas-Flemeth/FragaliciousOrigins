package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

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
    public Enderian(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ENDERIAN, state, customDataString);
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
