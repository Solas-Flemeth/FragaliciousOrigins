package org.originsreborn.fragaliciousorigins.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ReloadPluginEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    public ReloadPluginEvent(){

    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}
