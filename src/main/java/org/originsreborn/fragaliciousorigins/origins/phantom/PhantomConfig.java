package org.originsreborn.fragaliciousorigins.origins.phantom;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class PhantomConfig extends OriginConfig {
    private int ticksPerHunger, minimumHunger, hauntSlownessDuration, hauntDuration, restDuration, regenLightLevel, nightVisionLevel, disableNightVisionLevel;
    private int coyoteCharge, coyoteDrain;
    private double velocity, hauntAttackDamageMultiplier, hauntDamageTakenMultiplier, hauntSlownessRange, restAttackDamageMultiplier, restDamageTakenMultiplier, chanceToNotDrainMax, chanceToNotDrainInterval;
    private double speedLightMultiplier, miningMultiplier, attackSpeed, dodgeChance, fireResistanceDamageReduction, miningSpeed, secondaryCooldownMultiplier;

    public PhantomConfig() {
        super(OriginType.PHANTOM, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("ticksPerHunger").set(3); //int
            primaryAbilityNode.node("minimumHunger").set(3); //int
            primaryAbilityNode.node("velocity").set(0.2);
            primaryAbilityNode.node("chanceToNotDrainInterval").set(0.03);
            primaryAbilityNode.node("chanceToNotDrainMax").set(0.80);
            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
            CommentedConfigurationNode hauntNode = secondaryAbilityNode.node("haunt");
            hauntNode.node("attackDamageMultiplier").set(1.25);
            hauntNode.node("damageTakenMultiplier").set(0.8);
            hauntNode.node("slownessDuration").set(30);
            hauntNode.node("slownessRange").set(2.5);
            hauntNode.node("duration").set(10);

            CommentedConfigurationNode restNode = secondaryAbilityNode.node("rest");
            restNode.node("attackDamageMultiplier").set(0.25);
            restNode.node("damageTakenMultiplier").set(1.5);
            restNode.node("duration").set(20);

            CommentedConfigurationNode shadowSkinNode = getConfigNode().node("shadowskin");
            shadowSkinNode.node("regenLightLevel").set(5);
            shadowSkinNode.node("speedLightMultiplier").set(0.11);
            shadowSkinNode.node("miningSpeed").set(0.05);
            shadowSkinNode.node("attackSpeed").set(0.03);
            shadowSkinNode.node("nightVisionLevel").set(4);
            shadowSkinNode.node("disableNightVisionLevel").set(12);
            shadowSkinNode.node("dodgeChance").set(0.02); // Assuming a default value
            shadowSkinNode.node("fireResistanceDamageReduction").set(0.5);

            CommentedConfigurationNode otherworldlyNode = getConfigNode().node("otherworldly");
            otherworldlyNode.node("miningSpeed").set(1.2);
            otherworldlyNode.node("ticksPerHunger").set(5);
            otherworldlyNode.node("secondaryCooldownMultiplier").set(0.8);

            CommentedConfigurationNode coyoteNode = getConfigNode().node("coyote");
            coyoteNode.node("charge").set(120);
            coyoteNode.node("drain").set(10);
        } catch (Exception e) {
            // Handle exception
        }
    }

    @Override
    public void defineVariables() {
        ticksPerHunger = getConfigNode().node("primaryAbility").node("ticksPerHunger").getInt();
        minimumHunger = getConfigNode().node("primaryAbility").node("minimumHunger").getInt();
        velocity = getConfigNode().node("primaryAbility").node("velocity").getDouble();
        chanceToNotDrainMax =  getConfigNode().node("primaryAbility").node("chanceToNotDrainMax").getDouble();
        chanceToNotDrainInterval = getConfigNode().node("primaryAbility").node("chanceToNotDrainInterval").getDouble();
        hauntAttackDamageMultiplier = getConfigNode().node("secondaryAbility").node("haunt").node("attackDamageMultiplier").getDouble();
        hauntDamageTakenMultiplier = getConfigNode().node("secondaryAbility").node("haunt").node("damageTakenMultiplier").getDouble();
        hauntSlownessDuration = getConfigNode().node("secondaryAbility").node("haunt").node("slownessDuration").getInt();
        hauntSlownessRange = getConfigNode().node("secondaryAbility").node("haunt").node("slownessRange").getDouble();
        hauntDuration = getConfigNode().node("secondaryAbility").node("haunt").node("duration").getInt();

        restAttackDamageMultiplier = getConfigNode().node("secondaryAbility").node("rest").node("attackDamageMultiplier").getDouble();
        restDamageTakenMultiplier = getConfigNode().node("secondaryAbility").node("rest").node("damageTakenMultiplier").getDouble();
        restDuration = getConfigNode().node("secondaryAbility").node("rest").node("duration").getInt();

        regenLightLevel = getConfigNode().node("shadowskin").node("regenLightLevel").getInt();
        speedLightMultiplier = getConfigNode().node("shadowskin").node("speedLightMultiplier").getDouble();
        miningMultiplier = getConfigNode().node("shadowskin").node("miningSpeed").getDouble();
        attackSpeed = getConfigNode().node("shadowskin").node("attackSpeed").getDouble();
        nightVisionLevel = getConfigNode().node("shadowskin").node("nightVisionLevel").getInt();
        disableNightVisionLevel = getConfigNode().node("shadowskin").node("disableNightVisionLevel").getInt();
        dodgeChance = getConfigNode().node("shadowskin").node("dodgeChance").getDouble();
        fireResistanceDamageReduction = getConfigNode().node("shadowskin").node("fireResistanceDamageReduction").getDouble();

        miningSpeed = getConfigNode().node("otherworldly").node("miningSpeed").getDouble();
        ticksPerHunger = getConfigNode().node("otherworldly").node("ticksPerHunger").getInt();
        secondaryCooldownMultiplier = getConfigNode().node("otherworldly").node("secondaryCooldownMultiplier").getDouble();

        coyoteCharge = getConfigNode().node("coyote").node("charge").getInt();
        coyoteDrain = getConfigNode().node("coyote").node("drain").getInt();

    }

    // Getter methods
    public int getTicksPerHunger() {
        return ticksPerHunger;
    }

    public int getMinimumHunger() {
        return minimumHunger;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getChanceToNotDrainMax() {
        return chanceToNotDrainMax;
    }

    public double getChanceToNotDrainInterval() {
        return chanceToNotDrainInterval;
    }

    public double getHauntAttackDamageMultiplier() {
        return hauntAttackDamageMultiplier;
    }

    public double getHauntDamageTakenMultiplier() {
        return hauntDamageTakenMultiplier;
    }

    public int getHauntSlownessDuration() {
        return hauntSlownessDuration;
    }

    public double getHauntSlownessRange() {
        return hauntSlownessRange;
    }

    public int getHauntDuration() {
        return hauntDuration;
    }

    public double getRestAttackDamageMultiplier() {
        return restAttackDamageMultiplier;
    }

    public double getRestDamageTakenMultiplier() {
        return restDamageTakenMultiplier;
    }

    public int getRestDuration() {
        return restDuration;
    }

    public int getRegenLightLevel() {
        return regenLightLevel;
    }

    public double getSpeedLightMultiplier() {
        return speedLightMultiplier;
    }

    public double getMiningMultiplier() {
        return miningMultiplier;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getNightVisionLevel() {
        return nightVisionLevel;
    }

    public int getDisableNightVisionLevel() {
        return disableNightVisionLevel;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public double getFireResistanceDamageReduction() {
        return fireResistanceDamageReduction;
    }

    public double getMiningSpeed() {
        return miningSpeed;
    }

    public double getSecondaryCooldownMultiplier() {
        return secondaryCooldownMultiplier;
    }

    public int getCoyoteMaxCharge() {
        return coyoteCharge;
    }

    public int getCoyoteDrain() {
        return coyoteDrain;
    }
}
