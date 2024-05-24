package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

public class Giant extends Origin {
    /**
     * Primary Ability - Roars - All nearby hostile mobs get weakness and slowness. Nearby players get Strength and resistance
     * 2.5x player size
     * 1.0x speed
     * 1.2x gravity
     * 1.5x Jump height
     * Increase food usage
     * Increase 2x fall height requirement
     * increase 2x jump height
     * 2.5x build size
     * 2.5x attack range
     * Slower attack speed
     * Increase attack damage
     * 2x Hearts
     * 10% Natural Damage Resistance
     * Bonus damage from Fire & explosions
     */
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.GIANT);
    public Giant(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.GIANT, state, customDataString);
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
