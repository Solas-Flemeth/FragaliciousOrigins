package org.originsreborn.fragaliciousorigins.origins.giant;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class GiantConfig extends OriginConfig {
    private int radius, weaknessDuration, weaknessAmplifier, slownessDuration, slownessAmplifier, strengthDuration, strengthAmplifier, hasteDuration, hasteAmplifier;
    private double hungerLossChance;

    public GiantConfig() {
        super(OriginType.GIANT, "unique.yaml");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("radius").set(15);
            primaryAbilityNode.node("weakness").node("duration").set(400);
            primaryAbilityNode.node("weakness").node("amplifier").set(1);
            primaryAbilityNode.node("slowness").node("duration").set(400);
            primaryAbilityNode.node("slowness").node("amplifier").set(1);
            primaryAbilityNode.node("strength").node("duration").set(400);
            primaryAbilityNode.node("strength").node("amplifier").set(1);
            primaryAbilityNode.node("haste").node("duration").set(400);
            primaryAbilityNode.node("haste").node("amplifier").set(1);

            CommentedConfigurationNode foodNode = getConfigNode().node("food");
            foodNode.node("hungerLossChance").set(0.03);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void defineVariables() {
        radius = getConfigNode().node("primaryAbility").node("radius").getInt();
        weaknessDuration = getConfigNode().node("primaryAbility").node("weakness").node("duration").getInt();
        weaknessAmplifier = getConfigNode().node("primaryAbility").node("weakness").node("amplifier").getInt();
        slownessDuration = getConfigNode().node("primaryAbility").node("slowness").node("duration").getInt();
        slownessAmplifier = getConfigNode().node("primaryAbility").node("slowness").node("amplifier").getInt();
        strengthDuration = getConfigNode().node("primaryAbility").node("strength").node("duration").getInt();
        strengthAmplifier = getConfigNode().node("primaryAbility").node("strength").node("amplifier").getInt();
        hasteDuration = getConfigNode().node("primaryAbility").node("haste").node("duration").getInt();
        hasteAmplifier = getConfigNode().node("primaryAbility").node("haste").node("amplifier").getInt();
        hungerLossChance = getConfigNode().node("food").node("hungerLossChance").getDouble();
    }

    public int getPrimaryAbilityRadius() {
        return radius;
    }

    public int getPrimaryAbilityWeaknessDuration() {
        return weaknessDuration;
    }

    public int getPrimaryAbilityWeaknessAmplifier() {
        return weaknessAmplifier;
    }

    public int getPrimaryAbilitySlownessDuration() {
        return slownessDuration;
    }

    public int getPrimaryAbilitySlownessAmplifier() {
        return slownessAmplifier;
    }

    public int getPrimaryAbilityStrengthDuration() {
        return strengthDuration;
    }

    public int getPrimaryAbilityStrengthAmplifier() {
        return strengthAmplifier;
    }

    public int getPrimaryAbilityHasteDuration() {
        return hasteDuration;
    }

    public int getPrimaryAbilityHasteAmplifier() {
        return hasteAmplifier;
    }

    public double getHungerLossChance() {
        return hungerLossChance;
    }
}
