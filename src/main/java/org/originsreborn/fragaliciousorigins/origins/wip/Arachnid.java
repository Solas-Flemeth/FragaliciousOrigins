package org.originsreborn.fragaliciousorigins.origins.wip;

import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Primary - Spider web
 *  - Beams to a wall
 *  - Webs target if hit
 * Secondary - Toggle Climb
 * - Nightvision
 * - Moon cycle - chance to receive a random positive potion effect closer to full moon
 * - 1.5x longer combat reach
 * - 3x longer build reach
 * - Bows can spawn webs on arrow land (Chance %)
 * - Chance to spawn web and poison target on hit
 * - Lower HP
 * - Meat Only
 * - lower fall damage
 * - Immune to sharpness - bonus damage from bane of arthapods
 */

public class Arachnid extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.ARACHNID);
    public Arachnid(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.ARACHNID, state, customDataString);
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

    /**
     * @param map
     * @return
     */
    @Override
    public @NotNull HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        return map;
    }

    /**
     * @param map
     * @throws Exception
     */
    @Override
    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {

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
