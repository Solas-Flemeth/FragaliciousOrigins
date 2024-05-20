package org.originsreborn.fragaliciousorigins.util.enums;

import org.bukkit.World;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

public enum MoonCycle {
    FULL_MOON,
    WANING_GIBBOUS,
    LAST_QUARTER,
    WANING_CRESCENT,
    NEW_MOON,
    WAXING_CRESCENT,
    FIRST_QUARTER,
    WAXING_GIBBOUS;

    public static  MoonCycle getMoonCycle(World world){
        int currentPhase = (int)  ((world.getFullTime() / 24000.0) / 8.0);
        return switch (currentPhase) {
            case 0 -> MoonCycle.FULL_MOON;
            case 2 -> MoonCycle.WANING_GIBBOUS;
            case 3 -> MoonCycle.LAST_QUARTER;
            case 4 -> MoonCycle.WANING_CRESCENT;
            case 5 -> MoonCycle.NEW_MOON;
            case 6 -> MoonCycle.WAXING_CRESCENT;
            case 7 -> MoonCycle.FIRST_QUARTER;
            default -> MoonCycle.WAXING_GIBBOUS;
        };
    }
}
