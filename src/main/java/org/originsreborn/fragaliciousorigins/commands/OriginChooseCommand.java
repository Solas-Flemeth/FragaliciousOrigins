package org.originsreborn.fragaliciousorigins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.List;

public class OriginChooseCommand implements TabExecutor {

    /**
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId());
            if (origin.getType().equals(OriginType.HUMAN) && origin.getState().equals(OriginState.NORMAL)) {
                if (args.length == 0) {
                    return false;
                }
                OriginType type = OriginType.getByDisplayName(args[0]);
                if (type.equals(OriginType.HUMAN)) {
                    sender.sendMessage("Unknown Origin");
                    return true;
                }
                FragaliciousOrigins.ORIGINS.updateOrigin(type.generateOrigin(player.getUniqueId(), OriginState.NORMAL, ""));
                return true;
            }
            sender.sendMessage("Your origin must be Human in a state of Normal");
            return true;
        }
        sender.sendMessage("This command can only be used by a Human");
        return true;
    }

    /**
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //return OriginType.getTypesStrings();
        return null;
    }
}
