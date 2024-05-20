package org.originsreborn.fragaliciousorigins.origins.wip;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

/**
 * Primary Ability - Buff Bonded - The bonded origin gains a buff based upon their origin, and you gain one similiar
 * Secondary Ability - Buff Nearby - The Soulbonder gains 1 absorption per each nearby player. Every nearby player are given temporary buffs
 * Soulbonder gains debuffs or buffs based upon how close they are to their attached person.
 * Bonded Player gains natural buffs based upon how close they are to their attached person. Does not gain debuffs
 * Potential Buffs:
 *  - Shared Wound - Fighting nearby has a chance to give damage to the healthier player
 *  - Death Blow - A killing blow may get cancelled and regenerate the player some if their soulmate has atleast 4 hearts. The soulmate will lose 2 hearts.
 *  - Bonus XP
 *  - Blazeborn - Soulbonders dealing damage gives them heat.  Chance to ignite players longer than normal when fighting with fire.
 *  - Vampires - If a vampire runs out of health, a soulbonder may lose 5 hearts to gain back some blood and health. A soulbonder gets chance to regenerate health when attacking
 *  - Arachnid - poison effects are amplified. Bonder is immune to poison and can sometimes implement poison
 *  - Bee - Increase fatal sting chance to 5% and a 20% chance to only drop to half health instead. Bonder has a 5% chance to deal 1.5x damage on hit
 *  - Alchemist
 * Potential Debuffs (Soulbonder only)
 * - Less Health
 * - Weakness
 * - take Increase damage
 * - Can bond with /bond <playername>. The player will have to type the same for it to work. You can change bond every 120 minutes of gameplay
 */
public class Soulbonder extends Origin {
    public Soulbonder(UUID uuid, OriginType type, OriginState state, String customDataString) {
        super(uuid, type, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        return null;
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
        return OriginDifficulty.MEDIUM;
    }
}
