package org.originsreborn.fragaliciousorigins.intergration.disguiselib;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.LibsDisguises;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

public class DisguiseUtil {
    private static boolean isEnabled;
    private static  LibsDisguises libsDisguises;
    public DisguiseUtil(){
        Plugin plugin = FragaliciousOrigins.INSTANCE.getServer().getPluginManager().getPlugin("LibsDisguises");
        isEnabled = (plugin != null && plugin.isEnabled());
        if(isEnabled){
            libsDisguises = LibsDisguises.getInstance();
            isEnabled = libsDisguises.isEnabled();
        }

    }
    public static boolean isPlayerDisguised(Player player){
        if(isEnabled){
            return DisguiseAPI.isDisguised(player);
        }
        return false;
    }
    public static void disguisePlayer(Player player, PremadeDisguise disguise){
        if(isEnabled){
            switch (disguise){
                case WEREWOLF:
                    MobDisguise wolf = new MobDisguise(DisguiseType.WOLF);
                    LivingWatcher wolfWatcher = wolf.getWatcher();
                    wolfWatcher.setScale(3.0);
                    DisguiseAPI.disguiseEntity(player, wolf);
                    break;
            }
        }
    }
    public static void undisguisedPlayer(Player player){
        if(isEnabled){
            DisguiseAPI.undisguiseToAll(player);
        }
    }
}
