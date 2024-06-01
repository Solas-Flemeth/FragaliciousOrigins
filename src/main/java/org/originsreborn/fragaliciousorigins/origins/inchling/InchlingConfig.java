package org.originsreborn.fragaliciousorigins.origins.inchling;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class InchlingConfig extends OriginConfig {
    public InchlingConfig() {
        super(OriginType.INCHLING, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            //Primary Ability
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
            primaryAbilityNode.node("SpeedModifer").set(1.5);
            primaryAbilityNode.node("JumpheightModifier").set(1.5);
            primaryAbilityNode.node("SizeModifer").set(0.2);
            primaryAbilityNode.node("dodgeChance").set(0.5);
        } catch (SerializationException Exception) {

        }
    }

    // Primary Ability - Speed Modifier
    public double getSpeedModifier() {
        return getConfigNode().node("primaryability").node("SpeedModifer").getDouble();
    }

    // Primary Ability - Jump Height Modifier
    public double getJumpHeightModifier() {
        return getConfigNode().node("primaryability").node("JumpheightModifier").getDouble();
    }

    // Primary Ability - Size Modifier
    public double getSizeModifier() {
        return getConfigNode().node("primaryability").node("SizeModifer").getDouble();
    }

    // Primary Ability - Dodge Chance
    public double getDodgeChance() {
        return getConfigNode().node("primaryability").node("dodgeChance").getDouble();
    }

}
