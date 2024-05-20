package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability - Fade - Turns invisible and next shot deals 3.5x Damage  and inflicts wither for 2 seconds - will uninvis afterward. Last 6 seconds.
 * Secondary Ability - Toggle arrow Type
 *  - Standard - 1.5x Damage
 *  - Tracking - 1.1x Damage + Applies glow (10 second)
 *  - Stun Arrow - 0.7x Damage - applies slowness II (4 second) + Nausea and darkness (1.5s)
 * - Bows - Gains 1.5x Effective range.
 * - Crossbows - penetration - reduces the effectiveness of Toughness by 20%
 * - Swings slow with melee weapons and deals less damage
 * - Gains a speed boost on taking damage
 * - Cannot use explosive crossbows
 * - Standing still while crouching will turn you invisible
 * - Crouching applies slow falling
 * - Carnivore
 * - Chance to get extra loot off animals when killed via range.
 * - Deals 1.25x Damage to targets in the air and 2.5x to gliding
 * - Bonus archery xp
 */
public class Huntsman extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.HUNTSMAN);
    public Huntsman(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.HUNTSMAN, state, customDataString);
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
