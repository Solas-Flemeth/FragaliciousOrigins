package org.originsreborn.fragaliciousorigins.origins.elytrian;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ElytrianConfig extends OriginConfig {
    private double groundVelocityMultiplier, flyingMinVelocity, flyingVelocityMultiplier, slownessPerArmor, dodgeChance, attackDamage, attackSpeed, blockInteractRange, entityInteractRange, blockBreakSpeed;
    private int armor, toughness, modelData, minArmorToApply, ceilingHeight, slownessAmplifier;

    public ElytrianConfig() {
        super(OriginType.ELYTRIAN, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("groundVelocityMultiplier").set(3.0);
            primaryAbilityNode.node("flyingMinVelocity").set(5.0);
            primaryAbilityNode.node("flyingVelocityMultiplier").set(1.5);

            CommentedConfigurationNode elytraNode = getConfigNode().node("elytra");
            elytraNode.node("armor").set(4);
            elytraNode.node("toughness").set(8);
            elytraNode.node("modelData").set(0);

            CommentedConfigurationNode armorPenaltyNode = getConfigNode().node("armorPenalty");
            armorPenaltyNode.node("slownessPerArmor").set(0.02);
            armorPenaltyNode.node("minArmorToApply").set(4);

            CommentedConfigurationNode ceilingNode = getConfigNode().node("ceilingPenalty");
            ceilingNode.node("height").set(7);
            ceilingNode.node("slownessAmplifier").set(1);

            CommentedConfigurationNode glidingNode = getConfigNode().node("gliding");
            glidingNode.node("dodgeChance").set(0.2);
            glidingNode.node("attackDamage").set(2.0);
            glidingNode.node("attackSpeed").set(1.5);
            glidingNode.node("blockInteractRange").set(2.5);
            glidingNode.node("entityInteractRange").set(2.5);
            glidingNode.node("blockBreakSpeed").set(5.0);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void defineVariables() {
        groundVelocityMultiplier = getConfigNode().node("primaryAbility").node("groundVelocityMultiplier").getDouble();
        flyingMinVelocity = getConfigNode().node("primaryAbility").node("flyingMinVelocity").getDouble();
        flyingVelocityMultiplier = getConfigNode().node("primaryAbility").node("flyingVelocityMultiplier").getDouble();

        armor = getConfigNode().node("elytra").node("armor").getInt();
        toughness = getConfigNode().node("elytra").node("toughness").getInt();
        modelData = getConfigNode().node("elytra").node("modelData").getInt();

        slownessPerArmor = getConfigNode().node("armorPenalty").node("slownessPerArmor").getDouble();
        minArmorToApply = getConfigNode().node("armorPenalty").node("minArmorToApply").getInt();

        ceilingHeight = getConfigNode().node("ceilingPenalty").node("height").getInt();
        slownessAmplifier = getConfigNode().node("ceilingPenalty").node("slownessAmplifier").getInt();

        dodgeChance = getConfigNode().node("gliding").node("dodgeChance").getDouble();
        attackDamage = getConfigNode().node("gliding").node("attackDamage").getDouble();
        attackSpeed = getConfigNode().node("gliding").node("attackSpeed").getDouble();
        blockInteractRange = getConfigNode().node("gliding").node("blockInteractRange").getDouble();
        entityInteractRange = getConfigNode().node("gliding").node("entityInteractRange").getDouble();
        blockBreakSpeed = getConfigNode().node("gliding").node("blockBreakSpeed").getDouble();

        // Debug output for all variables
        System.out.println("ELYTRIAN CONFIG STARTS");
        System.out.println("groundVelocityMultiplier: " + groundVelocityMultiplier);
        System.out.println("flyingMinVelocity: " + flyingMinVelocity);
        System.out.println("flyingVelocityMultiplier: " + flyingVelocityMultiplier);
        System.out.println("armor: " + armor);
        System.out.println("toughness: " + toughness);
        System.out.println("modelData: " + modelData);
        System.out.println("slownessPerArmor: " + slownessPerArmor);
        System.out.println("minArmorToApply: " + minArmorToApply);
        System.out.println("ceilingHeight: " + ceilingHeight);
        System.out.println("slownessAmplifier: " + slownessAmplifier);
        System.out.println("dodgeChance: " + dodgeChance);
        System.out.println("attackDamage: " + attackDamage);
        System.out.println("attackSpeed: " + attackSpeed);
        System.out.println("blockInteractRange: " + blockInteractRange);
        System.out.println("entityInteractRange: " + entityInteractRange);
        System.out.println("blockBreakSpeed: " + blockBreakSpeed);
        System.out.println("ELYTRIAN CONFIG ENDS");
    }

    public double getPrimaryAbilityGroundVelocityMultiplier() {
        return groundVelocityMultiplier;
    }

    public double getPrimaryAbilityFlyingMinVelocity() {
        return flyingMinVelocity;
    }

    public double getPrimaryAbilityFlyingVelocityMultiplier() {
        return flyingVelocityMultiplier;
    }

    public int getElytraArmor() {
        return armor;
    }

    public int getElytraToughness() {
        return toughness;
    }

    public int getElytraModelData() {
        return modelData;
    }

    public double getArmorPenaltySlownessPerArmor() {
        return slownessPerArmor;
    }

    public int getArmorPenaltyMinArmorToApply() {
        return minArmorToApply;
    }

    public int getCeilingPenaltyHeight() {
        return ceilingHeight;
    }

    public int getCeilingPenaltySlownessAmplifier() {
        return slownessAmplifier;
    }

    public double getGlidingDodgeChance() {
        return dodgeChance;
    }

    public double getGlidingAttackDamage() {
        return attackDamage;
    }

    public double getGlidingAttackSpeed() {
        return attackSpeed;
    }

    public double getGlidingBlockInteractRange() {
        return blockInteractRange;
    }

    public double getGlidingEntityInteractRange() {
        return entityInteractRange;
    }

    public double getGlidingBlockBreakSpeed() {
        return blockBreakSpeed;
    }
}
