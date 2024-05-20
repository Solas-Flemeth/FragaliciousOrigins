package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability - Phase - Turns into a bat and can fly in creative. Drains blood while playing. Cannot attack
 * - Direct Sunlight is fatal and will instantly set you to half heart and quickly drain blood
 * - if health hits 1/2 heart, blood will drain quickly per hit.
 * - Vampire will deal increased damage and attack quicker when at low blood.
 * - Vampires may only get blood from human prey. This means by killing villagers or players.
 *   - Small chance to get blood on hit in pvp.
 * - Vampires blood gauge will fade slowly over time. This is a passive thing over time unless you run out of health.
 * - Vampires burn until extinguished with water. Every second on fire will drain % of your blood gauge
 * - Vampires have rapid regeneration.
 * - Blood Gauge
 *      - Killing a villager grants 20% blood. Killing a player grants 33% blood.
 *      - 1/2 heart = 5% blood
 *      - 1 second = 0.033% = 0.00033 blood drain
 *      - 0.25% a second while in bat form
 *      - You die when you hit 0% blood
 *
 */
public class Vampire extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.VAMPIRE);
    public Vampire(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.VAMPIRE, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(long tickNum) {

    }

    @Override
    public void originParticle(long tickNum) {

    }

    @Override
    public String serializeCustomData() {
        return "";
    }

    @Override
    public void deserializeCustomData(String customData) {

    }

    @Override
    public OriginDifficulty getDifficulty() {
        return OriginDifficulty.EXTREME;
    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
