package org.originsreborn.fragaliciousorigins.intergration.disguiselib;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

public class DisguiseLibHook {
    private static boolean isEnabled = false;
    private static DisguiseUtil disguiseUtil;
    public DisguiseLibHook(){
        Plugin plugin = FragaliciousOrigins.INSTANCE.getServer().getPluginManager().getPlugin("LibsDisguises");
        isEnabled = (plugin != null && plugin.isEnabled());
        if(isEnabled){
            disguiseUtil = new DisguiseUtil();
        }
    }
    public static void undisguisedPlayer(Player player){
        if(isEnabled) {
            disguiseUtil.undisguisedPlayer(player);
        }
    }
    public static void disguisePlayer(Player player, PremadeDisguise disguise) {
            if(isEnabled){
                disguiseUtil.disguisePlayer(player, disguise);
            }
    }
    public static boolean isPlayerDisguised(Player player){
        if(isEnabled){
            return disguiseUtil.isPlayerDisguised(player);
        }
        return false;

    }
}
