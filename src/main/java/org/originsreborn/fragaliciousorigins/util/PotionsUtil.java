package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

import static org.bukkit.potion.PotionEffectType.*;

public class PotionsUtil {
    public static void addEffect(Player player, PotionEffectType effectType, int amplifier, int duration) {
        player.addPotionEffect(new PotionEffect(effectType, duration, amplifier, false, false, false));
    }

    public static void addEffect(Player player, PotionEffectType effectType, int amplifier) {
        player.addPotionEffect(new PotionEffect(effectType, PotionEffect.INFINITE_DURATION, amplifier, false, false, false));
    }
    public static void addEffect(LivingEntity entity, PotionEffectType effectType, int amplifier) {
        entity.addPotionEffect(new PotionEffect(effectType, PotionEffect.INFINITE_DURATION, amplifier, false, false, false));
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

    /**
     * Returns the potion effect with its positive alternative
     * @param effect
     * @return
     */
    public static PotionEffect getPositiveEffect(PotionEffect effect){
        PotionEffectType type = effect.getType();
        //Potion effects are dumb and must be treated as if/else and not switches
        if (type.equals(BAD_OMEN)) {
            type = HERO_OF_THE_VILLAGE;
        } else if (type.equals(POISON) || type.equals(WITHER)) {
            type = REGENERATION;
        } else if (type.equals(DARKNESS) || type.equals(BLINDNESS)) {
            type = NIGHT_VISION;
        }else if(type.equals(NAUSEA)){
            type = WATER_BREATHING;
        } else if (type.equals(WEAKNESS)) {
            type = STRENGTH;
        } else if (type.equals(SLOWNESS)) {
            type = SPEED;
        } else if (type.equals(MINING_FATIGUE)) {
            type = HASTE;
        } else if (type.equals(HUNGER)) {
            type = SATURATION;
        } else {
            return effect;
        }
        return effect.withType(type);
    }

    /**
     * Modifies the amplifier by an amount. If the end result is less than 0, sets the amplifier to 0
     * @param effect
     * @param modifier
     * @return
     */
    public static PotionEffect getAmplifiedEffect(PotionEffect effect, int modifier){
        int modifiedAmplifier = effect.getAmplifier() + modifier;
        if(modifiedAmplifier < 0){
            modifiedAmplifier = 0;
        }
        return effect.withAmplifier(modifiedAmplifier);
    }

    /**
     * Generates a copy of the potion effect with an amplified duration
     * @param effect
     * @param modifier
     * @return
     */
    public static PotionEffect getAmplifiedDuration(PotionEffect effect, double modifier){
        int duration = (int) (((double) effect.getDuration()) * modifier);
        return effect.withDuration(duration);
    }
}
