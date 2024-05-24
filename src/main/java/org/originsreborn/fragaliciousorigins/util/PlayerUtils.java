package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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
            switch (attribute) {
                //additional
                case GENERIC_KNOCKBACK_RESISTANCE:
                case GENERIC_ARMOR:
                case GENERIC_ARMOR_TOUGHNESS:
                    attributeInstance.setBaseValue(attributeInstance.getDefaultValue() + amount);
                    player.updateInventory();
                    break;
                //multiplicative
                case GENERIC_ATTACK_SPEED:
                case GENERIC_SCALE:
                case GENERIC_STEP_HEIGHT:
                case GENERIC_JUMP_STRENGTH:
                case PLAYER_BLOCK_BREAK_SPEED:
                case GENERIC_GRAVITY:
                case GENERIC_FALL_DAMAGE_MULTIPLIER:
                    attributeInstance.setBaseValue(attributeInstance.getDefaultValue() * amount);
                    break;
                case GENERIC_MOVEMENT_SPEED:
                    attributeInstance.setBaseValue(0.1 * amount);
                    break;
                //setters
                default:
                    attributeInstance.setBaseValue(amount);
                    break;
            }
        } catch (NullPointerException e) {
            System.err.println(attribute.name() + " is not a valid attribute for players");
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
    public static double getAttribute(LivingEntity entity, Attribute attribute){
        try {
            AttributeInstance attributeInstance = entity.getAttribute(attribute);
            return attributeInstance.getValue();
        } catch(NullPointerException e){
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
        player.setSaturation(saturation);
    }

    public static void setHunger(Player player, int food) {
        player.setFoodLevel(food);
    }

}
