package org.originsreborn.fragaliciousorigins.origins.configs;

import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneralOriginConfig {
    private final Path configPath;
    private final Path directoryPath;
    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode configNode;


    public GeneralOriginConfig(OriginType type ) {
        directoryPath = Paths.get(FragaliciousOrigins.INSTANCE.getDataFolder().getPath() + File.separator + type.name().toLowerCase());
        this.configPath = Paths.get(FragaliciousOrigins.INSTANCE.getDataFolder().getPath() + File.separator + type.name().toLowerCase() + File.separator + "general.yml");
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

    private void populateDefaultConfig() {
        configNode = loader.createNode(); // Create a new node
        try {
            CommentedConfigurationNode cooldownNode = configNode.node("cooldowns");
            cooldownNode.node("primaryMaxCooldown").set(0);
            cooldownNode.node("secondaryMaxCooldown").set(0);
            CommentedConfigurationNode attributesNode = configNode.node("attributes");
            attributesNode.node("armor").set(0f);
            attributesNode.node("armorToughness").set(0f);
            attributesNode.node("attackDamage").set(2f);
            attributesNode.node("attackKnockback").set(0f);
            attributesNode.node("attackSpeed").set(4f);
            attributesNode.node("flyingSpeed").set(0.4f);
            attributesNode.node("knockbackResistance").set(0f);
            attributesNode.node("luck").set(0f);
            attributesNode.node("maxHealth").set(20f);
            attributesNode.node("movementSpeed").set(0.7f);
            attributesNode.node("scale").set(1f);
            attributesNode.node("stepHeight").set(0.6f);
            attributesNode.node("jumpStrength").set(0.7f);
            attributesNode.node("blockInteractRange").set(4.5f);
            attributesNode.node("blockBreakSpeed").set(3f);
            attributesNode.node("gravity").set(0.08f);
            attributesNode.node("safeFallDistance").set(3f);
            attributesNode.node("fallDamageMultiplier").set(1f);
            attributesNode.node("saturationCap").set(20f);
            CommentedConfigurationNode damageModifiersNode = configNode.node("damageModifiers");
            damageModifiersNode.node("explosionDamageMultiplier").set(1.0);
            damageModifiersNode.node("meleeDamageMultiplier").set(1.0);
            damageModifiersNode.node("projectileDamageMultiplier").set(1.0);
            damageModifiersNode.node("magicDamageMultiplier").set(1.0);
            damageModifiersNode.node("fireDamageMultiplier").set(1.0);
            CommentedConfigurationNode placeholdersNode = configNode.node("placeholders");
            CommentedConfigurationNode placeholdersNodeTitle = placeholdersNode.node("title");
            placeholdersNodeTitle.node("name").set("defaultName");
            placeholdersNodeTitle.node("description").set("A basic human");
            placeholdersNodeTitle.node("icon").set("icon");
            CommentedConfigurationNode placeholdersPrimaryAbility = placeholdersNode.node("primaryAbility");
            placeholdersPrimaryAbility.node("name").set("primaryAbility");
            placeholdersPrimaryAbility.node("description").set("an ability");
            CommentedConfigurationNode placeholdersSecondaryAbility = placeholdersNode.node("secondaryAbility");
            placeholdersSecondaryAbility.node("name").set("secondaryAbility");
            placeholdersSecondaryAbility.node("description").set("an ability");
            CommentedConfigurationNode placeholderAbility1 = placeholdersNode.node("ability1");
            placeholderAbility1.node("name").set("abilityName");
            placeholderAbility1.node("description").set("abilityDescription");
            CommentedConfigurationNode placeholderAbility2 = placeholdersNode.node("ability2");
            placeholderAbility2.node("name").set("abilityName");
            placeholderAbility2.node("description").set("abilityDescription");
            CommentedConfigurationNode placeholderAbility3 = placeholdersNode.node("ability3");
            placeholderAbility3.node("name").set("abilityName");
            placeholderAbility3.node("description").set("abilityDescription");
            CommentedConfigurationNode placeholderAbility4 = placeholdersNode.node("ability4");
            placeholderAbility4.node("name").set("abilityName");
            placeholderAbility4.node("description").set("abilityDescription");
            CommentedConfigurationNode placeholderAbility5 = placeholdersNode.node("ability5");
            placeholderAbility5.node("name").set("abilityName");
            placeholderAbility5.node("description").set("abilityDescription");
            CommentedConfigurationNode placeholderAbility6 = placeholdersNode.node("ability6");
            placeholderAbility6.node("name").set("abilityName");
            placeholderAbility6.node("description").set("abilityDescription");
            CommentedConfigurationNode placeholderAbility7 = placeholdersNode.node("ability7");
            placeholderAbility7.node("name").set("abilityName");
            placeholderAbility7.node("description").set("abilityDescription");
            CommentedConfigurationNode placeholderAbility8 = placeholdersNode.node("ability8");
            placeholderAbility8.node("name").set("abilityName");
            placeholderAbility8.node("description").set("abilityDescription");
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveConfig() {
        try {
            loader.save(configNode);
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public CommentedConfigurationNode getConfigNode() {
        return configNode;
    }
    public int getPrimaryMaxCooldown() {
        return configNode.node("cooldowns").node("primaryMaxCooldown").getInt();
    }

    public int getSecondaryMaxCooldown() {
        return configNode.node("cooldowns").node("secondaryMaxCooldown").getInt();
    }

    public float getArmor() {
        return configNode.node("attributes").node("armor").getFloat();
    }

    public float getArmorToughness() {
        return configNode.node("attributes").node("armorToughness").getFloat();
    }

    public float getAttackDamage() {
        return configNode.node("attributes").node("attackDamage").getFloat();
    }

    public float getAttackKnockback() {
        return configNode.node("attributes").node("attackKnockback").getFloat();
    }

    public float getAttackSpeed() {
        return configNode.node("attributes").node("attackSpeed").getFloat();
    }

    public float getFlyingSpeed() {
        return configNode.node("attributes").node("flyingSpeed").getFloat();
    }

    public float getKnockbackResistance() {
        return configNode.node("attributes").node("knockbackResistance").getFloat();
    }

    public float getLuck() {
        return configNode.node("attributes").node("luck").getFloat();
    }

    public float getMaxHealth() {
        return configNode.node("attributes").node("maxHealth").getFloat();
    }

    public float getMovementSpeed() {
        return configNode.node("attributes").node("movementSpeed").getFloat();
    }

    public float getScale() {
        return configNode.node("attributes").node("scale").getFloat();
    }

    public float getStepHeight() {
        return configNode.node("attributes").node("stepHeight").getFloat();
    }

    public float getJumpStrength() {
        return configNode.node("attributes").node("jumpStrength").getFloat();
    }

    public float getBlockInteractRange() {
        return configNode.node("attributes").node("blockInteractRange").getFloat();
    }

    public float getBlockBreakSpeed() {
        return configNode.node("attributes").node("blockBreakSpeed").getFloat();
    }

    public float getGravity() {
        return configNode.node("attributes").node("gravity").getFloat();
    }

    public float getSafeFallDistance() {
        return configNode.node("attributes").node("safeFallDistance").getFloat();
    }

    public float getFallDamageMultiplier() {
        return configNode.node("attributes").node("fallDamageMultiplier").getFloat();
    }

    public float getSaturationCap() {
        return configNode.node("attributes").node("saturationCap").getFloat();
    }

    public double getExplosionDamageMultiplier() {
        return configNode.node("damageModifiers").node("explosionDamageMultiplier").getDouble();
    }

    public double getMeleeDamageMultiplier() {
        return configNode.node("damageModifiers").node("meleeDamageMultiplier").getDouble();
    }

    public double getProjectileDamageMultiplier() {
        return configNode.node("damageModifiers").node("projectileDamageMultiplier").getDouble();
    }

    public double getMagicDamageMultiplier() {
        return configNode.node("damageModifiers").node("magicDamageMultiplier").getDouble();
    }

    public double getFireDamageMultiplier() {
        return configNode.node("damageModifiers").node("fireDamageMultiplier").getDouble();
    }

    public String getPlaceholdersNodeTitleName() {
        return configNode.node("placeholders").node("title").node("name").getString();
    }

    public String getPlaceholdersNodeTitleDescription() {
        return configNode.node("placeholders").node("title").node("description").getString();
    }

    public String getPlaceholdersNodeTitleIcon() {
        return configNode.node("placeholders").node("title").node("icon").getString();
    }

    public String getPlaceholdersPrimaryAbilityName() {
        return configNode.node("placeholders").node("primaryAbility").node("name").getString();
    }

    public String getPlaceholdersPrimaryAbilityDescription() {
        return configNode.node("placeholders").node("primaryAbility").node("description").getString();
    }

    public String getPlaceholdersSecondaryAbilityName() {
        return configNode.node("placeholders").node("secondaryAbility").node("name").getString();
    }

    public String getPlaceholdersSecondaryAbilityDescription() {
        return configNode.node("placeholders").node("secondaryAbility").node("description").getString();
    }

    public String getPlaceholderAbility1Name() {
        return configNode.node("placeholders").node("ability1").node("name").getString();
    }

    public String getPlaceholderAbility1Description() {
        return configNode.node("placeholders").node("ability1").node("description").getString();
    }

    public String getPlaceholderAbility2Name() {
        return configNode.node("placeholders").node("ability2").node("name").getString();
    }

    public String getPlaceholderAbility2Description() {
        return configNode.node("placeholders").node("ability2").node("description").getString();
    }

    public String getPlaceholderAbility3Name() {
        return configNode.node("placeholders").node("ability3").node("name").getString();
    }

    public String getPlaceholderAbility3Description() {
        return configNode.node("placeholders").node("ability3").node("description").getString();
    }

    public String getPlaceholderAbility4Name() {
        return configNode.node("placeholders").node("ability4").node("name").getString();
    }

    public String getPlaceholderAbility4Description() {
        return configNode.node("placeholders").node("ability4").node("description").getString();
    }

    public String getPlaceholderAbility5Name() {
        return configNode.node("placeholders").node("ability5").node("name").getString();
    }

    public String getPlaceholderAbility5Description() {
        return configNode.node("placeholders").node("ability5").node("description").getString();
    }

    public String getPlaceholderAbility6Name() {
        return configNode.node("placeholders").node("ability6").node("name").getString();
    }

    public String getPlaceholderAbility6Description() {
        return configNode.node("placeholders").node("ability6").node("description").getString();
    }

    public String getPlaceholderAbility7Name() {
        return configNode.node("placeholders").node("ability7").node("name").getString();
    }

    public String getPlaceholderAbility7Description() {
        return configNode.node("placeholders").node("ability7").node("description").getString();
    }

    public String getPlaceholderAbility8Name() {
        return configNode.node("placeholders").node("ability8").node("name").getString();
    }

    public String getPlaceholderAbility8Description() {
        return configNode.node("placeholders").node("ability8").node("description").getString();
    }

}