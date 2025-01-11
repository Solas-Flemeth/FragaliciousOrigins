package org.originsreborn.fragaliciousorigins.origins.blazeborn;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

/*
THINGS IMPACTED BY HEAT NO IN MAIN CONFIG:
- smelt Multiplier Cost
- Heat min
- Heat max
- burnTicksOnHit
- burnChance;
- fuelGainedFromFire/Lava
- regenRate

BOOLEANS:
- can get fuel from fire / lava
- can sprint
 */
public class FuelPerksConfig extends OriginConfig {
    private int fuelMinimum, burnTicks;
    private float smeltMultiplier;
    private int fuelOnDamageLoss, passiveDrain;
    private int regenRate;
    private float regenAmount;
    public FuelPerksConfig(String tier) {
        super(OriginType.CHICKEN, "fuel_" + tier);
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode fuelNode = getConfigNode().node("fuel");
            fuelNode.node("minimum").set(500);
            fuelNode.node("smeltMultiplier").set(0.5f);
            fuelNode.node("onDamageLoss").set(5);
            fuelNode.node("passiveLoss").set(1);
            fuelNode.node("regenLoss").set(10);
            CommentedConfigurationNode regenNode = getConfigNode().node("regeneration");
            regenNode.node("rate").set(5);
            regenNode.node("amount").set(0.2f);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        fuelMinimum = getConfigNode().node("fuel").node("miminimum").getInt(500);
        smeltMultiplier = getConfigNode().node("fuel").node("smeltMultiplier").getFloat(0.5f);
        fuelOnDamageLoss = getConfigNode().node("fuel").node("onDamageLoss").getInt(5);
        passiveDrain = getConfigNode().node("fuel").node("passiveLoss").getInt(1);
        regenRate = getConfigNode().node("regeneration").node("rate").getInt(5);
        regenAmount = getConfigNode().node("regeneration").node("amount").getFloat(0.2f);
    }

    public int getFuelMinimum() {
        return fuelMinimum;
    }

    public int getBurnTicks() {
        return burnTicks;
    }

    public float getSmeltMultiplier() {
        return smeltMultiplier;
    }

    public int getFuelOnDamageLoss() {
        return fuelOnDamageLoss;
    }

    public int getPassiveDrain() {
        return passiveDrain;
    }

    public int getRegenRate() {
        return regenRate;
    }

    public float getRegenAmount() {
        return regenAmount;
    }
}
