package org.originsreborn.fragaliciousorigins.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.Origin;

public class ToggleAbilityKeyCommand implements CommandExecutor {
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
            if(origin == null){
                commandSender.sendMessage(Component.text("Unable to swap key settings. Please try again later.").color(Origin.errorColor()));
                return false;
            }
            origin.toggleUseSwapHand();
            if(origin.isUseSwapHand()){
                commandSender.sendMessage(Component.text("You ability key is now used to perform your ability").color(Origin.textColor()));
            }else{
                commandSender.sendMessage(Component.text("You ability key is now used to swap hands").color(Origin.textColor()));
            }
        }else{
            commandSender.sendMessage("This command must be executed by a player");
        }
        return true;
    }
}
