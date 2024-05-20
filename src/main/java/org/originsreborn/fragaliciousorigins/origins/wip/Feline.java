package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 *  Primary Ability: Leaps a distance if looking above crosshair - gains damage boost temporarily
 *  Secondary ability: Dashes
 *  - Insane speed
 *  - 2.1x Jump Height
 *  - 0.66x player height
 *  - Doesn't alert Shriekers + shulk sensors
 *  - Faster attack speed
 *  - Nightvision
 *  - Carnivore
 *  - 15% slower mine speed
 *  - Being wet slows them down
 *  - Minimal fall damage
 *  - Double step height
 *  - 1/8 chance to avoid death
 *  - 1.5x gravity
 */
public class Feline extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.FELINE);
    public Feline(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.FELINE, state, customDataString);
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
        return OriginDifficulty.EASY;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
