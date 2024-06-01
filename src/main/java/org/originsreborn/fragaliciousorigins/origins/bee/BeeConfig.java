package org.originsreborn.fragaliciousorigins.origins.bee;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class BeeConfig extends OriginConfig {
    public BeeConfig() {
        super(OriginType.BEE, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try{
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
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
    public int getPrimaryAbilityRadius() {
        return getConfigNode().node("primaryability").node("radius").getInt();
    }

    public double getPrimaryAbilityHoneyBottleChance() {
        return getConfigNode().node("primaryability").node("honeyBottleChance").getDouble();
    }

    public double getPrimaryAbilityHoneyCombChance() {
        return getConfigNode().node("primaryability").node("honeyCombChance").getDouble();
    }

    public double getPrimaryAbilityFlowerChance() {
        return getConfigNode().node("primaryability").node("flowerChance").getDouble();
    }

    public double getPrimaryAbilityBonemealChance() {
        return getConfigNode().node("primaryability").node("bonemealChance").getDouble();
    }

    public int getPrimaryAbilityPhytokinHealthBoostAmplifier() {
        return getConfigNode().node("primaryability").node("phytokinHealthBoostAmplifier").getInt();
    }

    public int getPrimaryAbilityPhytokinHealthBoostDuration() {
        return getConfigNode().node("primaryability").node("phytokinHealthBoostDuration").getInt();
    }

    public double getSecondaryAbilityFinalStingChance() {
        return getConfigNode().node("secondaryAbility").node("finalStingChance").getDouble();
    }

    public double getSecondaryAbilityFinalStingMultiplier() {
        return getConfigNode().node("secondaryAbility").node("finalStingMultiplier").getDouble();
    }

    public int getHiveMindRadius() {
        return getConfigNode().node("hiveMind").node("radius").getInt();
    }


    public double getHiveMindBeeRegenChance() {
        return getConfigNode().node("hiveMind").node("beeRegen").node("chance").getDouble();
    }

    public int getHiveMindBeeRegenDuration() {
        return getConfigNode().node("hiveMind").node("beeRegen").node("duration").getInt();
    }

    public int getHiveMindBeeRegenAmplifier() {
        return getConfigNode().node("hiveMind").node("beeRegen").node("amplifier").getInt();
    }

    public double getHiveMindPhytokinSaturationChance() {
        return getConfigNode().node("hiveMind").node("phytokinSaturation").node("chance").getDouble();
    }

    public int getHiveMindPhytokinSaturationDuration() {
        return getConfigNode().node("hiveMind").node("phytokinSaturation").node("duration").getInt();
    }

    public int getHiveMindPhytokinSaturationAmplifier() {
        return getConfigNode().node("hiveMind").node("phytokinSaturation").node("amplifier").getInt();
    }

}
