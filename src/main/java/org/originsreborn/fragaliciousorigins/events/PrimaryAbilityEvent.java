package org.originsreborn.fragaliciousorigins.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.origins.Origin;

public class PrimaryAbilityEvent extends OriginEvent {
    public PrimaryAbilityEvent(Origin origin, Player player) {
        super(origin, player);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}
