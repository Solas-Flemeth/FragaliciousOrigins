package org.originsreborn.fragaliciousorigins.configs;

import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Collections;
import java.util.List;

public class MainOriginConfig extends OriginConfig {

    public MainOriginConfig(OriginType type) {
        super(type, "general");
    }


    public void populateDefaultConfig() {
        try {
            CommentedConfigurationNode cooldownNode = getConfigNode().node("cooldowns");
            cooldownNode.node("primaryMaxCooldown").set(0);
            cooldownNode.node("secondaryMaxCooldown").set(0);

            CommentedConfigurationNode attributesNode = getConfigNode().node("attributes");
            attributesNode.node("armor").set(0f); //ADDITIONAL
            attributesNode.node("armorToughness").set(0); //ADDITIONAL
            attributesNode.node("attackDamage").set(1); //NUMERIC
            attributesNode.node("attackSpeed").set(1.0); //multiplier
            attributesNode.node("flyingSpeed").set(0.4); //numeric
            attributesNode.node("knockbackResistance").set(0); //ADDITIONAL
            attributesNode.node("luck").set(0f); //NUMERIC
            attributesNode.node("maxHealth").set(20); //NUMERIC
            attributesNode.node("movementSpeed").set(1.0); //multiplier
            attributesNode.node("scale").set(1.0); //multiplier
            attributesNode.node("stepHeight").set(1.0); //multiplier
            attributesNode.node("jumpStrength").set(1.0); //multiplier
            attributesNode.node("blockInteractRange").set(4.5); //NUMERIC
            attributesNode.node("entityInteractRange").set(3); //NUMERIC
            attributesNode.node("blockBreakSpeed").set(1); //multiplier
            attributesNode.node("gravity").set(1.0); //multiplier
            attributesNode.node("safeFallDistance").set(3); //NUMERIC
            attributesNode.node("fallDamageMultiplier").set(1.0); //multiplier
            attributesNode.node("saturationCap").set(20); //NUMERIC
            attributesNode.node("skulkDetectable").set(true); //boolean
            CommentedConfigurationNode damageModifiersNode = getConfigNode().node("damageModifiers");
            damageModifiersNode.node("explosionDamageMultiplier").set(1.0);
            damageModifiersNode.node("meleeDamageMultiplier").set(1.0);
            damageModifiersNode.node("projectileDamageMultiplier").set(1.0);
            damageModifiersNode.node("magicDamageMultiplier").set(1.0);
            damageModifiersNode.node("fireDamageMultiplier").set(1.0);
            damageModifiersNode.node("waterDamageMultiplier").set(1.0);
            damageModifiersNode.node("burnDurationMultiplier").set(1.0);
            damageModifiersNode.node("dodgeChance").set(0.0);
            damageModifiersNode.node("sharpnessImmune").set(false);
            damageModifiersNode.node("smiteImmune").set(true);
            damageModifiersNode.node("baneOfArthropodsImmune").set(true);

            CommentedConfigurationNode permissions = getConfigNode().node("permissions");
            permissions.set(Collections.singletonList(""));

            CommentedConfigurationNode placeholdersNode = getConfigNode().node("placeholders");
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


    public int getPrimaryMaxCooldown() {
        return getConfigNode().node("cooldowns").node("primaryMaxCooldown").getInt();
    }

    public int getSecondaryMaxCooldown() {
        return getConfigNode().node("cooldowns").node("secondaryMaxCooldown").getInt();
    }

    public double getArmor() {
        return getConfigNode().node("attributes").node("armor").getDouble();
    }

    public double getArmorToughness() {
        return getConfigNode().node("attributes").node("armorToughness").getDouble();
    }

    public double getAttackDamage() {
        return getConfigNode().node("attributes").node("attackDamage").getDouble();
    }

    public double getAttackKnockback() {
        return getConfigNode().node("attributes").node("attackKnockback").getDouble();
    }

    public double getAttackSpeed() {
        return getConfigNode().node("attributes").node("attackSpeed").getDouble();
    }

    public double getFlyingSpeed() {
        return getConfigNode().node("attributes").node("flyingSpeed").getDouble();
    }

    public double getKnockbackResistance() {
        return getConfigNode().node("attributes").node("knockbackResistance").getDouble();
    }

    public double getLuck() {
        return getConfigNode().node("attributes").node("luck").getDouble();
    }

    public double getMaxHealth() {
        return getConfigNode().node("attributes").node("maxHealth").getDouble();
    }

    public double getMovementSpeed() {
        return getConfigNode().node("attributes").node("movementSpeed").getDouble();
    }

    public double getScale() {
        return getConfigNode().node("attributes").node("scale").getDouble();
    }

    public double getStepHeight() {
        return getConfigNode().node("attributes").node("stepHeight").getDouble();
    }

    public double getJumpStrength() {
        return getConfigNode().node("attributes").node("jumpStrength").getDouble();
    }

    public double getBlockInteractRange() {
        return getConfigNode().node("attributes").node("blockInteractRange").getDouble();
    }

    public double getPlayerEntityInteractRange() {
        return getConfigNode().node("attributes").node("entityInteractRange").getDouble();
    }

    public double getBlockBreakSpeed() {
        return getConfigNode().node("attributes").node("blockBreakSpeed").getDouble();
    }

    public double getGravity() {
        return getConfigNode().node("attributes").node("gravity").getDouble();
    }

    public double getSafeFallDistance() {
        return getConfigNode().node("attributes").node("safeFallDistance").getDouble();
    }

    public double getFallDamageMultiplier() {
        return getConfigNode().node("attributes").node("fallDamageMultiplier").getDouble();
    }

    public double getSaturationCap() {
        return getConfigNode().node("attributes").node("saturationCap").getDouble();
    }
    public boolean getSkulkDetectable() {
        return getConfigNode().node("attributes").node("skulkDetectable").getBoolean();
    }

    public double getExplosionDamageMultiplier() {
        return getConfigNode().node("damageModifiers").node("explosionDamageMultiplier").getDouble();
    }

    public double getMeleeDamageMultiplier() {
        return getConfigNode().node("damageModifiers").node("meleeDamageMultiplier").getDouble();
    }

    public double getProjectileDamageMultiplier() {
        return getConfigNode().node("damageModifiers").node("projectileDamageMultiplier").getDouble();
    }

    public double getMagicDamageMultiplier() {
        return getConfigNode().node("damageModifiers").node("magicDamageMultiplier").getDouble();
    }

    public double getWaterDamageMultiplier() {
        return getConfigNode().node("damageModifiers").node("waterDamageMultiplier").getDouble();
    }
    public double getFireDamageMultiplier() {
        return getConfigNode().node("damageModifiers").node("fireDamageMultiplier").getDouble();
    }

    public double getBurnDurationMultiplier() {
        return getConfigNode().node("damageModifiers").node("burnDurationMultiplier").getDouble();
    }

    public double getDodgeChance() {
        return getConfigNode().node("damageModifiers").node("dodgeChance").getDouble();
    }
    public boolean getSharpnessImmune() {
        return getConfigNode().node("damageModifiers").node("sharpnessImmune").getBoolean();
    }
    public boolean getSmiteImmune() {
        return getConfigNode().node("damageModifiers").node("smiteImmune").getBoolean();
    }
    public boolean getBaneOfArthopodsImmune() {
        return getConfigNode().node("damageModifiers").node("baneOfArthropodsImmune").getBoolean();
    }

    public List<String> getPermissions() {
        try {
            return getConfigNode().node("permissions").getList(String.class);
        } catch (SerializationException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public String getPlaceholdersNodeTitleName() {
        return getConfigNode().node("placeholders").node("title").node("name").getString();
    }

    public String getPlaceholdersNodeTitleDescription() {
        return getConfigNode().node("placeholders").node("title").node("description").getString();
    }

    public String getPlaceholdersNodeTitleIcon() {
        return getConfigNode().node("placeholders").node("title").node("icon").getString();
    }

    public String getPlaceholdersPrimaryAbilityName() {
        return getConfigNode().node("placeholders").node("primaryAbility").node("name").getString();
    }

    public String getPlaceholdersPrimaryAbilityDescription() {
        return getConfigNode().node("placeholders").node("primaryAbility").node("description").getString();
    }

    public String getPlaceholdersSecondaryAbilityName() {
        return getConfigNode().node("placeholders").node("secondaryAbility").node("name").getString();
    }

    public String getPlaceholdersSecondaryAbilityDescription() {
        return getConfigNode().node("placeholders").node("secondaryAbility").node("description").getString();
    }

    public String getPlaceholderAbility1Name() {
        return getConfigNode().node("placeholders").node("ability1").node("name").getString();
    }

    public String getPlaceholderAbility1Description() {
        return getConfigNode().node("placeholders").node("ability1").node("description").getString();
    }

    public String getPlaceholderAbility2Name() {
        return getConfigNode().node("placeholders").node("ability2").node("name").getString();
    }

    public String getPlaceholderAbility2Description() {
        return getConfigNode().node("placeholders").node("ability2").node("description").getString();
    }

    public String getPlaceholderAbility3Name() {
        return getConfigNode().node("placeholders").node("ability3").node("name").getString();
    }

    public String getPlaceholderAbility3Description() {
        return getConfigNode().node("placeholders").node("ability3").node("description").getString();
    }

    public String getPlaceholderAbility4Name() {
        return getConfigNode().node("placeholders").node("ability4").node("name").getString();
    }

    public String getPlaceholderAbility4Description() {
        return getConfigNode().node("placeholders").node("ability4").node("description").getString();
    }

    public String getPlaceholderAbility5Name() {
        return getConfigNode().node("placeholders").node("ability5").node("name").getString();
    }

    public String getPlaceholderAbility5Description() {
        return getConfigNode().node("placeholders").node("ability5").node("description").getString();
    }

    public String getPlaceholderAbility6Name() {
        return getConfigNode().node("placeholders").node("ability6").node("name").getString();
    }

    public String getPlaceholderAbility6Description() {
        return getConfigNode().node("placeholders").node("ability6").node("description").getString();
    }

    public String getPlaceholderAbility7Name() {
        return getConfigNode().node("placeholders").node("ability7").node("name").getString();
    }

    public String getPlaceholderAbility7Description() {
        return getConfigNode().node("placeholders").node("ability7").node("description").getString();
    }

    public String getPlaceholderAbility8Name() {
        return getConfigNode().node("placeholders").node("ability8").node("name").getString();
    }

    public String getPlaceholderAbility8Description() {
        return getConfigNode().node("placeholders").node("ability8").node("description").getString();
    }

}