package org.originsreborn.fragaliciousorigins.intergration.anomaly;


import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;

public class AnomalyHook implements Listener {
    private AnomalyManager anomalyManager;
    public AnomalyHook(){
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("FragaliciousAnomaly")){
            FragaliciousOrigins.INSTANCE.getLogger().warning("Anomaly Found, Starting Anomaly Module");
            anomalyManager = new AnomalyManager();
            Bukkit.getServer().getPluginManager().registerEvents(anomalyManager, FragaliciousOrigins.INSTANCE);
        }else{
            FragaliciousOrigins.INSTANCE.getLogger().warning("Anomaly Not Found, Skipping Anomaly Support");
        }
    }

    /**
     *
     * @return Anomaly Managert if anomaly is enabled
     */
    public AnomalyManager getAnomalyManager() {
        return anomalyManager;
    }
}
