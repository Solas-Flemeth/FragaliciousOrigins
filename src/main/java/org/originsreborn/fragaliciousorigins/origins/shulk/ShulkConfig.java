package org.originsreborn.fragaliciousorigins.origins.shulk;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;


public class ShulkConfig extends OriginConfig {

    public ShulkConfig() {
        super(OriginType.SHULK, "unique");
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

    public int getSecondaryRadius() {
        return getConfigNode().node("secondaryAbility").node("radius").getInt();
    }

    public int getSecondaryPlayerDuration() {
        return getConfigNode().node("secondaryAbility").node("playerDuration").getInt();
    }

    public int getSecondaryEnemyDuration() {
        return getConfigNode().node("secondaryAbility").node("enemyDuration").getInt();
    }

    public int getResistanceDuration() {
        return getConfigNode().node("resistance").node("duration").getInt();
    }

    public int getResistanceAmplifier() {
        return getConfigNode().node("resistance").node("amplifier").getInt();
    }
}
