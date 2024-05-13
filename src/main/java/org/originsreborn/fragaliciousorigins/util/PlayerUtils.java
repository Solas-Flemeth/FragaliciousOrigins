package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class PlayerUtils {
    /**
     * Set an attribute of a player.
     * If Knockback resistance, armor, or toughness, will update inventory
     *
     * @param player
     * @param attribute
     * @param amount
     */
    public static void setAttribute(Player player, Attribute attribute, float amount) {
        try{
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            switch (attribute) {
                case GENERIC_KNOCKBACK_RESISTANCE:
                case GENERIC_ARMOR:
                case GENERIC_ARMOR_TOUGHNESS:
                    attributeInstance.setBaseValue(attributeInstance.getDefaultValue() + amount);
                    player.updateInventory();
                    break;
                default:
                    attributeInstance.setBaseValue(amount);
                    break;
            }
            System.out.println("MODIFIED ATTRIBUTE TYPE: " + attribute.name() + " TO VALUE " + amount);
        }catch (NullPointerException e){
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
     * Resets a players attribute,and potentially forces an inventory to update
     *
     * @param player
     * @param attribute
     */
    public static void resetAttribute(Player player, Attribute attribute, boolean updateInventory) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
        if (updateInventory) {
            player.updateInventory();
        }
    }

    public static void setWalkSpeed(Player player, float walkSpeed) {
        player.setWalkSpeed(walkSpeed);
    }

    public static void resetWalkSpeed(Player player) {
        player.setWalkSpeed(0.2f);
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

    public static void setAttackSpeed(Player player, float attackspeed) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        attributeInstance.setBaseValue(attackspeed);
        player.updateInventory();
    }

    public static void resetAttackSpeed(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
        player.updateInventory();
    }

    public static void addEffect(Player player, PotionEffectType effectType, int amplifier, int duration) {
        player.addPotionEffect(new PotionEffect(effectType, duration, amplifier, false, false, false));
    }

    public static void addEffect(Player player, PotionEffectType effectType, int amplifier) {
        player.addPotionEffect(new PotionEffect(effectType, PotionEffect.INFINITE_DURATION, amplifier, false, false, false));
    }

    public static void addEffect(LivingEntity entity, PotionEffectType effectType, int amplifier, int duration) {
        entity.addPotionEffect(new PotionEffect(effectType, duration, amplifier, false, false, false));
    }

    public static void removeEffect(Player player, PotionEffectType effectType) {
        player.removePotionEffect(effectType);
    }

    public static void removeEffects(Player player) {
        Collection<PotionEffect> potionEffectCollection = player.getActivePotionEffects();
        for (PotionEffect effect : potionEffectCollection) {
            player.removePotionEffect(effect.getType());
        }
    }
}
