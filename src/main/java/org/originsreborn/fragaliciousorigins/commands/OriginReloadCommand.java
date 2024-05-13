package org.originsreborn.fragaliciousorigins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

public class OriginReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        FragaliciousOrigins.INSTANCE.reload();
        commandSender.sendMessage("Reloading Origins Plugin");
        return true;
    }

}
