package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Active Ability - Fertility Bloom - Breeds nearby animals and bonemeals nearby plants. Bees Origins will gain saturation when you use this ability
 * Secondary Ability - Adaptation - Scans nearby areas to determine a handful of adaptations.
 *   -You may have 1 type of bark based on dimension
 *     - Sulfuric Bark - Regenerates food from lava slowly and is immune to fire. Exposure to Water can cause the phytokin to randomly explode.  Cannot perform photosynthesis - yellow particle
 *     - Chorus Bark - naturally regenerates food and saturation over time. If you have saturation. Small chance to teleport randomly and remove a small portion of saturation - purple particle
 *     - Oak Bark - Exposure to sunlight or standing directly ontop of a Beacon regenerates food. Phytokin takes 2x fire damage and 3x burn duration, but gains natural toughness - green particle
 *   - Environmental Factors. You can have as many as you like
 *     - Water - Grows larger, and attacks for more damage, but attack slower. No more weakness
 *     - Plants - Grows additional thorns that applies wither on being hit but cannot regen food
 *     - Stone - Moves slower but has bonus toughness
 *  - Peaceful - has constant weakness II, but deals wither for 2s on attack
 *  - Thorns - has poisonous thorns that deal poison damage when attacked
 *  - Bonus Herbalism  + woodcutting XP
 *  - New drops for specific herbalism / wood cutting materials (Golden carrots or Golden Apples, glistening melons)
 *  - Has a chance to drop 2x loot when harvesting plants
 */
public class Phytokin extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.PHYTOKIN);
    public Phytokin(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.PHYTOKIN, state, customDataString);
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
    public String serializeCustomData() {
        return "";
    }

    @Override
    public void deserializeCustomData(String customData) {

    }

    @Override
    public void primaryAbilityLogic() {

    }

    @Override
    public void secondaryAbilityLogic() {

    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
