package org.originsreborn.fragaliciousorigins.origins.merling;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class MerlingConfig extends OriginConfig {
    public MerlingConfig() {
        super(OriginType.MERLING, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
                primaryAbilityNode.node("swimMultiplier").set(2.5);
            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
                secondaryAbilityNode.node("range").set(10);
                secondaryAbilityNode.node("potionDuration").set(200);
                secondaryAbilityNode.node("dolphinsGraceAmplifier").set(0);
            CommentedConfigurationNode waterAttributesNode = getConfigNode().node("waterAttributes");
                waterAttributesNode.node("blockBreakSpeed").set(5.0);
                waterAttributesNode.node("entityInteractRange").set(2.0);
                waterAttributesNode.node("blockInteractRange").set(2.0);
                waterAttributesNode.node("dolphinsGraceAmplifier").set(2);  
                waterAttributesNode.node("deathAvoidChance").set(0.2);
                waterAttributesNode.node("dodgeChance").set(0.2);
            CommentedConfigurationNode hydrationNode = getConfigNode().node("hydrationNode");
                hydrationNode.node("duration").set(100);
                hydrationNode.node("waterBreathingPotionChance").set(0.2);
                hydrationNode.node("respirationChance").set(0.15);
            CommentedConfigurationNode waterDamageNode = getConfigNode().node("waterDamage");
                waterDamageNode.node("tridentMeleeMultiplier").set(1.2);
                waterDamageNode.node("tridentRangeMultiplier").set(1.5);
                waterDamageNode.node("tridentVelocityMultiplier").set(1.5);
        } catch (SerializationException Exception) {

        }
    }
    // Primary Ability - Swim Multiplier
    public double getSwimMultiplier() {
        return getConfigNode().node("primaryability").node("swimMultiplier").getDouble();
    }

    // Secondary Ability - Range
    public int getSecondaryAbilityRange() {
        return getConfigNode().node("secondaryAbility").node("range").getInt();
    }

    // Secondary Ability - Potion Duration
    public int getSecondaryAbilityPotionDuration() {
        return getConfigNode().node("secondaryAbility").node("potionDuration").getInt();
    }

    // Secondary Ability - Dolphins Grace Amplifier
    public int getSecondaryAbilityDolphinsGraceAmplifier() {
        return getConfigNode().node("secondaryAbility").node("dolphinsGraceAmplifier").getInt();
    }

    // Water Attributes - Block Break Speed
    public double getBlockBreakSpeed() {
        return getConfigNode().node("waterAttributes").node("blockBreakSpeed").getDouble();
    }

    // Water Attributes - Entity Interact Range
    public double getEntityInteractRange() {
        return getConfigNode().node("waterAttributes").node("entityInteractRange").getDouble();
    }

    // Water Attributes - Entity Interact Range
    public double getBlockInteractRange() {
        return getConfigNode().node("waterAttributes").node("blockInteractRange").getDouble();
    }

    // Water Attributes - Dolphins Grace Amplifier
    public int getWaterAttributesDolphinsGraceAmplifier() {
        return getConfigNode().node("waterAttributes").node("dolphinsGraceAmplifier").getInt();
    }

    // Water Attributes - Death Avoid Chance
    public double getDeathAvoidChance() {
        return getConfigNode().node("waterAttributes").node("deathAvoidChance").getDouble();
    }

    // Water Attributes - Dodge Chance
    public double getWaterAttributesDodgeChance() {
        return getConfigNode().node("waterAttributes").node("dodgeChance").getDouble();
    }

    // Hydration Node - Duration
    public int getHydrationDuration() {
        return getConfigNode().node("hydrationNode").node("duration").getInt();
    }

    // Hydration Node - Water Breathing Potion Chance
    public double getWaterBreathingPotionChance() {
        return getConfigNode().node("hydrationNode").node("waterBreathingPotionChance").getDouble();
    }

    // Hydration Node - Respiration Node
    public double getRespirationChance() {
        return getConfigNode().node("hydrationNode").node("respirationChance").getDouble();
    }

    // Water Damage - Trident Melee Multiplier
    public double getTridentMeleeMultiplier() {
        return getConfigNode().node("waterDamage").node("tridentMeleeMultiplier").getDouble();
    }

    // Water Damage - Trident Range Multiplier
    public double getTridentRangeMultiplier() {
        return getConfigNode().node("waterDamage").node("tridentRangeMultiplier").getDouble();
    }

    // Water Damage - Trident Velocity Multiplier
    public double getTridentVelocityMultiplier() {
        return getConfigNode().node("waterDamage").node("tridentVelocityMultiplier").getDouble();
    }

}
