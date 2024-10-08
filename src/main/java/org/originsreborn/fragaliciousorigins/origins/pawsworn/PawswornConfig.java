package org.originsreborn.fragaliciousorigins.origins.pawsworn;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class PawswornConfig extends OriginConfig {
    private float leapVelocity, dashVelocity;
    private int speedDuration, speedAmplifier, strengthDuration, strengthAmplifier, dashCooldown, weaknessDuration, weaknessAmplifier;
    private double nineLivesChance;

    public PawswornConfig() {
        super(OriginType.PAWSWORN, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("leapVelocity").set(2.5f);
            primaryAbilityNode.node("speedDuration").set(60);
            primaryAbilityNode.node("speedAmplifier").set(0);
            primaryAbilityNode.node("strengthDuration").set(60);
            primaryAbilityNode.node("strengthAmplifier").set(3);

            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
            secondaryAbilityNode.node("dashVelocity").set(1.1f);
            secondaryAbilityNode.node("cooldown").set(15);

            getConfigNode().node("nineLivesChance").set(0.12);

            CommentedConfigurationNode waterWeaknessNode = getConfigNode().node("waterWeakness");
            waterWeaknessNode.node("weaknessDuration").set(5);
            waterWeaknessNode.node("amplifier").set(1);
        } catch (Exception e) {
            // Handle exception
        }
    }

    @Override
    public void defineVariables() {
        leapVelocity = getConfigNode().node("primaryAbility").node("leapVelocity").getFloat();
        speedDuration = getConfigNode().node("primaryAbility").node("speedDuration").getInt();
        speedAmplifier = getConfigNode().node("primaryAbility").node("speedAmplifier").getInt();
        strengthDuration = getConfigNode().node("primaryAbility").node("strengthDuration").getInt();
        strengthAmplifier = getConfigNode().node("primaryAbility").node("strengthAmplifier").getInt();

        dashVelocity = getConfigNode().node("secondaryAbility").node("dashVelocity").getFloat();
        dashCooldown = getConfigNode().node("secondaryAbility").node("cooldown").getInt();
        nineLivesChance = getConfigNode().node("nineLivesChance").getDouble();

        weaknessDuration = getConfigNode().node("waterWeakness").node("weaknessDuration").getInt();
        weaknessAmplifier = getConfigNode().node("waterWeakness").node("amplifier").getInt();
    }

    // Getter methods
    public float getLeapVelocity() {
        return leapVelocity;
    }

    public int getSpeedDuration() {
        return speedDuration;
    }

    public int getSpeedAmplifier() {
        return speedAmplifier;
    }

    public int getStrengthDuration() {
        return strengthDuration;
    }

    public int getStrengthAmplifier() {
        return strengthAmplifier;
    }

    public float getDashVelocity() {
        return dashVelocity;
    }

    public int getDashCooldown() {
        return dashCooldown;
    }

    public double getNineLivesChance() {
        return nineLivesChance;
    }

    public int getWeaknessDuration() {
        return weaknessDuration;
    }

    public int getWeaknessAmplifier() {
        return weaknessAmplifier;
    }
}
