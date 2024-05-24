package org.originsreborn.fragaliciousorigins.configs;

import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class OriginConfig {
    private final Path configPath;
    private final Path directoryPath;
    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode configNode;

    public OriginConfig(OriginType type, String yamlName) {
        this.directoryPath = Paths.get(FragaliciousOrigins.INSTANCE.getDataFolder().getPath() + File.separator + type.name().toLowerCase());
        this.configPath = Paths.get(FragaliciousOrigins.INSTANCE.getDataFolder().getPath() + File.separator + type.name().toLowerCase() + File.separator + yamlName + ".yml");
        this.loader = YamlConfigurationLoader.builder()
                .path(configPath).nodeStyle(NodeStyle.BLOCK)
                .build();
        loadConfig();
    }

    public OriginConfig(Path directoryPath, Path configPath) {
        this.directoryPath = directoryPath;
        this.configPath = configPath;
        this.loader = YamlConfigurationLoader.builder()
                .path(configPath).nodeStyle(NodeStyle.BLOCK)
                .build();
        loadConfig();
    }

    public void loadConfig() {
        try {
            if (!Files.exists(configPath)) {
                Files.createDirectories(directoryPath);
                Files.createFile(configPath);
                configNode = loader.createNode();
                populateDefaultConfig();
                saveConfig();
            } else {
                configNode = loader.load();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void populateDefaultConfig();

    public void saveConfig() {
        try {
            loader.save(configNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Path getConfigPath() {
        return configPath;
    }

    public Path getDirectoryPath() {
        return directoryPath;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
        return loader;
    }

    public CommentedConfigurationNode getConfigNode() {
        return configNode;
    }
}
