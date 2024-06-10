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

    public EnderianConfig() {
        super(OriginType.ENDERIAN, "unique");
    }

    @Override
    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode primaryAbilityNode = getConfigNode().node("primaryability");
            CommentedConfigurationNode primaryOverworldNode = primaryAbilityNode.node("overworld");
            primaryOverworldNode.node("range").set(20);
            primaryOverworldNode.node("cost").set(15);

            CommentedConfigurationNode primaryEndNode = primaryAbilityNode.node("end");
            primaryEndNode.node("range").set(50);
            primaryEndNode.node("cost").set(5);

            CommentedConfigurationNode secondaryAbilityNode = getConfigNode().node("secondaryability");
            CommentedConfigurationNode secondaryOverworldNode = secondaryAbilityNode.node("overworld");
            secondaryOverworldNode.node("range").set(10);
            secondaryOverworldNode.node("cost").set(5);

            CommentedConfigurationNode secondaryEndNode = secondaryAbilityNode.node("end");
            secondaryEndNode.node("range").set(5);
            secondaryEndNode.node("cost").set(2);

            CommentedConfigurationNode chargeNode = getConfigNode().node("charge");
            CommentedConfigurationNode enderpearlNode = chargeNode.node("enderpearl");
            enderpearlNode.node("endRefill").set(10);
            enderpearlNode.node("overworldRefill").set(5);
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
        secondaryEndRange = getConfigNode().node("secondaryability").node("end").node("range").getInt();
        secondaryEndCost = getConfigNode().node("secondaryability").node("end").node("cost").getInt();

        enderpearlEndRefill = getConfigNode().node("charge").node("enderpearl").node("endRefill").getInt();
        enderpearlOverworldRefill = getConfigNode().node("charge").node("enderpearl").node("overworldRefill").getInt();
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
}
