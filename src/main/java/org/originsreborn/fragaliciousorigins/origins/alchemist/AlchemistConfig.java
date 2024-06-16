package org.originsreborn.fragaliciousorigins.origins.alchemist;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class AlchemistConfig extends OriginConfig {
    private int expierenceGained, spiderEyeAmplifierChange, glowstoneAmplifierChange, redstoneAmplifierChange, arrowDuration;
    private double spiderEyeDurationMultiplier, glowstoneDurationMultiplier, redstoneDurationMultiplier;
    private double midasTouchChance, repairFreeChance, enchantmentFreeChance, doubleXPChance;
    private double arrowVelocity, explosionDamageModifier, explosionVelocity;
    private float splashRadiusModifier, lingeringRadiusModifier, lingeringDurationModifier, potionVelocityMultiplier, arrowRadius;

    public AlchemistConfig() {
        super(OriginType.ALCHEMIST, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("expierenceGained").set(30);

            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
            CommentedConfigurationNode spiderEyeNode = secondaryAbilityNode.node("spiderEye");
            spiderEyeNode.node("durationMultiplier").set(0.75);
            spiderEyeNode.node("amplifierChange").set(0); //int
            CommentedConfigurationNode glowstoneNode = secondaryAbilityNode.node("glowstone");
            glowstoneNode.node("durationMultiplier").set(0.5);
            glowstoneNode.node("amplifierChange").set(1); //int
            CommentedConfigurationNode redstoneNode = secondaryAbilityNode.node("redstone");
            redstoneNode.node("durationMultiplier").set(2.0);
            redstoneNode.node("amplifierChange").set(0); //int

            getConfigNode().node("midasTouchChance").set(0.75);

            CommentedConfigurationNode experienceNode = getConfigNode().node("expierence");
            experienceNode.node("repairFreeChance").set(0.5);
            experienceNode.node("enchantmentFreeChance").set(0.5);
            experienceNode.node("doubleXPChance").set(0.2);

            CommentedConfigurationNode combatNode = getConfigNode().node("combat");
            combatNode.node("arrowVelocity").set(0.75);
            combatNode.node("arrowRadius").set(1.5);
            combatNode.node("arrowDuration").set(200);
            combatNode.node("explosionDamageModifier").set(1.5);
            combatNode.node("explosionVelocityModifier").set(2.0);

            CommentedConfigurationNode potions = getConfigNode().node("potions");
            potions.node("splashRadiusModifier").set(1.5);
            potions.node("lingeringRadiusModifier").set(1.5);
            potions.node("lingeringDurationModifier").set(2.0);
            potions.node("velocityMultiplier").set(2.0);
        } catch (Exception e) {
            // Handle exception
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        expierenceGained = getConfigNode().node("primaryAbility").node("expierenceGained").getInt();
        spiderEyeDurationMultiplier = getConfigNode().node("secondaryAbility").node("spiderEye").node("durationMultiplier").getDouble();
        spiderEyeAmplifierChange = getConfigNode().node("secondaryAbility").node("spiderEye").node("amplifierChange").getInt();
        glowstoneDurationMultiplier = getConfigNode().node("secondaryAbility").node("glowstone").node("durationMultiplier").getDouble();
        glowstoneAmplifierChange = getConfigNode().node("secondaryAbility").node("glowstone").node("amplifierChange").getInt();
        redstoneDurationMultiplier = getConfigNode().node("secondaryAbility").node("redstone").node("durationMultiplier").getDouble();
        redstoneAmplifierChange = getConfigNode().node("secondaryAbility").node("redstone").node("amplifierChange").getInt();
        midasTouchChance = getConfigNode().node("midasTouchChance").getDouble();
        repairFreeChance = getConfigNode().node("expierence").node("repairFreeChance").getDouble();
        enchantmentFreeChance = getConfigNode().node("expierence").node("enchantmentFreeChance").getDouble();
        doubleXPChance = getConfigNode().node("expierence").node("doubleXPChance").getDouble();
        arrowVelocity = getConfigNode().node("combat").node("arrowVelocity").getDouble();
        arrowRadius = (float) getConfigNode().node("combat").node("arrowRadius").getDouble();
        arrowDuration = getConfigNode().node("combat").node("arrowDuration").getInt();
        explosionDamageModifier = getConfigNode().node("combat").node("explosionDamageModifier").getDouble();
        explosionVelocity = getConfigNode().node("combat").node("explosionVelocityModifier").getDouble();
        splashRadiusModifier = (float) getConfigNode().node("potions").node("splashRadiusModifier").getDouble();
        lingeringRadiusModifier = (float) getConfigNode().node("potions").node("lingeringRadiusModifier").getDouble();
        lingeringDurationModifier = (float) getConfigNode().node("potions").node("lingeringDurationModifier").getDouble();
        potionVelocityMultiplier = (float) getConfigNode().node("potions").node("velocityMultiplier").getDouble();
    }

    public int getExpierenceGained() {
        return expierenceGained;
    }

    public double getSpiderEyeDurationMultiplier() {
        return spiderEyeDurationMultiplier;
    }

    public int getSpiderEyeAmplifierChange() {
        return spiderEyeAmplifierChange;
    }

    public double getGlowstoneDurationMultiplier() {
        return glowstoneDurationMultiplier;
    }

    public int getGlowstoneAmplifierChange() {
        return glowstoneAmplifierChange;
    }

    public double getRedstoneDurationMultiplier() {
        return redstoneDurationMultiplier;
    }

    public int getRedstoneAmplifierChange() {
        return redstoneAmplifierChange;
    }

    public double getMidasTouchChance() {
        return midasTouchChance;
    }

    public double getRepairFreeChance() {
        return repairFreeChance;
    }

    public double getEnchantmentFreeChance() {
        return enchantmentFreeChance;
    }

    public double getDoubleXPChance() {
        return doubleXPChance;
    }

    public double getArrowVelocity() {
        return arrowVelocity;
    }

    public float getArrowRadius() {
        return arrowRadius;
    }

    public int getArrowDuration() {
        return arrowDuration;
    }

    public double getExplosionDamageModifier() {
        return explosionDamageModifier;
    }

    public double getExplosionVelocity() {
        return explosionVelocity;
    }

    public float getSplashRadiusModifier() {
        return splashRadiusModifier;
    }

    public float getLingeringRadiusModifier() {
        return lingeringRadiusModifier;
    }

    public double getLingeringDurationModifier() {
        return lingeringDurationModifier;
    }

    public float getPotionVelocityMultiplier() {
        return potionVelocityMultiplier;
    }
}
