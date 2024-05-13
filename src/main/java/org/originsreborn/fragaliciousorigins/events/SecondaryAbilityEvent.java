package org.originsreborn.fragaliciousorigins.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SecondaryAbilityEvent extends Event implements Cancellable {
    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
