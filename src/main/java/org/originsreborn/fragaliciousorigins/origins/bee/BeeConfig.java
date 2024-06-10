package org.originsreborn.fragaliciousorigins.origins.bee;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;


public class BeeConfig extends OriginConfig {
    private int radius, phytoAmp, phytoduration;
    private double bottleChance, combChance, flowerChance, bonemealChance;
    //secondary
    private double stingChance, stingMultiplier;
    private int hiveradius, hiveBeeAmp, hivePhytoAmp, hivePhytoDuration, hiveBeeDuration;
    private double hiveBeeChance, hivePhytoChance;
    public BeeConfig() {
        super(OriginType.BEE, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try{
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryAbility");
                primaryAbilityNode.node("radius").set(6);
                primaryAbilityNode.node("honeyBottleChance").set(0.33);
                primaryAbilityNode.node("honeyCombChance").set(0.25);
                primaryAbilityNode.node("flowerChance").set(0.25);
                primaryAbilityNode.node("bonemealChance").set(0.5);
                primaryAbilityNode.node("phytokinHealthBoostAmplifier").set(1);
                primaryAbilityNode.node("phytokinHealthBoostDuration").set(20);
            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryAbility");
                secondaryAbilityNode.node("finalStingChance").set(0.01);
                secondaryAbilityNode.node("finalStingMultiplier").set(10.0);
            CommentedConfigurationNode hiveMindNode = getConfigNode().node("hiveMind");
                hiveMindNode.node("radius").set(6);
                CommentedConfigurationNode beeRegenNode = hiveMindNode.node("beeRegen");
                    beeRegenNode.node("chance").set(0.03);
                    beeRegenNode.node("duration").set(2);
                    beeRegenNode.node("amplifier").set(1);
                CommentedConfigurationNode phytokinSaturationNode = hiveMindNode.node("phytokinSaturation");
                    phytokinSaturationNode.node("chance").set(0.03);
                    phytokinSaturationNode.node("duration").set(3);
                    phytokinSaturationNode.node("amplifier").set(1);
        }catch (Exception e){

        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        radius = getConfigNode().node("primaryAbility").node("radius").getInt();
        bottleChance = getConfigNode().node("primaryAbility").node("honeyBottleChance").getDouble();
        combChance = getConfigNode().node("primaryAbility").node("honeyCombChance").getDouble();
        flowerChance = getConfigNode().node("primaryAbility").node("flowerChance").getDouble();
        bonemealChance = getConfigNode().node("primaryAbility").node("bonemealChance").getDouble();
        phytoAmp = getConfigNode().node("primaryAbility").node("phytokinHealthBoostAmplifier").getInt();
        phytoduration =getConfigNode().node("primaryAbility").node("phytokinHealthBoostDuration").getInt();
        stingChance = getConfigNode().node("secondaryAbility").node("finalStingChance").getDouble();
        stingMultiplier =getConfigNode().node("secondaryAbility").node("finalStingMultiplier").getDouble();
        hiveradius = getConfigNode().node("hiveMind").node("radius").getInt();
        hiveBeeChance = getConfigNode().node("hiveMind").node("beeRegen").node("chance").getDouble();
        hiveBeeDuration = getConfigNode().node("hiveMind").node("beeRegen").node("duration").getInt();
        hiveBeeAmp =getConfigNode().node("hiveMind").node("beeRegen").node("amplifier").getInt();
        hivePhytoChance =getConfigNode().node("hiveMind").node("phytokinSaturation").node("chance").getDouble();
        hivePhytoDuration=getConfigNode().node("hiveMind").node("phytokinSaturation").node("duration").getInt();
        hivePhytoAmp=getConfigNode().node("hiveMind").node("phytokinSaturation").node("amplifier").getInt();
    }

    public int getPrimaryAbilityRadius() {
        return radius;
    }

    public double getPrimaryAbilityHoneyBottleChance() {
        return bottleChance;
    }

    public double getPrimaryAbilityHoneyCombChance() {
        return  combChance;
    }

    public double getPrimaryAbilityFlowerChance() {
        return flowerChance;
    }

    public double getPrimaryAbilityBonemealChance() {
        return bonemealChance;
    }

    public int getPrimaryAbilityPhytokinHealthBoostAmplifier() {
        return phytoAmp;
    }

    public int getPrimaryAbilityPhytokinHealthBoostDuration() {
        return phytoduration;
    }

    public double getSecondaryAbilityFinalStingChance() {
        return stingChance;
    }

    public double getSecondaryAbilityFinalStingMultiplier() {
        return stingMultiplier;
    }

    public int getHiveMindRadius() {
        return hiveradius;
    }


    public double getHiveMindBeeRegenChance() {
        return hiveBeeChance;
    }

    public int getHiveMindBeeRegenDuration() {
        return hiveBeeDuration;
    }

    public int getHiveMindBeeRegenAmplifier() {
        return hiveBeeAmp;
    }

    public double getHiveMindPhytokinSaturationChance() {
        return hivePhytoChance;
    }

    public int getHiveMindPhytokinSaturationDuration() {
        return hivePhytoDuration;
    }

    public int getHiveMindPhytokinSaturationAmplifier() {
        return hivePhytoAmp;
    }

}
