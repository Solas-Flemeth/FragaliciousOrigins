package org.originsreborn.fragaliciousorigins.origins.elytrian;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ElytrianConfig extends OriginConfig {
    public ElytrianConfig() {
        super(OriginType.ELYTRIAN, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
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
                glidingNode.node("attackDamage").set(2.0); //NUMERIC
                glidingNode.node("attackSpeed").set(1.5);
                glidingNode.node("blockInteractRange").set(2.5); //NUMERIC
                glidingNode.node("entityInteractRange").set(2.5); //NUMERIC
                glidingNode.node("blockBreakSpeed").set(5.0); //multiplier
        } catch (SerializationException Exception) {

        }
    }
    public double getPrimaryAbilityGroundVelocityMultiplier() {
        return getConfigNode().node("primaryability").node("groundVelocityMultiplier").getDouble();
    }

    public double getPrimaryAbilityFlyingMinVelocity() {
        return getConfigNode().node("primaryability").node("flyingMinVelocity").getDouble();
    }

    public double getPrimaryAbilityFlyingVelocityMultiplier() {
        return getConfigNode().node("primaryability").node("flyingVelocityMultiplier").getDouble();
    }

    public int getElytraArmor() {
        return getConfigNode().node("elytra").node("armor").getInt();
    }

    public int getElytraToughness() {
        return getConfigNode().node("elytra").node("toughness").getInt();
    }

    public int getElytraModelData() {
        return getConfigNode().node("elytra").node("modelData").getInt();
    }

    public double getArmorPenaltySlownessPerArmor() {
        return getConfigNode().node("armorPenalty").node("slownessPerArmor").getDouble();
    }

    public double getArmorPenaltyMinArmorToApply() {
        return getConfigNode().node("armorPenalty").node("minArmorToApply").getInt();
    }

    public int getCeilingPenaltyHeight() {
        return getConfigNode().node("ceilingPenalty").node("height").getInt();
    }

    public int getCeilingPenaltySlownessAmplifier() {
        return getConfigNode().node("ceilingPenalty").node("slownessAmplifier").getInt();
    }

    public double getGlidingDodgeChance() {
        return getConfigNode().node("gliding").node("dodgeChance").getDouble();
    }

    public double getGlidingAttackDamage() {
        return getConfigNode().node("gliding").node("attackDamage").getDouble();
    }

    public double getGlidingAttackSpeed() {
        return getConfigNode().node("gliding").node("attackSpeed").getDouble();
    }

    public double getGlidingBlockInteractRange() {
        return getConfigNode().node("gliding").node("blockInteractRange").getDouble();
    }

    public double getGlidingEntityInteractRange() {
        return getConfigNode().node("gliding").node("entityInteractRange").getDouble();
    }

    public double getGlidingBlockBreakSpeed() {
        return getConfigNode().node("gliding").node("blockBreakSpeed").getDouble();
    }

}
