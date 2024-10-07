package org.originsreborn.fragaliciousorigins.origins.shulk;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;


public class ShulkConfig extends OriginConfig {
    private int radius, playerDuration, enemyDuration, resistanceDuration,resistanceAmp;
    public ShulkConfig() {
        super(OriginType.SHULK, "unique.yaml");
    }

    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
                secondaryAbilityNode.node("radius").set(10);
                secondaryAbilityNode.node("playerDuration").set(200);
                secondaryAbilityNode.node("enemyDuration").set(60);
            CommentedConfigurationNode resistanceNode = getConfigNode().node("resistance");
                resistanceNode.node("duration").set(200);
                resistanceNode.node("amplifier").set(0);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */

    @Override
    public void defineVariables() {
        radius = getConfigNode().node("secondaryAbility").node("radius").getInt();
        playerDuration = getConfigNode().node("secondaryAbility").node("playerDuration").getInt();
        enemyDuration = getConfigNode().node("secondaryAbility").node("enemyDuration").getInt();
        resistanceAmp = getConfigNode().node("resistance").node("amplifier").getInt();
        resistanceDuration =getConfigNode().node("resistance").node("duration").getInt();
    }

    public int getSecondaryRadius() {
        return radius;
    }

    public int getSecondaryPlayerDuration() {
        return playerDuration;
    }

    public int getSecondaryEnemyDuration() {
        return enemyDuration;
    }

    public int getResistanceDuration() {
        return resistanceDuration;
    }

    public int getResistanceAmplifier() {
        return resistanceAmp;
    }
}
