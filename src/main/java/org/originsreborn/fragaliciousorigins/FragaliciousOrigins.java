package org.originsreborn.fragaliciousorigins;

import org.bukkit.plugin.java.JavaPlugin;
import org.originsreborn.fragaliciousorigins.abilities.AbilityListener;
import org.originsreborn.fragaliciousorigins.commands.OriginReloadCommand;
import org.originsreborn.fragaliciousorigins.commands.OriginSetCommand;
import org.originsreborn.fragaliciousorigins.jdbc.DataSourceManager;
import org.originsreborn.fragaliciousorigins.origins.OriginManager;
import org.originsreborn.fragaliciousorigins.origins.huntsman.Huntsman;
import org.originsreborn.fragaliciousorigins.origins.shulk.Shulk;
import org.originsreborn.fragaliciousorigins.origins.wip.*;
import org.originsreborn.fragaliciousorigins.configs.MainConfig;

public final class FragaliciousOrigins extends JavaPlugin {
    public static FragaliciousOrigins INSTANCE;
    public static OriginManager ORIGINS;
    public static MainConfig CONFIG;
    public static DataSourceManager DATASOURCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        CONFIG = new MainConfig(); //Register as a valid config file
        ORIGINS = new OriginManager();
        DATASOURCE = new DataSourceManager();
        this.getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        registerCommands();
        reload();
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reload() {
        CONFIG.loadConfig();
        Alchemist.onReload();
        Arachnid.onReload();
        Bee.onReload();
        Blazeborn.onReload();
        Elytrian.onReload();
        Enderian.onReload();
        Feline.onReload();
        Giant.onReload();
        Huntsman.onReload();
        Inchling.onReload();
        Merling.onReload();
        Phantom.onReload();
        Phytokin.onReload();
        ShapeShifter.onReload();
        Shulk.onReload();
        Stoneborn.onReload();
        Vampire.onReload();
        //DO LAST - this reloads all origin stats and applies to current playerbase
        ORIGINS.reloadOrigins();
    }
    private void registerCommands() {
        this.getCommand("originreload").setExecutor(new OriginReloadCommand());
        this.getCommand("originset").setExecutor(new OriginSetCommand());
    }

}
