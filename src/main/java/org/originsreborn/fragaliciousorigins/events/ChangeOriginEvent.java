package org.originsreborn.fragaliciousorigins.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.originsreborn.fragaliciousorigins.origins.Origin;

public class ChangeOriginEvent extends OriginEvent {
    private final ChangeReason changeReason;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public ChangeOriginEvent(Origin origin, Player player, ChangeReason changeReason) {
        super(origin, player);
        this.changeReason = changeReason;
    }

    public ChangeReason getChangeReason(){
        return changeReason;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}