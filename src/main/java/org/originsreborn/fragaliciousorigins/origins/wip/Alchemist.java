package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 *  - primary - enchant XP Boost + reroll enchant KEY
 *  - Secondary - Shift potion type
 *    - Redstone - Increase potion duration
 *    - Glowstone - Increase potion amplifier
 *    - Spider Eye - Changes all negative effects to positive
 *  - Midas Touch
 *    - All 75% chance turn to ores to gold.
 *    - 75% chance for redstone to turn to lapis.
 *    - Effected Diamonds & emeralds have a chance 10% to become golden diamonds
 *  - Romeo & Juliet - cannot trade with villagers
 *  - Keeps XP on death
 *  - Chance to keep XP on enchanting
 *  - 15% damage boost with explosives (crossbows)
 *  - Potion arrows now splash cause lingering AOEs
 *  - MCMMO XP boost for alchemy
 */
public class Alchemist extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ALCHEMIST);
    public Alchemist(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ALCHEMIST, state, customDataString);
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
