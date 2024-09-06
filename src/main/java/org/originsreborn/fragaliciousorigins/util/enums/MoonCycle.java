package org.originsreborn.fragaliciousorigins.util.enums;

import org.bukkit.World;

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
    public static double getPercentageTillFull(MoonCycle moonCycle) {
        return switch (moonCycle) {
            case MoonCycle.FULL_MOON -> 1.0;
            case MoonCycle.WANING_GIBBOUS, MoonCycle.WAXING_GIBBOUS -> 0.75;
            case MoonCycle.LAST_QUARTER, MoonCycle.FIRST_QUARTER -> 0.5;
            case MoonCycle.WANING_CRESCENT, MoonCycle.WAXING_CRESCENT -> 0.25;
            case MoonCycle.NEW_MOON -> 0.0;
        };
    }
}
