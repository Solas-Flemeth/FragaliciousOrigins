package org.originsreborn.fragaliciousorigins.origins.wip.postrelease;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability: Cheers! - All nearby players gain haste for +30 seconds. drunkness can scale the players haste and number of nearby players. Gain 25% drunkness
 * Secondary Ability: Clear - Removes a 7x7x7 chunk of all stone/gravel/netherrack from nearby the player. Scales based on nearby number of players
 * - 50% cooldown time on superbreaker and excavation ability
 * - drunkness increase players damage resistance and mining speed
 * - Pickaxe damage scales up to 4x based upon drunkness
 * - Shields cannot be knocked down
 * - has 50% resistance to alcohal.
 * - Has weakness and slowness is not at all drunk
 * - Spawns on death with 20% drunkness.
 * - 0.6x height
 * - Immune to nausea
 */
public class Stoneborn extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.STONEBORN);
    public Stoneborn(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.STONEBORN, state, customDataString);
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

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
