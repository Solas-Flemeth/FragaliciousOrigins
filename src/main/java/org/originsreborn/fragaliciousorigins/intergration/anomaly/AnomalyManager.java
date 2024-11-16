package org.originsreborn.fragaliciousorigins.intergration.anomaly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.originsreborn.fragaliciousanomaly.FragaliciousAnomaly;
import org.originsreborn.fragaliciousanomaly.events.PlayerJoinAnomalyEvent;
import org.originsreborn.fragaliciousanomaly.objects.Anomaly;
import org.originsreborn.fragaliciousanomaly.objects.enums.Rarity;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

public class AnomalyManager implements Listener {
    public AnomalyManager(){
        registerAnomalies();
    }
    public void registerAnomalies(){
        for(String originType : OriginType.getTypesStrings()){
            Anomaly anomaly;
            if(originType.equals("HUMAN")){
                anomaly = new Anomaly(
                        FragaliciousOrigins.INSTANCE,
                        "Reset Origin",
                        Rarity.RARE,
                        true,
                        Component.text("Reset your origin free").color(TextColor.color(0x9104BF)),
                        "Reset your origin for free"
                );
            }else{
                anomaly = new Anomaly(
                        FragaliciousOrigins.INSTANCE,
                        originType,
                        Rarity.COMMON,
                        true,
                        Component.text("Become an " + originType + " temporarily").color(TextColor.color(0x9104BF)),
                        "Become a " + originType + "temporarily"
                );
            }
            FragaliciousAnomaly.anomalyAPI().registerAnomaly(anomaly);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinAnomalyEvent event){
        if(event.getAnomaly().getName().equals("Reset Origin")){
            FragaliciousOrigins.ORIGINS.updateOrigin(OriginType.HUMAN.generateOrigin(event.getPlayer(), OriginState.NORMAL, ""));
        }
        OriginType type = OriginType.getByDisplayName(event.getAnomaly().getName());
        if(type != OriginType.HUMAN){
            Origin origin = type.generateOrigin(event.getPlayer(), OriginState.EVENT, "");
            origin.setTempTimeRemaining((int) FragaliciousAnomaly.dayService().timeTillNextDay());
            FragaliciousOrigins.ORIGINS.updateOrigin(origin);
        }
    }

}
