package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Has heat tiers that buff or debuff
 * Primary ability - eats food
 * Secondary Ability - Purges negative effects, give fire resistance to nearby players for 15 seconds, and grants heat
 * - Water damages
 * - Magic resistant (33%?)
 * - On fire gives damage bonus
 * - All melee and range attacks ignites targets if above certain heat
 * - Crouching will light a fire below you if you can build there
 * - Immune to sharpness - bonus damage from Brine (For now smite) (Custom Enchant 1.21)
 */
public class Blazeborn extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.BLAZEBORN);
    public Blazeborn(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.BLAZEBORN, state, customDataString);
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
