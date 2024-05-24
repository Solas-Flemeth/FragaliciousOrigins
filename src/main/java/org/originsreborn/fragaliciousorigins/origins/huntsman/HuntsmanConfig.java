package org.originsreborn.fragaliciousorigins.origins.huntsman;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;


public class HuntsmanConfig extends OriginConfig {
    public HuntsmanConfig() {
        super(OriginType.HUNTSMAN, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            //Primary Ability
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
                primaryAbilityNode.node("damageMultiplier").set(2.5);
                primaryAbilityNode.node("duration").set(6);
            CommentedConfigurationNode secondaryabilityNode = getConfigNode().node("secondaryability");
                //Broad Arrow Secondary
                CommentedConfigurationNode broadArrowNode = secondaryabilityNode.node("broadArrow");
                    broadArrowNode.node("damageMultiplier").set(1.3);
                //Tracking Arrow Secondary
                CommentedConfigurationNode trackingArrowNode = secondaryabilityNode.node("trackingArrow");
                    trackingArrowNode.node("damageMultiplier").set(1.1);
                    trackingArrowNode.node("duration").set(160);
                //Stun Arrow Secondary
                CommentedConfigurationNode stunArrowNode = secondaryabilityNode.node("stunArrow");
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
                crouchingNode.node("duration").set(6); //checks in 5 second intervals to reapply
            CommentedConfigurationNode aerialNode = getConfigNode().node("aerialDamage");
                aerialNode.node("enemyGlideMultiplier").set(2.5);
            CommentedConfigurationNode speedOnDamageNode = getConfigNode().node("speedOnDamage");
                speedOnDamageNode.node("duration").set(60);
                speedOnDamageNode.node("amplifier").set(1);
            CommentedConfigurationNode otherNode = getConfigNode().node("other");
                otherNode.node("crossbowDamageMultiplierPerToughness").set(0.3);
                otherNode.node("bowVelocityMultiplier").set(1.33);
        } catch (SerializationException Exception) {

        }
    }
    // Primary Ability
    public double getPrimaryAbilityDamageMultiplier() {
        return getConfigNode().node("primaryability").node("damageMultiplier").getDouble();
    }

    public int getPrimaryAbilityDuration() {
        return getConfigNode().node("primaryability").node("duration").getInt();
    }

    // Secondary Ability - Broad Arrow
    public double getBroadArrowDamageMultiplier() {
        return getConfigNode().node("secondaryability").node("broadArrow").node("damageMultiplier").getDouble();
    }

    // Secondary Ability - Tracking Arrow
    public double getTrackingArrowDamageMultiplier() {
        return getConfigNode().node("secondaryability").node("trackingArrow").node("damageMultiplier").getDouble();
    }

    public int getTrackingArrowDuration() {
        return getConfigNode().node("secondaryability").node("trackingArrow").node("duration").getInt();
    }

    // Secondary Ability - Stun Arrow
    public double getStunArrowDamageMultiplier() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("damageMultiplier").getDouble();
    }

    public boolean getStunArrowSlownessEnabled() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("slowness").node("enabled").getBoolean();
    }

    public int getStunArrowSlownessDuration() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("slowness").node("duration").getInt();
    }

    public int getStunArrowSlownessAmplifier() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("slowness").node("amplifier").getInt();
    }

    public boolean getStunArrowDarknessEnabled() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("darkness").node("enabled").getBoolean();
    }

    public int getStunArrowDarknessDuration() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("darkness").node("duration").getInt();
    }

    public int getStunArrowDarknessAmplifier() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("darkness").node("amplifier").getInt();
    }

    public boolean getStunArrowMiningFatigueEnabled() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("miningFatigue").node("enabled").getBoolean();
    }

    public int getStunArrowMiningFatigueDuration() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("miningFatigue").node("duration").getInt();
    }

    public int getStunArrowMiningFatigueAmplifier() {
        return getConfigNode().node("secondaryability").node("stunArrow").node("miningFatigue").node("amplifier").getInt();
    }
    // Crouching
    public int getCrouchDuration() {
        return getConfigNode().node("crouching").node("duration").getInt();
    }


    public double getAerialEnemyGlideMultiplier() {
        return getConfigNode().node("aerialDamage").node("enemyGlideMultiplier").getDouble();
    }

    // Speed on Damage
    public int getSpeedOnDamageDuration() {
        return getConfigNode().node("speedOnDamage").node("duration").getInt();
    }

    public int getSpeedOnDamageAmplifier() {
        return getConfigNode().node("speedOnDamage").node("amplifier").getInt();
    }

    // Other
    public double getCrossbowDamageMultiplierPerToughness() {
        return getConfigNode().node("other").node("crossbowDamageMultiplierPerToughness").getDouble();
    }

    public double getOtherBowVelocityMultiplier() {
        return getConfigNode().node("other").node("bowVelocityMultiplier").getDouble();
    }

}
