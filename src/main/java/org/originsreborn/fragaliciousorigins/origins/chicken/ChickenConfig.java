package org.originsreborn.fragaliciousorigins.origins.chicken;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ChickenConfig extends OriginConfig {
    private double eggSpawnChance;
    private float explosionPower;
    private double eggDamage;
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
                secondaryAbilityNode.node(explosionPower).set(1.0f);
            CommentedConfigurationNode passiveNode = getConfigNode().node("passive");
                passiveNode.node(eggSpawnChance).set(0.05);
                passiveNode.node(eggDamage).set(1.0f);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        eggSpawnChance = getConfigNode().node("passive").node("eggSpawnChance").getDouble();
        explosionPower = getConfigNode().node("secondaryAbility").node(explosionPower).getFloat();
        eggDamage = getConfigNode().node("passive").node("eggDamage").getDouble();
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
}
