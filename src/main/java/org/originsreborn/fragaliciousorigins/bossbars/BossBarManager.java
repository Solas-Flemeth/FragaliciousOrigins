package org.originsreborn.fragaliciousorigins.bossbars;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import org.bukkit.entity.Player;

public class BossBarManager {
    private final Map<String, BossBar> bossBars;

    public BossBarManager() {
        this.bossBars = new HashMap<>();
    }

    /**
     * Registers a new boss bar
     * @param key
     * @param title
     * @param progress
     * @param color
     * @param overlay
     */
    public void createBossBar(String key, Component title, float progress, Color color, Overlay overlay, Player player) {
        BossBar bossBar = BossBar.bossBar(title, progress, color, overlay);
        bossBar.addViewer(player);
        bossBars.put(key, bossBar);
    }

    /**
     * Updates a bossbar's title and progress
     * @param key
     * @param title
     * @param progress
     */
    public void updateBossBar(String key, Component title, float progress) {
        BossBar bossBar = bossBars.get(key);
        if (bossBar != null) {
            bossBar.name(title);
            bossBar.progress(progress);
        }
    }

    /**
     * Updates a boss bar's title, progress, and color
     * @param key
     * @param title
     * @param progress
     * @param color
     */
    public void updateBossBar(String key, Component title, float progress, Color color) {
        BossBar bossBar = bossBars.get(key);
        if (bossBar != null) {
            bossBar.name(title);
            bossBar.progress(progress);
            bossBar.color(color);
        }
    }

    /**
     * Removes a boss bar from the map
     * @param key
     */
    public void removeBossBar(String key) {
        if(containsBossBar(key)){
            BossBar bar = bossBars.get(key); // Retrieve the BossBar associated with the given key.
            Iterable<? extends BossBarViewer> viewerIterable = bar.viewers(); // Get an iterable collection of viewers of the BossBar.
            for (BossBarViewer viewer : viewerIterable) {
                try{
                    bar.removeViewer((Audience) viewer); // Remove the current viewer's player from the BossBar.
                }catch (NullPointerException ignored){} //player logout

            }
            bossBars.remove(key); // Remove the BossBar entry from the map using the provided key.
        }
    }

    /**
     * Returns the boss bar for said key
     * @param key
     * @return
     */
    public BossBar getBossBar(String key) {
        return bossBars.get(key);
    }

    /**
     * Returns whether a boss bar exist for the key
     * @param key
     * @return
     */
    public boolean containsBossBar(String key) {
        return bossBars.containsKey(key);
    }

    /**
     * Returns all Boss Bars in a Map
     * @return
     */
    public Map<String, BossBar> getAllBossBars() {
        return bossBars;
    }
}

