package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability - Toggle - Flee - Gains +50% speed and + 50% jump height and a 40% chance to dodge, but cannot attack. Size becomes 0.2.
 * - 0.24 x Scale
 * - 50% gravity
 * - 6 hearts
 * - 20% base chance to dodge
 * - 0.7x attack range
 * - 0.7x build range
 * - Better Food Managment
 */
public class Inchling extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG =  new MainOriginConfig(OriginType.INCHLING);
    public Inchling(UUID uuid, OriginState state, String customData) {
        super(uuid, OriginType.INCHLING, state, customData);
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

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.MEDIUM;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
