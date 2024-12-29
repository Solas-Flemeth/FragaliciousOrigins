package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

public class PlayerUtils {
    /**
     * Set an attribute of a player.
     * <p>
     * Warning:
     * Attack Speed, Movement Speed, Scale, Stepheight, Jump Strength, gravity, block break speed, and fall damage are all multiplicative
     *
     * @param player
     * @param attribute
     * @param amount
     */
    public static void setAttribute(Player player, Attribute attribute, double amount) {
        try {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            switch (attribute.getKey().asString()) {
                //additional
                case "minecraft:attack_damage":
                case "minecraft:armor_toughness":
                case "minecraft:armor":
                    attributeInstance.setBaseValue(attributeInstance.getDefaultValue() + amount);
                    player.updateInventory();
                    break;
                //multiplicative
                case "minecraft:attack_speed":
                case "minecraft:scale":
                case "minecraft:step_height":
                case "minecraft:jump_strength":
                case "minecraft:block_break_speed":
                case "minecraft:gravity":
                case "minecraft:fall_damage_multiplier":
                case "minecraft:block_interaction_range":
                case "minecraft:entity_interaction_range":
                case "minecraft:burning_time":
                    attributeInstance.setBaseValue(attributeInstance.getDefaultValue() * amount);
                    break;
                case "minecraft:movement_speed":
                    attributeInstance.setBaseValue(0.1 * amount);
                    break;
                case "minecraft:knockback_resistance":
                case "minecraft:luck":
                case "minecraft:max_health":
                case "minecraft:safe_fall_distance":
                case "minecraft:explosion_knockback_resistance":
                case "minecraft:mining_efficiency":
                case "minecraft:movement_efficiency":
                case "minecraft:oxygen_bonus":
                case "minecraft:sneaking_speed":
                case "minecraft:submerged_mining_speed":
                case "minecraft:sweeping_damage_ratio":
                case "minecraft:water_movement_efficiency":
                    attributeInstance.setBaseValue(amount);
                    break;
                //setters
                default:
                    FragaliciousOrigins.INSTANCE.getLogger().warning(attribute.key() + " is not a mapped attribute");
                    attributeInstance.setBaseValue(amount);
                    break;
            }
        } catch (NullPointerException e) {
            FragaliciousOrigins.INSTANCE.getLogger().severe(attribute.key() + " is not a valid attribute for players");
        }
    }

    public static void setAttribute(LivingEntity entity, Attribute attribute, double amount) {
        try {
            AttributeInstance attributeInstance = entity.getAttribute(attribute);
            switch (attribute.getKey().asString()) {
                //additional
                case "minecraft:armor":
                case "attack_damage":
                case "minecraft:armor_toughness":
                    attributeInstance.setBaseValue(attributeInstance.getDefaultValue() + amount);
                    break;
                //multiplicative
                case "minecraft:attack_speed":
                case "minecraft:scale":
                case "minecraft:step_height":
                case "minecraft:jump_strength":
                case "minecraft:block_break_speed":
                case "minecraft:gravity":
                case "minecraft:fall_damage_multiplier":
                case "minecraft:block_interaction_range":
                case "minecraft:entity_interaction_range":
                case "minecraft:burning_time":
                    attributeInstance.setBaseValue(attributeInstance.getDefaultValue() * amount);
                    break;
                case "minecraft:movement_speed":
                    attributeInstance.setBaseValue(0.1 * amount);
                    break;
                //setters
                case "minecraft:knockback_resistance":
                case "minecraft:luck":
                case "minecraft:max_health":
                case "minecraft:safe_fall_distance":
                case "minecraft:explosion_knockback_resistance":
                case "minecraft:mining_efficiency":
                case "minecraft:movement_efficiency":
                case "minecraft:oxygen_bonus":
                case "minecraft:sneaking_speed":
                case "minecraft:submerged_mining_speed":
                case "minecraft:sweeping_damage_ratio":
                case "minecraft:water_movement_efficiency":
                    attributeInstance.setBaseValue(amount);
                    break;
                default:
                    FragaliciousOrigins.INSTANCE.getLogger().warning(attribute.key() + " is not a mapped attribute");
                    attributeInstance.setBaseValue(amount);
                    break;
            }
        } catch (NullPointerException e) {
            FragaliciousOrigins.INSTANCE.getLogger().severe(attribute.key() + " is not a valid attribute for players");
        }
    }

    /**
     * Resets a players specific attribute
     *
     * @param player
     * @param attribute
     */
    public static void resetAttribute(Player player, Attribute attribute) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
    }

    /**
     * Resets a players specific attribute,and potentially forces an inventory to update
     *
     * @param player
     * @param attribute
     */
    public static void resetAttribute(Player player, Attribute attribute, boolean updateInventory) {
        resetAttribute(player, attribute);
        if (updateInventory) {
            player.updateInventory();
        }
    }

    public static double getAttribute(LivingEntity entity, Attribute attribute) {
        try {
            AttributeInstance attributeInstance = entity.getAttribute(attribute);
            return attributeInstance.getValue();
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    public static void setFlySpeed(Player player, float flySpeed) {
        player.setFlySpeed(flySpeed);
    }

    public static void resetFlySpeed(Player player) {
        player.setFlySpeed(0.1f);
    }

    public static void setSaturation(Player player, float saturation) {
        if (player.getFoodLevel() < saturation) {
            player.setSaturation(player.getFoodLevel());
        } else {
            player.setSaturation(saturation);
        }

    }

    public static void addSaturation(Player player, float saturation) {
        float updatedSaturation = player.getSaturation() + saturation;
        setSaturation(player, updatedSaturation);
    }

    public static void setHunger(Player player, int food) {
        if (food > 20) {
            player.setFoodLevel(20);
        } else {
            player.setFoodLevel(food);
        }

    }

    public static void addHunger(Player player, int food) {
        setHunger(player, player.getFoodLevel() + food);
    }

    public static void addExhaustion(Player player, float exhaustion) {
        player.setExhaustion(player.getExhaustion() + exhaustion);
    }

    /**
     * Checks if there is a solid block above the player up to the height of roofHeight.
     *
     * @param player
     * @param roofHeight
     * @return
     */
    public static boolean isUnderRoof(Player player, int roofHeight) {
        Location location = player.getLocation();
        int xCord = location.getBlockX();
        int zCord = location.getBlockZ();
        int yCord = location.getBlockY();
        World world = location.getWorld();
        for (int i = 1; i < roofHeight; i++) {
            Location checkLocation = new Location(world, xCord, yCord + 2 + i, zCord);
            if (checkLocation.getBlock().isSolid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the total Experience of the player
     *
     * @param player
     * @return
     */
    public static int getExperience(Player player) {
        return player.calculateTotalExperiencePoints();
    }

    /**
     * Adds experience to the player
     *
     * @param player
     * @param value
     * @return
     */
    public static void addExperience(Player player, int value) {
        int total = getExperience(player);
        if (value >= 0) {
            player.setExperienceLevelAndProgress(total + value);
        }
    }

    /**
     * Subtracts experience from the player
     *
     * @param player
     * @param value
     * @return
     */
    public static void removeExperience(Player player, int value) {
        int total = getExperience(player);
        if (total > value) {
            player.setExperienceLevelAndProgress(total - value);
        } else {
            player.setExperienceLevelAndProgress(0);

        }
    }

    /**
     * Spawns an item underneath the player
     *
     * @param player
     * @param item
     */
    public static void spawnItemOnPlayer(Player player, ItemStack item) {
        player.getWorld().dropItem(player.getLocation(), item);
    }

}
