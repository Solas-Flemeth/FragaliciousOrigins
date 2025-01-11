package org.originsreborn.fragaliciousorigins.origins.chicken;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ChickenConfig extends OriginConfig {
    private double eggSpawnChance;
    private float explosionPower, explosiveChance;
    private double eggDamage;
    private double bossEggChance,specialEggChance,hostileEggChance; //otherwise default
    public ChickenConfig() {
        super(OriginType.CHICKEN, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try{
            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
                secondaryAbilityNode.node("explosionPower").set(1.0f);
            CommentedConfigurationNode passiveNode = getConfigNode().node("passive");
                passiveNode.node("eggSpawnChance").set(0.05f);
                passiveNode.node("eggDamage").set(1.0f);
                passiveNode.node("explosiveEggChance").set(0.05f);
            CommentedConfigurationNode eggChanceNode = getConfigNode().node("eggChance");
                eggChanceNode.node("bossEggChance").set(0.01);
                eggChanceNode.node("specialEggChance").set(0.29);
                eggChanceNode.node("hostileEggChance").set(0.2);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        eggSpawnChance = getConfigNode().node("passive").node("eggSpawnChance").getDouble(1.0f);
        explosionPower = getConfigNode().node("secondaryAbility").node("explosionPower").getFloat(0.05f);
        eggDamage = getConfigNode().node("passive").node("eggDamage").getDouble(1.0f);
        explosiveChance = getConfigNode().node("passive").node("explosiveEggChance").getFloat(0.05f);
        bossEggChance = getConfigNode().node("eggChance").node("bossEggChance").getDouble(0.01);
        specialEggChance = getConfigNode().node("eggChance").node("specialEggChance").getDouble(0.29);
        hostileEggChance = getConfigNode().node("eggChance").node("hostileEggChance").getDouble(0.2);

    }

    public double getEggSpawnChance() {
        return eggSpawnChance;
    }

    public float getExplosionPower() {
        return explosionPower;
    }

    public double getEggDamage() {
        return eggDamage;
    }

    public double getBossEggChance() {
        return bossEggChance;
    }

    public double getSpecialEggChance() {
        return specialEggChance;
    }

    public double getHostileEggChance() {
        return hostileEggChance;
    }
}
