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
        for (String permission : permissions){
            registerPermissions(player,permission);
        }
    }

    /**
     * Registers a permissions to the player. This will last for the existing instance.
     * @param player
     * @param permission
     */
    public static void registerPermissions(Player player, String permission){
        if (!player.hasPermission(permission)) {
            // Get or create the player's PermissionAttachment
            PermissionAttachment attachment = player.addAttachment(FragaliciousOrigins.INSTANCE);
            // Grant the permission
            attachment.setPermission(permission, true);
            // Save the changes
            player.recalculatePermissions();
        }
    }

    /**
     * Removes all permissions from the plugin.
     * @param player
     */
    public static void resetPermissions(Player player) {
        player.removeAttachment(player.addAttachment(FragaliciousOrigins.INSTANCE));
        player.recalculatePermissions();
    }
    //TODO: Register luckperms checks too
}
