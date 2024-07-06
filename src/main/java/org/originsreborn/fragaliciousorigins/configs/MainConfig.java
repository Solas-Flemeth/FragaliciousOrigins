package org.originsreborn.fragaliciousorigins.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;


public class MainConfig {
    private final FileConfiguration mainConfig;
    //JDBC
    private static String JDBC_URL, USERNAME, PASSWORD, TABLE_PREFIX;
    private static Integer SAVE_INTERVAL;
    private static String DIFFICULTY_EASY_ICON;
    private static String DIFFICULTY_MEDIUM_ICON;
    private static String DIFFICULTY_HARD_ICON;
    private static String DIFFICULTY_EXTREME_ICON;
    private static String DISCORD_GUILD;
    private static String DISCORD_CHANNEL;
    private static String BOT_TOKEN;
    public MainConfig() {
        mainConfig = FragaliciousOrigins.INSTANCE.getConfig();
        buildConfig();
        loadConfig();
    }

    /**
     * Builds the main config
     */
    private void buildConfig() {
        //JDBC
        mainConfig.addDefault("Table Prefix", "ORIGINS_");
        mainConfig.addDefault("JDBC URL", "jdbcURL");
        mainConfig.addDefault("Username", "user");
        mainConfig.addDefault("Password", "password");
        mainConfig.addDefault("Save Interval", 600);
        mainConfig.addDefault("Difficulty Easy Icon", "easy");
        mainConfig.addDefault("Difficulty Medium Icon", "medium");
        mainConfig.addDefault("Difficulty Hard Icon", "hard");
        mainConfig.addDefault("Difficulty Extreme Icon", "extreme");
        mainConfig.addDefault("Discord Guild","1158515580802969620");
        mainConfig.addDefault("Discord Channel","1169047609810370560");
        mainConfig.addDefault("Bot Token", "token");
        mainConfig.options().copyDefaults(true);
        FragaliciousOrigins.INSTANCE.saveConfig();
    }

    /**
     * Loads the variables in the config
     */
    public void loadConfig() {
        TABLE_PREFIX = mainConfig.getString("Table Prefix", "BOUNTY_");
        JDBC_URL = mainConfig.getString("JDBC URL", "jdbcURL");
        USERNAME = mainConfig.getString("Username", "user");
        PASSWORD = mainConfig.getString("Password", "password");
        SAVE_INTERVAL = mainConfig.getInt("Save Interval", 600);
        DIFFICULTY_EASY_ICON = mainConfig.getString("Difficulty Easy Icon", "easy");
        DIFFICULTY_MEDIUM_ICON = mainConfig.getString("Difficulty Medium Icon", "medium");
        DIFFICULTY_HARD_ICON = mainConfig.getString("Difficulty Hard Icon", "hard");
        DIFFICULTY_EXTREME_ICON = mainConfig.getString("Difficulty Extreme Icon", "extreme");
        DISCORD_GUILD = mainConfig.getString("Discord Guild","1158515580802969620");
        DISCORD_CHANNEL = mainConfig.getString("Discord Channel","1169047609810370560");
        BOT_TOKEN = mainConfig.getString("Bot Token", "token");
    }

    public static String getJdbcUrl() {
        return JDBC_URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getTablePrefix() {
        return TABLE_PREFIX;
    }

    public static Integer getSaveInterval() {
        return SAVE_INTERVAL;
    }

    public static String getDifficultyEasyIcon() {
        return DIFFICULTY_EASY_ICON;
    }

    public static String getDifficultyMediumIcon() {
        return DIFFICULTY_MEDIUM_ICON;
    }

    public static String getDifficultyHardIcon() {
        return DIFFICULTY_HARD_ICON;
    }

    public static String getDifficultyExtremeIcon() {
        return DIFFICULTY_EXTREME_ICON;
    }

    public static String getDiscordChannel() {
        return DISCORD_CHANNEL;
    }

    public static String getBotToken() {
        return BOT_TOKEN;
    }

    public static String getDiscordGuild() {
        return DISCORD_GUILD;
    }
}
