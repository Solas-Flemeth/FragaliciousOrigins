package org.originsreborn.fragaliciousorigins.commands;

import org.bukkit.Bukkit;
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

import java.util.ArrayList;
import java.util.List;

public class OriginSetCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length < 2){
            //Not long enough arguments
            return false;
        }
        //save values
        String name = strings[0];
        String type = strings[1];
        String state = "NORMAL";
        int duration = 0;
        if(strings.length >= 3){
            state = strings[2];
        }
        if(strings.length >= 4){
            try{
                duration = Math.abs(Integer.valueOf(strings[3]));
            }catch (NumberFormatException ignored){

            }
        }
        //build values
        Player player = Bukkit.getPlayer(name);
        if(player == null){
            commandSender.sendMessage("Unknown Player");
            return true; // invalid player
        }
        OriginType originType = OriginType.getByDisplayName(type);
        OriginState originState = OriginState.getState(state);
        Origin origin = originType.generateOrigin(player.getUniqueId(), originState, "");
        if(duration > 0){
            origin.setTempTimeRemaining(duration);
            commandSender.sendMessage("Creating an Origin for player " + player.getName() + " as a " + originType.getDisplay() + " with the state of " + originState.name() + "and a duration of " + duration +" seconds");
        }else{
            commandSender.sendMessage("Creating an Origin for player " + player.getName() + " as a " + originType.getDisplay() + " with the state of " + originState.name());
        }
        FragaliciousOrigins.ORIGINS.updateOrigin(origin);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (strings.length){
            case 1:
                List<Player> players = List.copyOf(FragaliciousOrigins.INSTANCE.getServer().getOnlinePlayers());
                List<String> playerNames = new ArrayList<>(players.size());
                for(Player player : players){
                    playerNames.add(player.getName());
                }
                return playerNames;
            case 2:
                return OriginType.getTypesStrings();
            case 3:
                return OriginState.getStatesStrings();
        }
        return List.of();
    }
}
