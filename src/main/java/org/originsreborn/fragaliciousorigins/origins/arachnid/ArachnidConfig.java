package org.originsreborn.fragaliciousorigins.origins.arachnid;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;


public class ArachnidConfig extends OriginConfig{
    private float climbSpeed;
    public ArachnidConfig() {
        super(OriginType.ARACHNID, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try {
        CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("amount").set(3);
            primaryAbilityNode.node("spawnRadius").set(10);
        CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
            secondaryAbilityNode.node("climbSpeed").set(0.1f);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {

    }
}
