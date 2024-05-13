package org.originsreborn.fragaliciousorigins.origins;

import org.jetbrains.annotations.Nullable;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.jdbc.OriginsDAO;
import org.originsreborn.fragaliciousorigins.jdbc.SerializedOrigin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OriginManager {
    private final HashMap<UUID, Origin> originsMap;

    public OriginManager() {
        originsMap = new HashMap<UUID, Origin>();
    }

    /**
     * Checks to see if a player is in the origin mapper.
     * @param uuid
     * @return
     */
    public boolean containsUUID(UUID uuid) {
        return originsMap.containsKey(uuid);
    }

    /**
     * Returns the players origin
     * @param uuid
     * @return
     */
    @Nullable
    public Origin getOrigin(UUID uuid) {
        return originsMap.get(uuid);
    }

    /**
     * Adds or update a players origin
     * @param origin
     */
    public void updateOrigin(Origin origin) {
        originsMap.put(origin.getUUID() , origin);
        origin.updateStats();
        FragaliciousOrigins.INSTANCE.getLogger().fine("SETTING PLAYER TO " + origin.getPlayer().getName() +  " TO ORIGIN TYPE OF " + origin.getType().getDisplay());
        OriginsDAO.saveOrigin(new SerializedOrigin(origin));
    }

    /**
     * Removes an origin from the system.
     * Returns the Origin that was removed
     * @param uuid
     * @return
     */
    @Nullable
    public Origin removeOrigin(UUID uuid) {
        if (containsUUID(uuid)) {
            return originsMap.remove(uuid);
        }
        return null;
    }

    /**
     * Gets a list of all the origins
     * @return
     */
    public List<Origin> getOrigins(){
        return new ArrayList<Origin>(originsMap.values());
    }

    /**
     * Reapplies accurate stats to all origins from a plugin reload
     */
    public void reloadOrigins(){
        FragaliciousOrigins.INSTANCE.getLogger().fine("Reloading Origins. There may be a small amount of lag");
        for (Origin origin : getOrigins()){
            origin.updateStats();
        }
    }
}
