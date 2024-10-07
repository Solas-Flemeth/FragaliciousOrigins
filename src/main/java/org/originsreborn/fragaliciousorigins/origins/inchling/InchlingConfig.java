package org.originsreborn.fragaliciousorigins.origins.inchling;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class InchlingConfig extends OriginConfig {
    private double speedModifier, jumpHeightModifier, sizeModifier, dodgeChance, hungerLossCancelChance, saturationGainChance;

    public InchlingConfig() {
        super(OriginType.INCHLING, "unique.yaml");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("speedModifier").set(1.5);
            primaryAbilityNode.node("jumpHeightModifier").set(1.5);
            primaryAbilityNode.node("sizeModifier").set(0.2);
            primaryAbilityNode.node("dodgeChance").set(0.5);

            CommentedConfigurationNode foodNode = getConfigNode().node("food");
            foodNode.node("hungerLossCancelChance").set(0.25);
            foodNode.node("saturationGainChance").set(0.05);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void defineVariables() {
        speedModifier = getConfigNode().node("primaryAbility").node("speedModifier").getDouble();
        jumpHeightModifier = getConfigNode().node("primaryAbility").node("jumpHeightModifier").getDouble();
        sizeModifier = getConfigNode().node("primaryAbility").node("sizeModifier").getDouble();
        dodgeChance = getConfigNode().node("primaryAbility").node("dodgeChance").getDouble();
        hungerLossCancelChance = getConfigNode().node("food").node("hungerLossCancelChance").getDouble();
        saturationGainChance = getConfigNode().node("food").node("saturationGainChance").getDouble();
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public double getJumpHeightModifier() {
        return jumpHeightModifier;
    }

    public double getSizeModifier() {
        return sizeModifier;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public double getHungerLossCancelChance() {
        return hungerLossCancelChance;
    }

    public double getSaturationGainChance() {
        return saturationGainChance;
    }
}
