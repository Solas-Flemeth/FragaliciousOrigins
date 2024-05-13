package org.originsreborn.fragaliciousorigins.origins;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.originsreborn.fragaliciousorigins.origins.configs.GeneralOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

public class Shulk extends Origin{
    public static final GeneralOriginConfig generalOriginConfig;
    public Shulk(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.SHULK, state, customDataString);
    }

    @Override
    public void originTick(int tickNum) {

    }

    @Override
    public void updateStats() {
        setDefaultStats(generalOriginConfig);
    }

    @Override
    public void onDeath(PlayerDeathEvent event) {
        setDefaultStats(generalOriginConfig);
    }


    public static void onReload() {
        generalOriginConfig.loadConfig();
    }

    @Override
    public String serializeCustomData() {
        return null;
    }

    @Override
    public void deserializeCustomData(String customData) {

    }

    @Override
    public String primaryAbilityName() {
        return null;
    }

    @Override
    public String secondaryAbilityName() {
        return null;
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

    static{
        generalOriginConfig = new GeneralOriginConfig(OriginType.SHULK);

    }
}
