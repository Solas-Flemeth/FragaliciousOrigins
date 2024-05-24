package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability - Toggle Swim Speed
 * Secondary Ability - Toggle gravity in water
 * - Slower mine speed on surface but faster mine speed in water
 * - Being wet grants a 20% chance to avoid death and restore health to full on a killing blow
 * - Tridents deal bonus damage
 * - Deals natural bonus water damage to waterphobic targets (Blazes, Blazeborn, Enderian, Endermen, snowmen, magma cubes, etc)
 * - Takes bonus damage from fire and burns 2x longer
 * - Must be in water or will suffocate. Waterbreathing and respiration increase duration without water.
 *    - Can refill water with water bottles, rain, or stepping in water.
 * - Nearby players get water breathing if in water and the merling is in water for 5 seconds (ticks every 3 seconds)
 * - Bonus Luck
 * - Has an exclusive loot table for when fishing
 */
public class Merling extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.MERLING);
    public Merling(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.MERLING, state, customDataString);
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
    public String serializeCustomData() {
        return "";
    }

    @Override
    public void deserializeCustomData(String customData) {

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
}
