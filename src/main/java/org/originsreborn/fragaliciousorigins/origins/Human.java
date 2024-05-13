package org.originsreborn.fragaliciousorigins.origins;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.originsreborn.fragaliciousorigins.origins.configs.GeneralOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

public class Human extends Origin {
    public static final GeneralOriginConfig generalOriginConfig;

    static {
        generalOriginConfig = new GeneralOriginConfig(OriginType.HUMAN);
    }

    public Human(UUID uuid) {
        super(uuid, OriginType.HUMAN, OriginState.NORMAL);
    }

    public static void onReload() {
        generalOriginConfig.loadConfig();
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
}
