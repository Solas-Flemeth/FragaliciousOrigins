package org.originsreborn.fragaliciousorigins.origins.arachnid;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;


public class ArachnidConfig extends OriginConfig{
    private int primaryAbilityAmount, primaryAbilitySpawnRadius;
    private float webMeleeChance, webRangeChance;
    private int webMeleeDuration, webMeleePoisonDuration, webMeleePoisonAmplifier;
    private int webRangeDuration, webRangePoisonDuration, webRangePoisonAmplifier;
    public ArachnidConfig() {
        super(OriginType.ARACHNID, "unique.yaml");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try {
        CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
            primaryAbilityNode.node("amount").set(3);
            primaryAbilityNode.node("spawnRadius").set(10);
        CommentedConfigurationNode webbingNode = getConfigNode().node("webbing");
            CommentedConfigurationNode meleeNode = webbingNode.node("melee");
                meleeNode.node("chance").set(0.2f);
                meleeNode.node("webDurationSeconds").set(3);
                meleeNode.node("poisonDurationTicks").set(50);
                meleeNode.node("poisonAmplifier").set(2);
            CommentedConfigurationNode rangeNode = webbingNode.node("range");
                rangeNode.node("chance").set(0.2f);
                rangeNode.node("webDurationSeconds").set(3);
                rangeNode.node("poisonDurationTicks").set(50);
                rangeNode.node("poisonAmplifier").set(2);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        primaryAbilityAmount = getConfigNode().node("primaryAbility").node("amount").getInt();
        primaryAbilitySpawnRadius = getConfigNode().node("primaryAbility").node("spawnRadius").getInt();
        webMeleeChance = getConfigNode().node("webbing").node("melee").node("chance").getFloat();
        webMeleeDuration = getConfigNode().node("webbing").node("melee").node("webDurationSeconds").getInt();
        webMeleePoisonDuration = getConfigNode().node("webbing").node("melee").node("poisonDurationTicks").getInt();
        webMeleePoisonAmplifier = getConfigNode().node("webbing").node("melee").node("poisonAmplifier").getInt();
        webRangeChance = getConfigNode().node("webbing").node("range").node("chance").getFloat();
        webRangeDuration = getConfigNode().node("webbing").node("range").node("webDurationSeconds").getInt();
        webRangePoisonDuration = getConfigNode().node("webbing").node("range").node("poisonDurationTicks").getInt();
        webRangePoisonAmplifier = getConfigNode().node("webbing").node("range").node("poisonAmplifier").getInt();
    }

    public int getPrimaryAbilityAmount() {
        return primaryAbilityAmount;
    }

    public int getPrimaryAbilitySpawnRadius() {
        return primaryAbilitySpawnRadius;
    }

    public float getWebMeleeChance() {
        return webMeleeChance;
    }

    public float getWebRangeChance() {
        return webRangeChance;
    }

    public int getWebMeleeDuration() {
        return webMeleeDuration;
    }

    public int getWebMeleePoisonDuration() {
        return webMeleePoisonDuration;
    }

    public int getWebMeleePoisonAmplifier() {
        return webMeleePoisonAmplifier;
    }

    public int getWebRangeDuration() {
        return webRangeDuration;
    }

    public int getWebRangePoisonDuration() {
        return webRangePoisonDuration;
    }

    public int getWebRangePoisonAmplifier() {
        return webRangePoisonAmplifier;
    }

    @Override
    public String toString() {
        return "ArachnidConfig{" +
                "primaryAbilityAmount=" + primaryAbilityAmount +
                ", primaryAbilitySpawnRadius=" + primaryAbilitySpawnRadius +
                ", webMeleeChance=" + webMeleeChance +
                ", webRangeChance=" + webRangeChance +
                ", webMeleeDuration=" + webMeleeDuration +
                ", webMeleePoisonDuration=" + webMeleePoisonDuration +
                ", webMeleePoisonAmplifier=" + webMeleePoisonAmplifier +
                ", webRangeDuration=" + webRangeDuration +
                ", webRangePoisonDuration=" + webRangePoisonDuration +
                ", webRangePoisonAmplifier=" + webRangePoisonAmplifier +
                '}';
    }
}