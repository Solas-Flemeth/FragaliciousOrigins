package org.originsreborn.fragaliciousorigins.origins.merling;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class MerlingConfig extends OriginConfig {
    private double swimMultiplier, blockBreakSpeed, entityInteractRange, blockInteractRange, deathAvoidChance, dodgeChance, waterBreathingPotionChance, respirationChance, tridentMeleeMultiplier, tridentRangeMultiplier, tridentVelocityMultiplier;
    private int secondaryAbilityRange, secondaryAbilityPotionDuration, secondaryAbilityDolphinsGraceAmplifier, waterAttributesDolphinsGraceAmplifier, hydrationDuration, minArmorToApply, ceilingHeight, slownessAmplifier;

    public MerlingConfig() {
        super(OriginType.MERLING, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
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
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void defineVariables() {
        swimMultiplier = getConfigNode().node("primaryAbility").node("swimMultiplier").getDouble();

        secondaryAbilityRange = getConfigNode().node("secondaryAbility").node("range").getInt();
        secondaryAbilityPotionDuration = getConfigNode().node("secondaryAbility").node("potionDuration").getInt();
        secondaryAbilityDolphinsGraceAmplifier = getConfigNode().node("secondaryAbility").node("dolphinsGraceAmplifier").getInt();

        blockBreakSpeed = getConfigNode().node("waterAttributes").node("blockBreakSpeed").getDouble();
        entityInteractRange = getConfigNode().node("waterAttributes").node("entityInteractRange").getDouble();
        blockInteractRange = getConfigNode().node("waterAttributes").node("blockInteractRange").getDouble();
        waterAttributesDolphinsGraceAmplifier = getConfigNode().node("waterAttributes").node("dolphinsGraceAmplifier").getInt();
        deathAvoidChance = getConfigNode().node("waterAttributes").node("deathAvoidChance").getDouble();
        dodgeChance = getConfigNode().node("waterAttributes").node("dodgeChance").getDouble();

        hydrationDuration = getConfigNode().node("hydrationNode").node("duration").getInt();
        waterBreathingPotionChance = getConfigNode().node("hydrationNode").node("waterBreathingPotionChance").getDouble();
        respirationChance = getConfigNode().node("hydrationNode").node("respirationChance").getDouble();

        tridentMeleeMultiplier = getConfigNode().node("waterDamage").node("tridentMeleeMultiplier").getDouble();
        tridentRangeMultiplier = getConfigNode().node("waterDamage").node("tridentRangeMultiplier").getDouble();
        tridentVelocityMultiplier = getConfigNode().node("waterDamage").node("tridentVelocityMultiplier").getDouble();
    }

    public double getSwimMultiplier() {
        return swimMultiplier;
    }

    public int getSecondaryAbilityRange() {
        return secondaryAbilityRange;
    }

    public int getSecondaryAbilityPotionDuration() {
        return secondaryAbilityPotionDuration;
    }

    public int getSecondaryAbilityDolphinsGraceAmplifier() {
        return secondaryAbilityDolphinsGraceAmplifier;
    }

    public double getBlockBreakSpeed() {
        return blockBreakSpeed;
    }

    public double getEntityInteractRange() {
        return entityInteractRange;
    }

    public double getBlockInteractRange() {
        return blockInteractRange;
    }

    public int getWaterAttributesDolphinsGraceAmplifier() {
        return waterAttributesDolphinsGraceAmplifier;
    }

    public double getDeathAvoidChance() {
        return deathAvoidChance;
    }

    public double getWaterAttributesDodgeChance() {
        return dodgeChance;
    }

    public int getHydrationDuration() {
        return hydrationDuration;
    }

    public double getWaterBreathingPotionChance() {
        return waterBreathingPotionChance;
    }

    public double getRespirationChance() {
        return respirationChance;
    }

    public double getTridentMeleeMultiplier() {
        return tridentMeleeMultiplier;
    }

    public double getTridentRangeMultiplier() {
        return tridentRangeMultiplier;
    }

    public double getTridentVelocityMultiplier() {
        return tridentVelocityMultiplier;
    }
}
