package org.originsreborn.fragaliciousorigins.origins.wip;

import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

import static org.bukkit.potion.PotionEffectType.*;

/**
 *  - primary - enchant XP Boost + reroll enchant KEY
 *  - Secondary - Shift potion type
 *    - Redstone - Increase potion duration
 *    - Glowstone - Increase potion amplifier
 *    - Spider Eye - Changes all negative effects to positive
 *  - Keeps XP on death
 *  - Midas Touch
 *    - All 75% chance turn to ores to gold.
 *    - 75% chance for redstone to turn to lapis.
 *    - Effected Diamonds & emeralds have a chance 10% to become golden diamonds
 *  - Romeo & Juliet - cannot trade with villagers
 *  - Chance to keep XP on enchanting
 *  - 15% damage boost with explosives (crossbows)
 *  - Potion arrows now splash cause lingering AOEs
 *  - MCMMO XP boost for alchemy
 */
public class Alchemist extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ALCHEMIST);
    public Alchemist(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ALCHEMIST, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {

    }

    @Override
    public void originParticle(int tickNum) {

    }

    @Override
    public void primaryAbilityLogic() {

    }

    @Override
    public void secondaryAbilityLogic() {

    }

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.HARD;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }


    private static PotionEffectType getPositiveOpposite(PotionEffectType type){
        if (type.equals(BAD_OMEN)) {
            return HERO_OF_THE_VILLAGE;
        } else if (type.equals(WITHER) || type.equals(POISON)) {
            return REGENERATION;
        } else if (type.equals(SLOWNESS)) {
            return SPEED;
        } else if (type.equals(INSTANT_DAMAGE)) {
            return INSTANT_HEALTH;
        } else if (type.equals(WEAKNESS)) {
            return STRENGTH;
        } else if (type.equals(HUNGER)) {
            return SATURATION;
        } else if (type.equals(MINING_FATIGUE)) {
            return HASTE;
        } else if (type.equals(BLINDNESS)) {
            return NIGHT_VISION;
        } else if (type.equals(DARKNESS)) {
            return GLOWING;
        }
        return type;

    }
}
