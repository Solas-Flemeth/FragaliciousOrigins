package org.originsreborn.fragaliciousorigins.intergration.anomaly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        registerOriginAnomalies();
        registerImmunityAnomalies();
    }
    @EventHandler
    public void onJoin(PlayerJoinAnomalyEvent event){
        if(event.getAnomaly().getName().toLowerCase().equals("reset origin")){
            FragaliciousOrigins.ORIGINS.updateOrigin(OriginType.HUMAN.generateOrigin(event.getPlayer(), OriginState.NORMAL, ""));
        }
        OriginType type = OriginType.getByDisplayName(event.getAnomaly().getName());
        if(type != OriginType.HUMAN){
            Origin origin = type.generateOrigin(event.getPlayer(), OriginState.EVENT, "");
            origin.setTempTimeRemaining(((int) FragaliciousAnomaly.dayService().timeTillNextDay()/20)+1);
            FragaliciousOrigins.ORIGINS.updateOrigin(origin);
        }
    }
    private void registerOriginAnomalies(){
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
                FragaliciousAnomaly.anomalyAPI().registerAnomaly(anomaly);
            }else if(originType.equals("VAMPIRE") || originType.equals("SHAPESHIFTER")) {
                //do nothing
            }else{
                anomaly = new Anomaly(
                        FragaliciousOrigins.INSTANCE,
                        originType.toLowerCase(),
                        Rarity.UNUSUAL,
                        true,
                        Component.text("Become an " + originType.toLowerCase() + " temporarily").color(TextColor.color(0x9104BF)),
                        "Become a " + originType.toLowerCase() + "temporarily"
                );
                FragaliciousAnomaly.anomalyAPI().registerAnomaly(anomaly);
            }
        }
    }
    private void registerImmunityAnomalies() {
        Anomaly enderianAnomaly = new Anomaly(
                FragaliciousOrigins.INSTANCE,
                "Enderian Empowerment",
                Rarity.COMMON, // Assuming a rarity level, adjust as needed
                true, // Assuming this anomaly is active
                Component.text("Enderians are immune to water").color(TextColor.color(0x00FF00)), // Example description
                "Provides immunity to water for Enderians"
        );
        FragaliciousAnomaly.anomalyAPI().registerAnomaly(enderianAnomaly);

        Anomaly merlingAnomaly = new Anomaly(
                FragaliciousOrigins.INSTANCE,
                "Merling Empowerment",
                Rarity.COMMON, // Assuming a rarity level, adjust as needed
                true, // Assuming this anomaly is active
                Component.text("Merlings do not dehydrate").color(TextColor.color(0x00FF00)), // Example description
                "Merlings do not dehydrate"
        );
        FragaliciousAnomaly.anomalyAPI().registerAnomaly(merlingAnomaly);

        Anomaly phantomAnomaly = new Anomaly(
                FragaliciousOrigins.INSTANCE,
                "Phantom Empowerment",
                Rarity.COMMON, // Assuming a rarity level, adjust as needed
                true, // Assuming this anomaly is active
                Component.text("Phantoms do not die from sunlight").color(TextColor.color(0x00FF00)), // Example description
                "Phantoms do not die from sunlight"
        );
        FragaliciousAnomaly.anomalyAPI().registerAnomaly(phantomAnomaly);

        Anomaly fairyAnomaly = new Anomaly(
                FragaliciousOrigins.INSTANCE,
                "Fairy Empowerment",
                Rarity.COMMON, // Assuming a rarity level, adjust as needed
                true, // Assuming this anomaly is active
                Component.text("Fairies do not get exhausted from flying").color(TextColor.color(0x00FF00)), // Example description
                "Fairies do not get exhausted from flying"
        );
        FragaliciousAnomaly.anomalyAPI().registerAnomaly(fairyAnomaly);

        Anomaly fastAbilitiesAnomaly = new Anomaly(
                FragaliciousOrigins.INSTANCE,
                "Fast Abilities",
                Rarity.OCCASIONAL, // Assuming a rarity level, adjust as needed
                true, // Assuming this anomaly is active
                Component.text("Abilities recharge 3x faster").color(TextColor.color(0xFF3F)), // Example description
                "Abilities recharge 3x faster"
        );
        FragaliciousAnomaly.anomalyAPI().registerAnomaly(fastAbilitiesAnomaly);

    }
    public String getCurrentAnomalyName(){
        Anomaly anomaly = FragaliciousAnomaly.anomalyAPI().getCurrentAnomaly();
        if(anomaly != null) {
            return anomaly.getName();
        }else{
            return "None";
        }
    }
}
