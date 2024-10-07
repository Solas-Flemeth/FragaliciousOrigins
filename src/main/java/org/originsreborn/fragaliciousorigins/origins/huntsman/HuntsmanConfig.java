package org.originsreborn.fragaliciousorigins.origins.huntsman;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class HuntsmanConfig extends OriginConfig {
    private double primaryAbilityDamageMultiplier, broadArrowDamageMultiplier, healMultiplier,
            stunArrowDamageMultiplier, aerialEnemyGlideMultiplier, crossbowDamageMultiplierPerToughness,
            otherBowVelocityMultiplier;
    private int primaryAbilityDuration, strengthTicksPerDamage, stunArrowSlownessDuration, stunArrowSlownessAmplifier,
            stunArrowDarknessDuration, stunArrowDarknessAmplifier, stunArrowMiningFatigueDuration, strengthAmplifier,
            stunArrowMiningFatigueAmplifier, crouchDuration, speedOnDamageDuration, speedOnDamageAmplifier;
    private boolean stunArrowSlownessEnabled, stunArrowDarknessEnabled, stunArrowMiningFatigueEnabled;

    public HuntsmanConfig() {
        super(OriginType.HUNTSMAN, "unique.yaml");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("damageMultiplier").set(2.5);
            primaryAbilityNode.node("duration").set(6);

            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");

            CommentedConfigurationNode broadArrowNode = secondaryAbilityNode.node("broadArrow");
            broadArrowNode.node("damageMultiplier").set(1.3);

            CommentedConfigurationNode buffArrow = secondaryAbilityNode.node("buffArrow");
            buffArrow.node("healMultiplier").set(0.3);
            buffArrow.node("strengthAmp").set(2);
            buffArrow.node("strengthTickPerDamage").set(2);

            CommentedConfigurationNode stunArrowNode = secondaryAbilityNode.node("stunArrow");
            stunArrowNode.node("damageMultiplier").set(0.6);

            CommentedConfigurationNode slownessNode = stunArrowNode.node("slowness");
            slownessNode.node("enabled").set(true);
            slownessNode.node("duration").set(80);
            slownessNode.node("amplifier").set(1);

            CommentedConfigurationNode darknessNode = stunArrowNode.node("darkness");
            darknessNode.node("enabled").set(true);
            darknessNode.node("duration").set(10);
            darknessNode.node("amplifier").set(0);

            CommentedConfigurationNode miningFatigueNode = stunArrowNode.node("miningFatigue");
            miningFatigueNode.node("enabled").set(true);
            miningFatigueNode.node("duration").set(40);
            miningFatigueNode.node("amplifier").set(0);

            CommentedConfigurationNode crouchingNode = getConfigNode().node("crouching");
            crouchingNode.node("duration").set(6);

            CommentedConfigurationNode aerialNode = getConfigNode().node("aerialDamage");
            aerialNode.node("enemyGlideMultiplier").set(2.5);

            CommentedConfigurationNode speedOnDamageNode = getConfigNode().node("speedOnDamage");
            speedOnDamageNode.node("duration").set(60);
            speedOnDamageNode.node("amplifier").set(1);

            CommentedConfigurationNode otherNode = getConfigNode().node("other");
            otherNode.node("crossbowDamageMultiplierPerToughness").set(0.3);
            otherNode.node("bowVelocityMultiplier").set(1.33);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void defineVariables() {
        primaryAbilityDamageMultiplier = getConfigNode().node("primaryAbility").node("damageMultiplier").getDouble();
        primaryAbilityDuration = getConfigNode().node("primaryAbility").node("duration").getInt();

        broadArrowDamageMultiplier = getConfigNode().node("secondaryAbility").node("broadArrow").node("damageMultiplier").getDouble();

        healMultiplier = getConfigNode().node("secondaryAbility").node("buffArrow").node("healMultiplier").getDouble();
        strengthAmplifier = getConfigNode().node("secondaryAbility").node("buffArrow").node("strengthAmp").getInt();
        strengthTicksPerDamage = getConfigNode().node("secondaryAbility").node("buffArrow").node("strengthTickPerDamage").getInt();

        stunArrowDamageMultiplier = getConfigNode().node("secondaryAbility").node("stunArrow").node("damageMultiplier").getDouble();

        stunArrowSlownessEnabled = getConfigNode().node("secondaryAbility").node("stunArrow").node("slowness").node("enabled").getBoolean();
        stunArrowSlownessDuration = getConfigNode().node("secondaryAbility").node("stunArrow").node("slowness").node("duration").getInt();
        stunArrowSlownessAmplifier = getConfigNode().node("secondaryAbility").node("stunArrow").node("slowness").node("amplifier").getInt();

        stunArrowDarknessEnabled = getConfigNode().node("secondaryAbility").node("stunArrow").node("darkness").node("enabled").getBoolean();
        stunArrowDarknessDuration = getConfigNode().node("secondaryAbility").node("stunArrow").node("darkness").node("duration").getInt();
        stunArrowDarknessAmplifier = getConfigNode().node("secondaryAbility").node("stunArrow").node("darkness").node("amplifier").getInt();

        stunArrowMiningFatigueEnabled = getConfigNode().node("secondaryAbility").node("stunArrow").node("miningFatigue").node("enabled").getBoolean();
        stunArrowMiningFatigueDuration = getConfigNode().node("secondaryAbility").node("stunArrow").node("miningFatigue").node("duration").getInt();
        stunArrowMiningFatigueAmplifier = getConfigNode().node("secondaryAbility").node("stunArrow").node("miningFatigue").node("amplifier").getInt();

        crouchDuration = getConfigNode().node("crouching").node("duration").getInt();

        aerialEnemyGlideMultiplier = getConfigNode().node("aerialDamage").node("enemyGlideMultiplier").getDouble();

        speedOnDamageDuration = getConfigNode().node("speedOnDamage").node("duration").getInt();
        speedOnDamageAmplifier = getConfigNode().node("speedOnDamage").node("amplifier").getInt();

        crossbowDamageMultiplierPerToughness = getConfigNode().node("other").node("crossbowDamageMultiplierPerToughness").getDouble();
        otherBowVelocityMultiplier = getConfigNode().node("other").node("bowVelocityMultiplier").getDouble();
    }

    public double getPrimaryAbilityDamageMultiplier() {
        return primaryAbilityDamageMultiplier;
    }

    public int getPrimaryAbilityDuration() {
        return primaryAbilityDuration;
    }

    public double getBroadArrowDamageMultiplier() {
        return broadArrowDamageMultiplier;
    }

    public double getHealMultiplier() {
        return healMultiplier;
    }

    public int getStrengthAmplifier(){
        return strengthAmplifier;
    }
    public int getStrengthTicksPerDamage() {
        return strengthTicksPerDamage;
    }

    public double getStunArrowDamageMultiplier() {
        return stunArrowDamageMultiplier;
    }

    public boolean getStunArrowSlownessEnabled() {
        return stunArrowSlownessEnabled;
    }

    public int getStunArrowSlownessDuration() {
        return stunArrowSlownessDuration;
    }

    public int getStunArrowSlownessAmplifier() {
        return stunArrowSlownessAmplifier;
    }

    public boolean getStunArrowDarknessEnabled() {
        return stunArrowDarknessEnabled;
    }

    public int getStunArrowDarknessDuration() {
        return stunArrowDarknessDuration;
    }

    public int getStunArrowDarknessAmplifier() {
        return stunArrowDarknessAmplifier;
    }

    public boolean getStunArrowMiningFatigueEnabled() {
        return stunArrowMiningFatigueEnabled;
    }

    public int getStunArrowMiningFatigueDuration() {
        return stunArrowMiningFatigueDuration;
    }

    public int getStunArrowMiningFatigueAmplifier() {
        return stunArrowMiningFatigueAmplifier;
    }

    public int getCrouchDuration() {
        return crouchDuration;
    }

    public double getAerialEnemyGlideMultiplier() {
        return aerialEnemyGlideMultiplier;
    }

    public int getSpeedOnDamageDuration() {
        return speedOnDamageDuration;
    }

    public int getSpeedOnDamageAmplifier() {
        return speedOnDamageAmplifier;
    }

    public double getCrossbowDamageMultiplierPerToughness() {
        return crossbowDamageMultiplierPerToughness;
    }

    public double getOtherBowVelocityMultiplier() {
        return otherBowVelocityMultiplier;
    }
}
