package org.originsreborn.fragaliciousorigins.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.originsreborn.fragaliciousorigins.origins.Origin;

public abstract class OriginEvent extends Event implements Cancellable {
    protected boolean cancelled = false;
    protected Origin origin;
    protected Player player;
    public OriginEvent(Origin origin, Player player){
        this.origin = origin;
        this.player = player;

    }
    public Origin getOrigin() {
        return origin;
    }

    public Player getPlayer() {
        return player;
    }
    public boolean isCancelled() {
        return cancelled;
    }
    public void setCancelled(boolean b){
        cancelled = b;
    }
}
