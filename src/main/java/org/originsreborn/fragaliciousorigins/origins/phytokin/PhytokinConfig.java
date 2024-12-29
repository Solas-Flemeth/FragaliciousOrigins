package org.originsreborn.fragaliciousorigins.origins.phytokin;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class PhytokinConfig extends OriginConfig {
    private int timePerTick, hungerPerTick;
    private float saturationPerTick,exhaustPerTick;
    private int witherDuration, witherAmplifier;
    private double thornDamage;
    public PhytokinConfig() {
        super(OriginType.PHYTOKIN, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try{
            CommentedConfigurationNode photosynthesisNode = getConfigNode().node("photosynthesis");
            photosynthesisNode.node("timePerTick").set(10);
            photosynthesisNode.node("saturationPerTick").set(1.0f);
            photosynthesisNode.node("hungerPerTick").set(1);
            photosynthesisNode.node("exhaustPerTick").set(-0.1f);
            CommentedConfigurationNode meleeNode = getConfigNode().node("melee");
            meleeNode.node("witherDuration").set(10);
            meleeNode.node("witherAmplifier").set(1);
            CommentedConfigurationNode thornsNode = getConfigNode().node("thorns");
            thornsNode.node("damage").set(2.0);

        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        timePerTick = getConfigNode().node("photosynthesis").node("timePerTick").getInt(10);
        saturationPerTick = getConfigNode().node("photosynthesis").node("saturationPerTick").getFloat(1.0f);
        hungerPerTick = getConfigNode().node("photosynthesis").node("hungerPerTick").getInt(1);
        exhaustPerTick = getConfigNode().node("photosynthesis").node("exhaustPerTick").getFloat(-0.1f);
        witherDuration = getConfigNode().node("melee").node("witherDuration").getInt(10);
        witherAmplifier = getConfigNode().node("melee").node("witherAmplifier").getInt(1);
        thornDamage = getConfigNode().node("thorns").node("damage").getDouble(2.0);
    }

    public int getTimePerTick() {
        return timePerTick;
    }

    public float getSaturationPerTick() {
        return saturationPerTick;
    }

    public int getHungerPerTick() {
        return hungerPerTick;
    }

    public float getExhaustPerTick() {
        return exhaustPerTick;
    }

    public int getWitherDuration() {
        return witherDuration;
    }

    public int getWitherAmplifier() {
        return witherAmplifier;
    }

    public double getThornDamage() {
        return thornDamage;
    }
}
