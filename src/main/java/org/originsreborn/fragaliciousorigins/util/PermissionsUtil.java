package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

import java.util.List;

public class PermissionsUtil {
    /**
     * Registers a list of permissions to the player. This will last for the existing instance.
     * @param player
     * @param permissions
     */
    public static void registerPermission(Player player, List<String> permissions){
        try{
            for (String permission : permissions){
                registerPermissions(player,permission);
            }
        }catch (Exception ignored){

        }
    }

    /**
     * Registers a permissions to the player. This will last for the existing instance.
     * @param player
     * @param permission
     */
    public static void registerPermissions(Player player, String permission){
        try{
            if (!player.hasPermission(permission)) {
                // Get or create the player's PermissionAttachment
                PermissionAttachment attachment = player.addAttachment(FragaliciousOrigins.INSTANCE);
                // Grant the permission
                attachment.setPermission(permission, true);
                // Save the changes
                player.recalculatePermissions();
            }
        }catch (Exception ignored){}
    }

    /**
     * Removes all permissions from the plugin.
     * @param player
     */
    public static void resetPermissions(Player player) {
        try{
        player.removeAttachment(player.addAttachment(FragaliciousOrigins.INSTANCE));
        player.recalculatePermissions();
        }catch (Exception ignored){}
    }
    //TODO: Register luckperms checks too
}
