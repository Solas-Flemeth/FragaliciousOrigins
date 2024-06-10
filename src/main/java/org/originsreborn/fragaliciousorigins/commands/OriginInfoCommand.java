package org.originsreborn.fragaliciousorigins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.Origin;

public class OriginInfoCommand implements CommandExecutor {

    /**
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId());
            if(origin != null){
                player.performCommand("cp " + origin.getType().name().toLowerCase());
                return true;
            }
            commandSender.sendMessage("You do not have an origin. Try logging or report this as a bug");
        }else{
            commandSender.sendMessage("This command can only be performed by a player");
        }
        return true;
    }
}
