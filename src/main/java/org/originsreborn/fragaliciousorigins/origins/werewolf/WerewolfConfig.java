package org.originsreborn.fragaliciousorigins.origins.werewolf;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class WerewolfConfig extends OriginConfig {
    //prim ability
    private int radius, playerStrengthDuration, playerStrengthAmplifier, playerRegenerationDuration, playerRegenerationAmplifier, playerResistanceDuration, playerResistanceAmplifier, playerSpeedDuration, playerSpeedAmplifier;
    private int wolfStrengthAmplifier, wolfStrengthDuration, wolfRegenerationAmplifier, wolfRegenerationDuration, wolfResistanceAmplifier, wolfResistanceDuration, wolfSpeedAmplifier, wolfSpeedDuration;
    private float wolfDamageMultiplierPerArmour, wolfDamageMultiplierPerToughness, wolfBaseMelee;
    private float humanDamageMultiplierPerArmour, humanDamageMultiplierPerToughness,humanBaseMelee;
    private double speedPenaltyArmor, attackPenaltyArmor, damagePenaltyArmor, speedPenaltyToughness, attackPenaltyToughness, damagePenaltyToughness;
    public WerewolfConfig() {
        super(OriginType.WEREWOLF, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
                primaryAbilityNode.node("radius").set(20);
                CommentedConfigurationNode werewolfNode = primaryAbilityNode.node("werewolf");
                    werewolfNode.node("strengthAmplifier").set(1);
                    werewolfNode.node("strengthDuration").set(200);
                    werewolfNode.node("regenerationAmplifier").set(1);
                    werewolfNode.node("regenerationDuration").set(200);
                    werewolfNode.node("resistanceAmplifier").set(1);
                    werewolfNode.node("resistanceDuration").set(200);
                    werewolfNode.node("speedAmplifier").set(2);
                    werewolfNode.node("speedDuration").set(200);
                CommentedConfigurationNode wolfNode = primaryAbilityNode.node("wolf");
                    wolfNode.node("strengthAmplifier").set(2);
                    wolfNode.node("strengthDuration").set(500);
                    wolfNode.node("regenerationAmplifier").set(2);
                    wolfNode.node("regenerationDuration").set(500);
                    wolfNode.node("resistanceAmplifier").set(2);
                    wolfNode.node("resistanceDuration").set(500);
                    wolfNode.node("speedAmplifier").set(3);
                    wolfNode.node("speedDuration").set(500);
            CommentedConfigurationNode clawsNode = getConfigNode().node("claws");
                CommentedConfigurationNode wolfClawsNode = clawsNode.node("wolf");
                    wolfClawsNode.node("damageMultiplierPerArmor").set(0.075f);
                    wolfClawsNode.node("damageMultiplierPerToughness").set(0.075f);
                    wolfClawsNode.node("baseMelee").set(4f);
                CommentedConfigurationNode humanClawsNode = clawsNode.node("human");
                    humanClawsNode.node("damageMultiplierPerArmor").set(0.075f);
                    humanClawsNode.node("damageMultiplierPerToughness").set(0.075f);
                    humanClawsNode.node("baseMelee").set(2f);
            CommentedConfigurationNode wolfArmorPenaltyNode = getConfigNode().node("wolfArmorPenalty");
                wolfArmorPenaltyNode.node("speedPerArmor").set(0.04f);
                wolfArmorPenaltyNode.node("attackSpeedPerArmor").set(0.04f);
                wolfArmorPenaltyNode.node("damagePerArmor").set(0.04f);
                wolfArmorPenaltyNode.node("speedPerToughness").set(0.04f);
                wolfArmorPenaltyNode.node("attackSpeedPerToughness").set(0.04f);
                wolfArmorPenaltyNode.node("damagePerToughness").set(0.04f);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     */
    @Override
    public void defineVariables() {
        radius = getConfigNode().node("primaryAbility").node("radius").getInt();
        playerStrengthAmplifier = getConfigNode().node("primaryAbility").node("werewolf").node("strengthAmplifier").getInt();
        playerStrengthDuration = getConfigNode().node("primaryAbility").node("werewolf").node("strengthDuration").getInt();
        playerRegenerationAmplifier = getConfigNode().node("primaryAbility").node("werewolf").node("regenerationAmplifier").getInt();
        playerRegenerationDuration = getConfigNode().node("primaryAbility").node("werewolf").node("regenerationDuration").getInt();
        playerResistanceAmplifier = getConfigNode().node("primaryAbility").node("werewolf").node("resistanceAmplifier").getInt();
        playerResistanceDuration = getConfigNode().node("primaryAbility").node("werewolf").node("resistanceDuration").getInt();
        playerSpeedAmplifier = getConfigNode().node("primaryAbility").node("werewolf").node("speedAmplifier").getInt();
        playerSpeedDuration = getConfigNode().node("primaryAbility").node("werewolf").node("speedDuration").getInt();
        wolfStrengthAmplifier = getConfigNode().node("primaryAbility").node("wolf").node("strengthAmplifier").getInt();
        wolfStrengthDuration = getConfigNode().node("primaryAbility").node("wolf").node("strengthDuration").getInt();
        wolfRegenerationAmplifier = getConfigNode().node("primaryAbility").node("wolf").node("regenerationAmplifier").getInt();
        wolfRegenerationDuration = getConfigNode().node("primaryAbility").node("wolf").node("regenerationDuration").getInt();
        wolfResistanceAmplifier = getConfigNode().node("primaryAbility").node("wolf").node("resistanceAmplifier").getInt();
        wolfResistanceDuration = getConfigNode().node("primaryAbility").node("wolf").node("resistanceDuration").getInt();
        wolfSpeedAmplifier = getConfigNode().node("primaryAbility").node("wolf").node("speedAmplifier").getInt();
        //wolf melee
        wolfDamageMultiplierPerArmour = getConfigNode().node("claws").node("wolf").node("damageMultiplierPerArmor").getFloat();
        wolfDamageMultiplierPerToughness = getConfigNode().node("claws").node("wolf").node("damageMultiplierPerToughness").getFloat();
        wolfBaseMelee = getConfigNode().node("claws").node("wolf").node("baseMelee").getFloat();
        //human melee
        humanDamageMultiplierPerArmour = getConfigNode().node("claws").node("human").node("damageMultiplierPerArmor").getFloat();
        humanDamageMultiplierPerToughness = getConfigNode().node("claws").node("human").node("damageMultiplierPerToughness").getFloat();
        humanBaseMelee = getConfigNode().node("claws").node("human").node("baseMelee").getFloat();

        speedPenaltyArmor = getConfigNode().node("wolfArmorPenalty").node("speedPerArmor").getFloat();
        attackPenaltyArmor = getConfigNode().node("wolfArmorPenalty").node("attackSpeedPerArmor").getFloat();
        damagePenaltyArmor = getConfigNode().node("wolfArmorPenalty").node("damagePerArmor").getFloat();
        speedPenaltyToughness = getConfigNode().node("wolfArmorPenalty").node("speedPerToughness").getFloat();
        attackPenaltyToughness = getConfigNode().node("wolfArmorPenalty").node("attackSpeedPerToughness").getFloat();
        damagePenaltyToughness = getConfigNode().node("wolfArmorPenalty").node("damagePerToughness").getFloat();
    }

    public int getRadius() {
        return radius;
    }

    public int getPlayerStrengthDuration() {
        return playerStrengthDuration;
    }

    public int getPlayerStrengthAmplifier() {
        return playerStrengthAmplifier;
    }

    public int getPlayerRegenerationDuration() {
        return playerRegenerationDuration;
    }

    public int getPlayerRegenerationAmplifier() {
        return playerRegenerationAmplifier;
    }

    public int getPlayerResistanceDuration() {
        return playerResistanceDuration;
    }

    public int getPlayerResistanceAmplifier() {
        return playerResistanceAmplifier;
    }

    public int getPlayerSpeedDuration() {
        return playerSpeedDuration;
    }

    public int getPlayerSpeedAmplifier() {
        return playerSpeedAmplifier;
    }

    public int getWolfStrengthAmplifier() {
        return wolfStrengthAmplifier;
    }

    public int getWolfStrengthDuration() {
        return wolfStrengthDuration;
    }

    public int getWolfRegenerationAmplifier() {
        return wolfRegenerationAmplifier;
    }

    public int getWolfRegenerationDuration() {
        return wolfRegenerationDuration;
    }

    public int getWolfResistanceAmplifier() {
        return wolfResistanceAmplifier;
    }

    public int getWolfResistanceDuration() {
        return wolfResistanceDuration;
    }

    public int getWolfSpeedAmplifier() {
        return wolfSpeedAmplifier;
    }

    public int getWolfSpeedDuration() {
        return wolfSpeedDuration;
    }

    public float getWolfDamageMultiplierPerArmour() {
        return wolfDamageMultiplierPerArmour;
    }

    public float getWolfDamageMultiplierPerToughness() {
        return wolfDamageMultiplierPerToughness;
    }

    public float getWolfBaseMelee() {
        return wolfBaseMelee;
    }

    public float getHumanDamageMultiplierPerArmour() {
        return humanDamageMultiplierPerArmour;
    }

    public float getHumanDamageMultiplierPerToughness() {
        return humanDamageMultiplierPerToughness;
    }

    public float getHumanBaseMelee() {
        return humanBaseMelee;
    }

    public double getSpeedPenaltyArmor() {
        return speedPenaltyArmor;
    }

    public double getAttackPenaltyArmor() {
        return attackPenaltyArmor;
    }

    public double getDamagePenaltyArmor() {
        return damagePenaltyArmor;
    }

    public double getSpeedPenaltyToughness() {
        return speedPenaltyToughness;
    }

    public double getAttackPenaltyToughness() {
        return attackPenaltyToughness;
    }

    public double getDamagePenaltyToughness() {
        return damagePenaltyToughness;
    }
}
