/**
 * Primary ability - teleport on demand (15s/5s)
 * Secondary ability - Teleport on damage (5s/2s). has a chance to dodge damage on hit (25% range, 10% all others)
 * - Using enderpearls speeds up teleportation recharge (No damage). (8s/15s seconds)
 * - has teleport bar. (60s)
 * - 1.20x attack range (1.5x in end)
 * - 1.20x build range  (1.5x in end)
 * - Teleporting grants minor damage resistance for 2 seconds
 * - Gains 4 hearts (total 28 HP) in end.
 * - Water deals rapid damage
 * - Teleports to bed if entering void
 * - 1.5x size
 */
package org.originsreborn.fragaliciousorigins.origins.enderian;

import org.originsreborn.fragaliciousorigins.configs.OriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class EnderianConfig extends OriginConfig {
    private int primaryOverworldRange, primaryOverworldCost, primaryEndRange, primaryEndCost;
    private int secondaryOverworldRange, secondaryOverworldCost, secondaryEndRange, secondaryEndCost;
    private int enderpearlEndRefill, enderpearlOverworldRefill;
    private double secondaryOverworldDodgeChance, secondaryEndDodgeChance;
    private int chargeCapacity, invulnFrames;
    private double health, blockInteractionRange, entityInteractionRange;
    public EnderianConfig() {
        super(OriginType.ENDERIAN, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
            CommentedConfigurationNode primaryOverworldNode = primaryAbilityNode.node("overworld");
            primaryOverworldNode.node("range").set(20);
            primaryOverworldNode.node("cost").set(12);

            CommentedConfigurationNode primaryEndNode = primaryAbilityNode.node("end");
            primaryEndNode.node("range").set(50);
            primaryEndNode.node("cost").set(5);

            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryability");
            CommentedConfigurationNode secondaryOverworldNode = secondaryAbilityNode.node("overworld");
            secondaryOverworldNode.node("range").set(10);
            secondaryOverworldNode.node("cost").set(5);
            secondaryOverworldNode.node("dodgeChance").set(0.15);

            CommentedConfigurationNode secondaryEndNode = secondaryAbilityNode.node("end");
            secondaryEndNode.node("range").set(5);
            secondaryEndNode.node("cost").set(2);
            secondaryEndNode.node("dodgeChance").set(0.3);

            CommentedConfigurationNode chargeNode = getConfigNode().node("charge");
            CommentedConfigurationNode enderpearlNode = chargeNode.node("enderpearl");
            enderpearlNode.node("endRefill").set(30);
            enderpearlNode.node("overworldRefill").set(15);
            chargeNode.node("chargeCapacity").set(120);
            getConfigNode().node("invulnFrames").set(5);
            CommentedConfigurationNode endStatsNode = getConfigNode().node("endStats");
            endStatsNode.node("health").set(28.0);
            endStatsNode.node("blockInteractRange").set(1.75);
            endStatsNode.node("entityInteractRange").set(1.75);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void defineVariables() {
        primaryOverworldRange = getConfigNode().node("primaryability").node("overworld").node("range").getInt();
        primaryOverworldCost = getConfigNode().node("primaryability").node("overworld").node("cost").getInt();
        primaryEndRange = getConfigNode().node("primaryability").node("end").node("range").getInt();
        primaryEndCost = getConfigNode().node("primaryability").node("end").node("cost").getInt();

        secondaryOverworldRange = getConfigNode().node("secondaryability").node("overworld").node("range").getInt();
        secondaryOverworldCost = getConfigNode().node("secondaryability").node("overworld").node("cost").getInt();
        secondaryOverworldDodgeChance = getConfigNode().node("secondaryability").node("overworld").node("dodgeChance").getDouble();
        secondaryEndRange = getConfigNode().node("secondaryability").node("end").node("range").getInt();
        secondaryEndCost = getConfigNode().node("secondaryability").node("end").node("cost").getInt();
        secondaryEndDodgeChance = getConfigNode().node("secondaryability").node("end").node("dodgeChance").getDouble();

        enderpearlEndRefill = getConfigNode().node("charge").node("enderpearl").node("endRefill").getInt();
        enderpearlOverworldRefill = getConfigNode().node("charge").node("enderpearl").node("overworldRefill").getInt();
        chargeCapacity = getConfigNode().node("charge").node("chargeCapacity").getInt();
        invulnFrames = getConfigNode().node("invulnFrames").getInt();
        health = getConfigNode().node("endStats").node("health").getDouble();
        blockInteractionRange = getConfigNode().node("endStats").node("blockInteractRange").getDouble();
        entityInteractionRange = getConfigNode().node("endStats").node("entityInteractRange").getDouble();

    }

    public int getPrimaryOverworldRange() {
        return primaryOverworldRange;
    }

    public int getPrimaryOverworldCost() {
        return primaryOverworldCost;
    }

    public int getPrimaryEndRange() {
        return primaryEndRange;
    }

    public int getPrimaryEndCost() {
        return primaryEndCost;
    }

    public int getSecondaryOverworldRange() {
        return secondaryOverworldRange;
    }

    public int getSecondaryOverworldCost() {
        return secondaryOverworldCost;
    }

    public int getSecondaryEndRange() {
        return secondaryEndRange;
    }

    public int getSecondaryEndCost() {
        return secondaryEndCost;
    }

    public int getEnderpearlEndRefill() {
        return enderpearlEndRefill;
    }

    public int getEnderpearlOverworldRefill() {
        return enderpearlOverworldRefill;
    }

    public int getChargeCapacity() {
        return chargeCapacity;
    }

    public double getSecondaryOverworldDodgeChance() {
        return secondaryOverworldDodgeChance;
    }

    public double getSecondaryEndDodgeChance() {
        return secondaryEndDodgeChance;
    }

    public double getHealth() {
        return health;
    }

    public double getBlockInteractionRange() {
        return blockInteractionRange;
    }

    public double getEntityInteractionRange() {
        return entityInteractionRange;
    }
    public int getInvulnFrames(){
        return invulnFrames;
    }
}
