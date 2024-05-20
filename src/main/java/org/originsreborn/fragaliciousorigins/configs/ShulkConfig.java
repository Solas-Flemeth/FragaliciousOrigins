package org.originsreborn.fragaliciousorigins.configs;

import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Paths;

public class ShulkConfig extends OriginConfig {

    public ShulkConfig() {
        super(Paths.get(FragaliciousOrigins.INSTANCE.getDataFolder().getPath() + File.separator + OriginType.SHULK.name().toLowerCase()),
                Paths.get(FragaliciousOrigins.INSTANCE.getDataFolder().getPath() + File.separator + OriginType.SHULK.name().toLowerCase() + File.separator + "specific.yml")
        );
    }

    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
            secondaryAbilityNode.node("radius").set(10);
            secondaryAbilityNode.node("playerDuration").set(60);
            secondaryAbilityNode.node("enemyDuration").set(200);
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
}
