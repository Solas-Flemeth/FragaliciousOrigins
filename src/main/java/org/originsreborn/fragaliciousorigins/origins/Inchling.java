package org.originsreborn.fragaliciousorigins.origins;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.originsreborn.fragaliciousorigins.origins.configs.GeneralOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

public class Inchling extends Origin {
    public static final GeneralOriginConfig generalOriginConfig =  new GeneralOriginConfig(OriginType.INCHLING);
    public Inchling(UUID uuid, OriginState state) {
        super(uuid, OriginType.INCHLING, state);
    }


    @Override
    public void originTick(int tickNum) {

    }

    @Override
    public void updateStats() {
        setDefaultStats(generalOriginConfig);
    }

    public static void onReload() {
        generalOriginConfig.loadConfig();

    }
    @Override
    public void onDeath(PlayerDeathEvent event) {
        setDefaultStats(generalOriginConfig);
    }

    @Override
    public String serializeCustomData() {
        return "";
    }

    @Override
    public void deserializeCustomData(String customData) {

    }

    @Override
    public String primaryAbilityName() {
        return "";
    }

    @Override
    public String secondaryAbilityName() {
        return "";
    }

    @Override
    public int getPrimaryMaxCooldown() {
        return 0;
    }

    @Override
    public int getSecondaryMaxCooldown() {
        return 0;
    }

    @Override
    public OriginDifficulty getDifficulty() {
        return null;
    }
}
