package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability - Boost - flies off the ground or boost natural speed
 * Secondary Ability - Toggle Wings - turns wings on and off (Will delete current chestplate)
 * - Slowness under low roofs
 * - slower attack speed on ground
 * - increase impact damage
 * - has unbreakable elytra (removes on origin change)
 * - reduce fall damage (20%?)
 * - Major increase to attack range, build range, attack speed and attack damage while gliding
 * - Can catch air drifts while flying
 * - chance to dodge attacks while gliding (20%)
 * - Lower gravity (80%)
 */
public class Elytrian extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ELYTRIAN);

    public Elytrian(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ELYTRIAN, state, customDataString);
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
        return OriginDifficulty.MEDIUM;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
