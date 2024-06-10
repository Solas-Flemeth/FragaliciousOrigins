package org.originsreborn.fragaliciousorigins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

public class SecondaryAbilityCommand implements CommandExecutor {
    /**
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId()).secondaryAbility();
            return true;
        }
        sender.sendMessage("This command can only be used by a player");
        return false;
    }
}
    