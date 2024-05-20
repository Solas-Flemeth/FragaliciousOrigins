package org.originsreborn.fragaliciousorigins.origins.complete;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

public class Human extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.HUMAN);


    public Human(UUID uuid) {
        super(uuid, OriginType.HUMAN, OriginState.NORMAL);
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

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
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
}
