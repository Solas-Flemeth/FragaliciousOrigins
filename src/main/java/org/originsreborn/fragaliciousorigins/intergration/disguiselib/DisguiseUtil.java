package org.originsreborn.fragaliciousorigins.intergration.disguiselib;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.LibsDisguises;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.entity.Player;

public class DisguiseUtil {
    private final LibsDisguises libsDisguises;

    public DisguiseUtil() {
        libsDisguises = LibsDisguises.getInstance();
    }

    public boolean isPlayerDisguised(Player player) {
        return DisguiseAPI.isDisguised(player);
    }

    public void disguisePlayer(Player player, PremadeDisguise disguise) {
        switch (disguise) {
            case WEREWOLF:
                MobDisguise wolf = new MobDisguise(DisguiseType.WOLF);
                LivingWatcher wolfWatcher = wolf.getWatcher();
                wolfWatcher.setScale(3.0);
                DisguiseAPI.disguiseEntity(player, wolf);
                break;
        }
    }

    public void undisguisedPlayer(Player player) {
        DisguiseAPI.undisguiseToAll(player);
    }
}
