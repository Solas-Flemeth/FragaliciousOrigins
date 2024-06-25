package org.originsreborn.fragaliciousorigins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.originsreborn.fragaliciousorigins.abilities.AbilityListener;
import org.originsreborn.fragaliciousorigins.bossbars.BossBarManager;
import org.originsreborn.fragaliciousorigins.commands.*;
import org.originsreborn.fragaliciousorigins.intergration.OriginPlaceholders;
import org.originsreborn.fragaliciousorigins.jdbc.DataSourceManager;
import org.originsreborn.fragaliciousorigins.origins.OriginManager;
import org.originsreborn.fragaliciousorigins.origins.alchemist.Alchemist;
import org.originsreborn.fragaliciousorigins.origins.bee.Bee;
import org.originsreborn.fragaliciousorigins.origins.elytrian.Elytrian;
import org.originsreborn.fragaliciousorigins.origins.feline.Feline;
import org.originsreborn.fragaliciousorigins.origins.phantom.Phantom;
import org.originsreborn.fragaliciousorigins.origins.wip.Enderian;
import org.originsreborn.fragaliciousorigins.origins.giant.Giant;
import org.originsreborn.fragaliciousorigins.origins.huntsman.Huntsman;
import org.originsreborn.fragaliciousorigins.origins.inchling.Inchling;
import org.originsreborn.fragaliciousorigins.origins.merling.Merling;
import org.originsreborn.fragaliciousorigins.origins.shulk.Shulk;
import org.originsreborn.fragaliciousorigins.origins.wip.*;
import org.originsreborn.fragaliciousorigins.configs.MainConfig;
import org.originsreborn.fragaliciousorigins.origins.wip.postrelease.ShapeShifter;
import org.originsreborn.fragaliciousorigins.origins.wip.postrelease.Stoneborn;
import org.originsreborn.fragaliciousorigins.origins.wip.postrelease.Vampire;

public final class FragaliciousOrigins extends JavaPlugin {
    public static FragaliciousOrigins INSTANCE;
    public static OriginManager ORIGINS;
    public static BossBarManager BOSS_BARS;
    public static MainConfig CONFIG;
    public static DataSourceManager DATASOURCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        CONFIG = new MainConfig();//Register as a valid config file
        BOSS_BARS = new BossBarManager();
        ORIGINS = new OriginManager();
        DATASOURCE = new DataSourceManager();

        this.getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        registerCommands();
        registerPlaceholderAPI();
        reload();
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reload() {
        try {
            CONFIG.loadConfig();
            Alchemist.onReload();
            Arachnid.onReload();
            Blazeborn.Bee.onReload();
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
            Bee.onReload();
        }catch (Exception e){

        }
        //DO LAST - this reloads
        //all origin stats and applies to current playerbase
        ORIGINS.reloadOrigins();
    }
    private void registerCommands() {
        this.getCommand("originreload").setExecutor(new OriginReloadCommand());
        this.getCommand("originset").setExecutor(new OriginSetCommand());
        this.getCommand("primaryability").setExecutor(new PrimaryAbilityCommand());
        this.getCommand("secondaryability").setExecutor(new SecondaryAbilityCommand());
        this.getCommand("human").setExecutor(new HumanCommand());
        this.getCommand("originchoose").setExecutor(new OriginChooseCommand());
        this.getCommand("origininfo").setExecutor(new OriginInfoCommand());
    }
    private void registerPlaceholderAPI(){
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new OriginPlaceholders().register();
        }
    }

}
