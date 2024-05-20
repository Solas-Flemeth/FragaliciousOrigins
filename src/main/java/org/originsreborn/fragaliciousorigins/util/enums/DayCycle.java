package org.originsreborn.fragaliciousorigins.util.enums;

import org.bukkit.World;

public enum DayCycle {
    SUNRISE(23000),
    DAY(0),
    SUNSET(12000),
    NIGHT(13000);
    private final int time;

    DayCycle(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
    public static DayCycle getCurrentTime(World world){
        long currentTick = world.getTime() % 24000;
        if(currentTick >= 23000){
            return DayCycle.SUNRISE;
        }else if( currentTick >= 13000){
            return DayCycle.NIGHT;
        }else if(currentTick >= 12000){
            return DayCycle.SUNSET;
        }else{
            return DayCycle.DAY;
        }
    }
}
