package org.originsreborn.fragaliciousorigins.origins.fairy;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class FairyConfig extends OriginConfig {
    private float primaryRange, primaryAbilityReduction;
    private int primaryRegenDuration, primaryAmplifier;
    private int speedDuration, speedAmplifier;
    private int hasteDuration, hasteAmplifier;
    private float flightExhaustion;
    public FairyConfig() {
        super(OriginType.FAIRY, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try{
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("range").set(10.0f);
            primaryAbilityNode.node("regenDuration").set(100);
            primaryAbilityNode.node("regenAmplifier").set(1);
            primaryAbilityNode.node("abilityReduction").set(0.5f);
            CommentedConfigurationNode foodConsumeNode = getConfigNode().node("food");
            CommentedConfigurationNode speedNode  = foodConsumeNode.node("speed");
            speedNode.node("duration").set(100);
            speedNode.node("amplifier").set(2);
            CommentedConfigurationNode hasteNode  = foodConsumeNode.node("haste");
            hasteNode.node("duration").set(100);
            hasteNode.node("amplifier").set(2);
            getConfigNode().node("flightExhaustion").set(0.2f);
        }catch (Exception ignored){

        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        primaryRange = getConfigNode().node("primaryAbility").node("range").getFloat(10.0f);
        primaryRegenDuration = getConfigNode().node("primaryAbility").node("regenDuration").getInt(100);
        primaryAmplifier = getConfigNode().node("primaryAbility").node("regenAmplifier").getInt(1);
        primaryAbilityReduction = getConfigNode().node("primaryAbility").node("abilityReduction").getFloat(0.5f);
        speedAmplifier = getConfigNode().node("foodConsumeNode").node("speed").node("duration").getInt(100);
        speedDuration = getConfigNode().node("foodConsumeNode").node("speed").node("amplifier").getInt(2);
        hasteDuration = getConfigNode().node("foodConsumeNode").node("haste").node("duration").getInt(100);
        hasteAmplifier = getConfigNode().node("foodConsumeNode").node("haste").node("amplifier").getInt(2);
        flightExhaustion = getConfigNode().node().getFloat(0.2f);
    }

    public float getPrimaryRange() {
        return primaryRange;
    }

    public float getPrimaryAbilityReduction() {
        return primaryAbilityReduction;
    }

    public int getPrimaryRegenDuration() {
        return primaryRegenDuration;
    }

    public int getPrimaryAmplifier() {
        return primaryAmplifier;
    }

    public int getSpeedDuration() {
        return speedDuration;
    }

    public int getSpeedAmplifier() {
        return speedAmplifier;
    }

    public int getHasteDuration() {
        return hasteDuration;
    }

    public int getHasteAmplifier() {
        return hasteAmplifier;
    }

    public float getFlightExhaustion() {
        return flightExhaustion;
    }
}
