package org.originsreborn.fragaliciousorigins.origins.merling;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class MerlingConfig extends OriginConfig {
    public MerlingConfig() {
        super(OriginType.MERLING, "unique");
    }

    /**
     *
     */
    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
                primaryAbilityNode.node("swimMultiplier").set(2.5);
            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("primaryability");
                secondaryAbilityNode.node("range").set(10);
                secondaryAbilityNode.set("potionDuration").set(10);
                secondaryAbilityNode.set("dolphinsGraceAmplifier").set(0);
            CommentedConfigurationNode waterAttributesNode = getConfigNode().node("waterAttributes");
                waterAttributesNode.node("blockBreakSpeed").set(5.0);
                waterAttributesNode.node("entityInteractRange").set(2.0);
                waterAttributesNode.node("blockBreakSpeed").set(2.0);
                waterAttributesNode.node("gravity").set(1.0);
                waterAttributesNode.node("dolphinsGraceAmplifier").set(2);
                waterAttributesNode.node("deathDodgeChance").set(0.2);
            CommentedConfigurationNode hydrationNode = getConfigNode().node("hydrationNode");
                hydrationNode.set("duration").set(60);
                hydrationNode.set("waterBreathingPotionChance").set(0.2);
                hydrationNode.set("respirationNode").set(0.15);
                hydrationNode.set("hydrationDamage");
            CommentedConfigurationNode waterDamageNode = getConfigNode().set("WaterDamage");
                waterDamageNode.set("drownDamage").set(1);
                waterDamageNode.set("tridentMeleeMultiplier").set(1.2);
                waterDamageNode.set("tridentRangeMultiplier").set(1.5);
        } catch (SerializationException Exception) {

        }
    }
}
