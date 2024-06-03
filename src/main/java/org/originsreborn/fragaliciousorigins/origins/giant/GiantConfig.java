package org.originsreborn.fragaliciousorigins.origins.giant;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class GiantConfig extends OriginConfig {
    public GiantConfig() {
        super(OriginType.GIANT, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        //Primary Ability - Roars - All nearby hostile mobs get weakness and slowness. Nearby players get Strength and haste
        try {
            //Primary Ability
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
                primaryAbilityNode.node("radius").set(15);
                CommentedConfigurationNode weaknessNode = primaryAbilityNode.node("weakness");
                    weaknessNode.node("duration").set(400);
                    weaknessNode.node("amplifier").set(1);
                CommentedConfigurationNode slownessNode = primaryAbilityNode.node("slowness");
                    slownessNode.node("duration").set(400);
                    slownessNode.node("amplifier").set(1);
                CommentedConfigurationNode strengthNode = primaryAbilityNode.node("strength");
                    strengthNode.node("duration").set(400);
                    strengthNode.node("amplifier").set(1);
                CommentedConfigurationNode hasteNode = primaryAbilityNode.node("haste");
                    hasteNode.node("duration").set(400);
                    hasteNode.node("amplifier").set(1);
        CommentedConfigurationNode foodNode = getConfigNode().node("food");
            foodNode.node("hungerLossChance").set(0.03);
        } catch (SerializationException Exception) {

        }
    }
    public int getPrimaryAbilityRadius() {
        return getConfigNode().node("primaryability").node("radius").getInt();
    }

    public int getPrimaryAbilityWeaknessDuration() {
        return getConfigNode().node("primaryability").node("weakness").node("duration").getInt();
    }

    public int getPrimaryAbilityWeaknessAmplifier() {
        return getConfigNode().node("primaryability").node("weakness").node("amplifier").getInt();
    }

    public int getPrimaryAbilitySlownessDuration() {
        return getConfigNode().node("primaryability").node("slowness").node("duration").getInt();
    }

    public int getPrimaryAbilitySlownessAmplifier() {
        return getConfigNode().node("primaryability").node("slowness").node("amplifier").getInt();
    }

    public int getPrimaryAbilityStrengthDuration() {
        return getConfigNode().node("primaryability").node("strength").node("duration").getInt();
    }

    public int getPrimaryAbilityStrengthAmplifier() {
        return getConfigNode().node("primaryability").node("strength").node("amplifier").getInt();
    }

    public int getPrimaryAbilityHasteDuration() {
        return getConfigNode().node("primaryability").node("haste").node("duration").getInt();
    }

    public int getPrimaryAbilityHasteAmplifier() {
        return getConfigNode().node("primaryability").node("haste").node("amplifier").getInt();
    }

    // Food - Hunger Loss Cancel Chance
    public double getHungerLossChance() {
        return getConfigNode().node("food").node("hungerLossChance").getDouble();
    }

}
