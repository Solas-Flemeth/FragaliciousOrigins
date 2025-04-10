package org.originsreborn.fragaliciousorigins.origins;

import org.bukkit.event.player.PlayerRespawnEvent;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

public class Human extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.HUMAN);


    public Human(UUID uuid) {
        super(uuid, OriginType.HUMAN, OriginState.NORMAL);
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {

    }

    @Override
    public void originParticle(int tickNum) {

    }

    /**
     *
     */
    @Override
    public void primaryAbility() {
        if(getType().equals(OriginType.HUMAN) && getState().equals(OriginState.NORMAL)){
            getPlayer().performCommand("cpanel human");
            return;
        };
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }

    @Override
    public String serializeCustomData() {
        return "";
    }

    @Override
    public void deserializeCustomData(String customData) {

    }

    @Override
    public void primaryAbilityLogic() {

    }

    @Override
    public void secondaryAbilityLogic() {

    }

    /**
     * @param event
     */
    @Override
    public void onRespawn(PlayerRespawnEvent event) {
        super.onRespawn(event);
        if(getType().equals(OriginType.HUMAN) && getState().equals(OriginState.NORMAL)){
            getPlayer().performCommand("cpanel human");
        }
    }
}
