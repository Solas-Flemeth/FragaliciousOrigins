package org.originsreborn.fragaliciousorigins.origins;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.Nullable;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.jdbc.OriginsDAO;
import org.originsreborn.fragaliciousorigins.jdbc.SerializedOrigin;
import org.originsreborn.fragaliciousorigins.origins.phantom.Phantom;
import org.originsreborn.fragaliciousorigins.util.enums.DayCycle;
import org.originsreborn.fragaliciousorigins.util.enums.MoonCycle;

import java.util.*;

public class OriginManager {
    private static final int MAX_TICK = 864000; //1 day of ticks if alternating ticks
    private final HashMap<UUID, Origin> originsMap;
    private int tick = 0;
    private final World world = FragaliciousOrigins.INSTANCE.getServer().getWorlds().getFirst();
    private DayCycle dayCycle = DayCycle.getCurrentTime(world);
    public OriginManager() {
        originsMap = new HashMap<UUID, Origin>();
        BukkitScheduler scheduler = FragaliciousOrigins.INSTANCE.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(FragaliciousOrigins.INSTANCE, () -> {
            //Tick origins logic every 2 ticks
            tickOrigins(tick);
            tick++;
            if (tick > MAX_TICK) {
                tick = 0;
            }
        }, 2, 2);
    }

    /**
     * Checks to see if a player is in the origin mapper.
     *
     * @param uuid
     * @return
     */
    public boolean containsUUID(UUID uuid) {
        return originsMap.containsKey(uuid);
    }

    /**
     * Returns the players origin
     *
     * @param uuid
     * @return
     */
    @Nullable
    public Origin getOrigin(UUID uuid) {
        return originsMap.get(uuid);
    }

    /**
     * Adds or update a players origin
     *
     * @param origin
     */
    public void updateOrigin(Origin origin) {
        if (containsUUID(origin.getUUID())) {
            getOrigin(origin.getUUID()).onRemoveOrigin();
        }
        origin.setDefaultStats(); //force override to make sure stats reset
        originsMap.put(origin.getUUID(), origin);
        origin.updateStats();
        FragaliciousOrigins.INSTANCE.getLogger().fine("SETTING PLAYER TO " + origin.getPlayer().getName() + " TO ORIGIN TYPE OF " + origin.getType().getDisplay());
        OriginsDAO.saveOrigin(new SerializedOrigin(origin));
    }

    /**
     * Removes an origin from the system.
     * Returns the Origin that was removed
     *
     * @param uuid
     * @return
     */
    @Nullable
    public Origin removeOrigin(UUID uuid) {
        if (containsUUID(uuid)) {
            getOrigin(uuid).onRemoveOrigin();
            return originsMap.remove(uuid);
        }
        return null;
    }

    /**
     * Gets a list of all the origins
     *
     * @return
     */
    public List<Origin> getOrigins() {
        return new ArrayList<Origin>(originsMap.values());
    }

    /**
     * Reapplies accurate stats to all origins from a plugin reload
     */
    public void reloadOrigins() {
        FragaliciousOrigins.INSTANCE.getLogger().fine("Reloading Origins. There may be a small amount of lag");
        for (Origin origin : getOrigins()) {
            origin.setDefaultStats();
            origin.updateStats();
        }
    }

    /**
     * Tick Origin Logic
     *
     * @param tick
     */
    public void tickOrigins(int tick) {
        boolean changeTime = false;
        MoonCycle moonCycle = MoonCycle.getMoonCycle(world);
        if(tick%100 == 0){
            DayCycle tempDayCycle = DayCycle.getCurrentTime(world);
            if(tempDayCycle != dayCycle){
                dayCycle = tempDayCycle;
                changeTime = true;
            }
        }
        if (originsMap.isEmpty()) {
            return;
        }
        boolean isCoolDownTick = tick % 10 == 0;
        List<Origin> outdatedTempOrigins = new ArrayList<>();
        Iterator<Origin> iterator = originsMap.values().iterator();
        while (iterator.hasNext()) {
            Origin origin = iterator.next();
            if (origin.getPlayer() != null) {
                if (isCoolDownTick) {
                    origin.cooldownTick();
                    switch (origin.getState()) {
                        case EVENT:
                        case TEMPORARY:
                        case SHAPESHIFTER:
                            int timeRemaining = origin.getTempTimeRemaining();
                            if (timeRemaining == 0) {
                                iterator.remove();
                                outdatedTempOrigins.add(origin);
                                continue;
                            } else {
                                origin.setTempTimeRemaining(timeRemaining - 1);
                            }
                            break;
                    }
                }
                origin.originTick(tick);
                if(changeTime){ //change time of day
                    origin.onTimeChange(dayCycle, moonCycle);
                }
                if (origin.getPlayer().getGameMode().equals(GameMode.SURVIVAL)){
                    if(!(origin.getPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY))) {
                        origin.originParticle(tick);
                    }
                    origin.applyExhaustion();
                } else if (origin instanceof Phantom) {
                    origin.originParticle(tick);
                }
            } else {
                //if the player is no longer on, remove them
                iterator.remove();
                removeOrigin(origin.getUUID());
            }
        }
        if (!outdatedTempOrigins.isEmpty()) {
            FragaliciousOrigins.INSTANCE.getServer().getScheduler().runTaskLaterAsynchronously(FragaliciousOrigins.INSTANCE, () -> {
                for (Origin origin : outdatedTempOrigins) {
                    OriginsDAO.deleteOrigin(new SerializedOrigin(origin));
                    Origin updatedOrigin = OriginsDAO.getOrigin(origin.getUUID());
                    updateOrigin(updatedOrigin);
                }
            }, 0);
        }
    }
}
