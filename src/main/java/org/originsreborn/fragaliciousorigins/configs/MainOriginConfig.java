package org.originsreborn.fragaliciousorigins.configs;

import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainOriginConfig extends OriginConfig {
    boolean sharpness, smite, arthapods;
    //cooldowns
    private int primaryCooldown, secondaryCooldown;
    //attributes, ;
    private double armor, attack, armorToughness, attackSpeed, flySpeed, luck, maxHealth,
            movementSpeed, scale, stepHeight, jumpstrength,
            blockInteractRange, entityInteractRange, blockBreakSpeed, gravity, fallDamageMultiplier, safeFallDistance;
    private boolean stepSound;
    private double explosionKnockbackResistance, miningEfficiency, movementEfficiency, oxygenBonus, sneakingSpeed, submergedMiningSpeed, sweepingDamageRatio, waterMovementEfficiency;
    //DamageModifiers
    private double knockbackResistance, explosionsDamage, meleeDamage, projectileDamage, magicDamage, fireDamage, waterDamage, dodgeChance, meleeAtackMultiplier;
    private float burnDuration;
    //permissions & placeholders
    private  int difficulty;
    private List<String> permissions, originDesc;
    private Ability primary, secondary, crouch;
    private List<Ability> abilities;
    private String originName, icon;


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
            attributesNode.node("attackDamage").set(1.0); //NUMERIC
            attributesNode.node("attackSpeed").set(1.0); //multiplier
            attributesNode.node("flyingSpeed").set(0.4); //numeric
            attributesNode.node("knockbackResistance").set(0); //ADDITIONAL
            attributesNode.node("luck").set(0f); //NUMERIC
            attributesNode.node("maxHealth").set(20); //NUMERIC
            attributesNode.node("movementSpeed").set(1.0); //multiplier
            attributesNode.node("scale").set(1.0); //multiplier
            attributesNode.node("stepHeight").set(1.0); //multiplier
            attributesNode.node("jumpStrength").set(1.0); //multiplier
            attributesNode.node("blockInteractRange").set(1.0); //NUMERIC
            attributesNode.node("entityInteractRange").set(1.0); //NUMERIC
            attributesNode.node("blockBreakSpeed").set(1); //multiplier
            attributesNode.node("gravity").set(1.0); //multiplier
            attributesNode.node("safeFallDistance").set(3); //NUMERIC
            attributesNode.node("fallDamageMultiplier").set(1.0); //multiplier
            attributesNode.node("stepSound").set(true);
            attributesNode.node("explosionKnockbackResistance").set(0.0);
            attributesNode.node("miningEfficiency").set(0.0);
            attributesNode.node("movementEfficiency").set(0.0);
            attributesNode.node("oxygenBonus").set(0.0);
            attributesNode.node("sneakingSpeed").set(0.3);
            attributesNode.node("submergedMiningSpeed").set(0.2);
            attributesNode.node("sweepingDamageRatio").set(0.0);
            attributesNode.node("waterMovementEfficiency").set(0.0);
            CommentedConfigurationNode damageModifiersNode = getConfigNode().node("damageModifiers");
            damageModifiersNode.node("explosionDamageMultiplier").set(1.0);
            damageModifiersNode.node("meleeDamageMultiplier").set(1.0);
            damageModifiersNode.node("projectileDamageMultiplier").set(1.0);
            damageModifiersNode.node("magicDamageMultiplier").set(1.0);
            damageModifiersNode.node("fireDamageMultiplier").set(1.0);
            damageModifiersNode.node("waterDamageMultiplier").set(1.0);
            damageModifiersNode.node("burnDurationMultiplier").set(1.0);
            damageModifiersNode.node("meleeAttackMultiplier").set(1.0);
            damageModifiersNode.node("dodgeChance").set(0.0);
            damageModifiersNode.node("sharpnessImmune").set(false);
            damageModifiersNode.node("smiteImmune").set(true);
            damageModifiersNode.node("baneOfArthropodsImmune").set(true);

            CommentedConfigurationNode permissions = getConfigNode().node("permissions");
            permissions.set(Collections.singletonList(""));

            CommentedConfigurationNode placeholdersNode = getConfigNode().node("placeholders");
            CommentedConfigurationNode placeholdersNodeTitle = placeholdersNode.node("title");
            placeholdersNodeTitle.node("name").set("defaultName");
            placeholdersNodeTitle.node("description").set(Collections.singletonList(""));
            placeholdersNodeTitle.node("icon").set("icon");
            placeholdersNodeTitle.node("difficulty").set(0);
            CommentedConfigurationNode placeholdersPrimaryAbility = placeholdersNode.node("primaryAbility");
            placeholdersPrimaryAbility.node("name").set("primaryAbility");
            placeholdersPrimaryAbility.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholdersSecondaryAbility = placeholdersNode.node("secondaryAbility");
            placeholdersSecondaryAbility.node("name").set("secondaryAbility");
            placeholdersSecondaryAbility.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderCrouchAbility = placeholdersNode.node("crouchAbility");
            placeholderCrouchAbility.node("name").set("abilityName");
            placeholderCrouchAbility.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility1 = placeholdersNode.node("ability1");
            placeholderAbility1.node("name").set("abilityName");
            placeholderAbility1.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility2 = placeholdersNode.node("ability2");
            placeholderAbility2.node("name").set("abilityName");
            placeholderAbility2.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility3 = placeholdersNode.node("ability3");
            placeholderAbility3.node("name").set("abilityName");
            placeholderAbility3.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility4 = placeholdersNode.node("ability4");
            placeholderAbility4.node("name").set("abilityName");
            placeholderAbility4.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility5 = placeholdersNode.node("ability5");
            placeholderAbility5.node("name").set("abilityName");
            placeholderAbility5.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility6 = placeholdersNode.node("ability6");
            placeholderAbility6.node("name").set("abilityName");
            placeholderAbility6.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility7 = placeholdersNode.node("ability7");
            placeholderAbility7.node("name").set("abilityName");
            placeholderAbility7.node("description").set(Collections.singletonList(""));
            CommentedConfigurationNode placeholderAbility8 = placeholdersNode.node("ability8");
            placeholderAbility8.node("name").set("abilityName");
            placeholderAbility8.node("description").set(Collections.singletonList(""));
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void defineVariables() {
        //cooldowns
        primaryCooldown = getConfigNode().node("cooldowns").node("primaryMaxCooldown").getInt();
        secondaryCooldown = getConfigNode().node("cooldowns").node("secondaryMaxCooldown").getInt();

        armor = getConfigNode().node("attributes").node("armor").getDouble();
        armorToughness = getConfigNode().node("attributes").node("armorToughness").getDouble();
        attack = getConfigNode().node("attributes").node("attackDamage").getDouble();
        attackSpeed = getConfigNode().node("attributes").node("attackSpeed").getDouble();
        knockbackResistance = getConfigNode().node("attributes").node("knockbackResistance").getDouble();
        flySpeed = getConfigNode().node("attributes").node("flyingSpeed").getDouble();
        luck = getConfigNode().node("attributes").node("luck").getDouble();
        maxHealth = getConfigNode().node("attributes").node("maxHealth").getDouble();
        movementSpeed = getConfigNode().node("attributes").node("movementSpeed").getDouble();
        scale = getConfigNode().node("attributes").node("scale").getDouble();
        stepHeight = getConfigNode().node("attributes").node("stepHeight").getDouble();
        jumpstrength = getConfigNode().node("attributes").node("jumpStrength").getDouble();
        blockInteractRange = getConfigNode().node("attributes").node("blockInteractRange").getDouble();
        entityInteractRange = getConfigNode().node("attributes").node("entityInteractRange").getDouble();
        blockBreakSpeed = getConfigNode().node("attributes").node("blockBreakSpeed").getDouble();
        gravity = getConfigNode().node("attributes").node("gravity").getDouble();
        safeFallDistance = getConfigNode().node("attributes").node("safeFallDistance").getDouble();
        fallDamageMultiplier = getConfigNode().node("attributes").node("fallDamageMultiplier").getDouble();
        stepSound = getConfigNode().node("attributes").node("stepSound").getBoolean();
        //1.21 attributes
        explosionKnockbackResistance = getConfigNode().node("attributes").node("explosionKnockbackResistance").getDouble();
        miningEfficiency = getConfigNode().node("attributes").node("miningEfficiency").getDouble();
        movementEfficiency = getConfigNode().node("attributes").node("movementEfficiency").getDouble();
        oxygenBonus = getConfigNode().node("attributes").node("oxygenBonus").getDouble();
        sneakingSpeed = getConfigNode().node("attributes").node("sneakingSpeed").getDouble();
        submergedMiningSpeed = getConfigNode().node("attributes").node("submergedMiningSpeed").getDouble();
        sweepingDamageRatio = getConfigNode().node("attributes").node("sweepingDamageRatio").getDouble();
        waterMovementEfficiency = getConfigNode().node("attributes").node("waterMovementEfficiency").getDouble();
        //damage modifiers
        explosionsDamage = getConfigNode().node("damageModifiers").node("explosionDamageMultiplier").getDouble();
        meleeDamage = getConfigNode().node("damageModifiers").node("meleeDamageMultiplier").getDouble();
        projectileDamage = getConfigNode().node("damageModifiers").node("projectileDamageMultiplier").getDouble();
        waterDamage = getConfigNode().node("damageModifiers").node("waterDamageMultiplier").getDouble();
        magicDamage = getConfigNode().node("damageModifiers").node("magicDamageMultiplier").getDouble();
        fireDamage = getConfigNode().node("damageModifiers").node("fireDamageMultiplier").getDouble();
        burnDuration = getConfigNode().node("damageModifiers").node("burnDurationMultiplier").getFloat();
        dodgeChance = getConfigNode().node("damageModifiers").node("dodgeChance").getDouble();
        meleeAtackMultiplier = getConfigNode().node("damageModifiers").node("meleeAttackMultiplier").getDouble();
        sharpness = getConfigNode().node("damageModifiers").node("sharpnessImmune").getBoolean();
        smite = getConfigNode().node("damageModifiers").node("smiteImmune").getBoolean();
        arthapods = getConfigNode().node("damageModifiers").node("baneOfArthropodsImmune").getBoolean();
        permissions = getStringsFromNode(getConfigNode().node("permissions"));
        //info
        originName = getConfigNode().node("placeholders").node("title").node("name").getString();
        originDesc = getStringsFromNode(getConfigNode().node("placeholders").node("title").node("description"));
        icon = getConfigNode().node("placeholders").node("title").node("icon").getString();
        difficulty = getConfigNode().node("placeholders").node("difficulty").getInt();
        abilities = new ArrayList<Ability>(8);
        primary = new Ability(getConfigNode().node("placeholders").node("primaryAbility").node("name").getString(),
                getStringsFromNode(getConfigNode().node("placeholders").node("primaryAbility").node("description"))
        );
        secondary = new Ability(getConfigNode().node("placeholders").node("secondaryAbility").node("name").getString(),
                getStringsFromNode(getConfigNode().node("placeholders").node("secondaryAbility").node("description"))
        );
        crouch = new Ability(getConfigNode().node("placeholders").node("crouchAbility").node("name").getString(),
                getStringsFromNode(getConfigNode().node("placeholders").node("crouchAbility").node("description"))
        );
        for (int ability = 1; ability < 9; ability++) {
            abilities.add(new Ability(
                    getConfigNode().node("placeholders").node("ability" + ability).node("name").getString(),
                    getStringsFromNode(getConfigNode().node("placeholders").node("ability" + ability).node("description"))
            ));
        }
    }

    public int getPrimaryMaxCooldown() {
        return primaryCooldown;
    }

    public int getSecondaryMaxCooldown() {
        return secondaryCooldown;
    }

    public double getArmor() {
        return armor;
    }

    public double getArmorToughness() {
        return armorToughness;
    }

    public double getAttackDamage() {
        return attack;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getFlyingSpeed() {
        return flySpeed;
    }

    public double getKnockbackResistance() {
        return knockbackResistance;
    }

    public double getLuck() {
        return luck;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public double getScale() {
        return scale;
    }

    public double getStepHeight() {
        return stepHeight;
    }

    public double getJumpStrength() {
        return jumpstrength;
    }

    public double getBlockInteractRange() {
        return blockInteractRange;
    }

    public double getPlayerEntityInteractRange() {
        return entityInteractRange;
    }

    public double getBlockBreakSpeed() {
        return blockBreakSpeed;
    }

    public double getGravity() {
        return gravity;
    }

    public double getSafeFallDistance() {
        return safeFallDistance;
    }

    public double getFallDamageMultiplier() {
        return fallDamageMultiplier;
    }

    public boolean hasStepSounds() {
        return stepSound;
    }

    public double getExplosionKnockbackResistance() {
        return explosionKnockbackResistance;
    }

    public double getMiningEfficiency() {
        return miningEfficiency;
    }

    public double getMovementEfficiency() {
        return movementEfficiency;
    }

    public double getOxygenBonus() {
        return oxygenBonus;
    }

    public double getSneakingSpeed() {
        return sneakingSpeed;
    }

    public double getSubmergedMiningSpeed() {
        return submergedMiningSpeed;
    }

    public double getSweepingDamageRatio() {
        return sweepingDamageRatio;
    }

    public double getWaterMovementEfficiency() {
        return waterMovementEfficiency;
    }

    public double getExplosionDamageMultiplier() {
        return explosionsDamage;
    }

    public double getMeleeDamageMultiplier() {
        return meleeDamage;
    }

    public double getProjectileDamageMultiplier() {
        return projectileDamage;
    }

    public double getMagicDamageMultiplier() {
        return magicDamage;
    }

    public double getWaterDamageMultiplier() {
        return waterDamage;
    }

    public double getFireDamageMultiplier() {
        return fireDamage;
    }

    public float getBurnDurationMultiplier() {
        return burnDuration;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }
    public double getMeleeAttackMultiplier(){
        return meleeAtackMultiplier;
    }

    public boolean getSharpnessImmune() {
        return sharpness;
    }

    public boolean getSmiteImmune() {
        return smite;
    }

    public boolean getBaneOfArthopodsImmune() {
        return arthapods;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public String getPlaceholdersNodeTitleName() {
        return originName;
    }

    public List<String> getPlaceholdersNodeTitleDescription() {
        return originDesc;
    }

    public String getPlaceholdersNodeTitleIcon() {
        return icon;
    }

    public String getPlaceholdersPrimaryAbilityName() {
        return primary.getName();
    }

    public List<String> getPlaceholdersPrimaryAbilityDescription() {
        return primary.getDescription();
    }

    public String getPlaceholdersSecondaryAbilityName() {
        return secondary.getName();
    }

    public List<String> getPlaceholdersSecondaryAbilityDescription() {
        return secondary.getDescription();
    }

    public String getPlaceholdersCrouchAbilityName() {
        return crouch.getName();
    }

    public List<String> getPlaceholdersCrouchAbilityDescription() {
        return crouch.getDescription();
    }

    public String getPlaceholderAbilityName(int ability) {
        if (ability > 9 || ability < 1) {
            return "Invalid Ability";
        }
        return abilities.get(ability - 1).getName();
    }

    public List<String> getAbilityDescription(int ability) {
        if (ability > 9 || ability < 1) {
            return Collections.emptyList();
        }
        return abilities.get(ability - 1).getDescription();
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public Ability getCrouch() {
        return crouch;
    }

    public Ability getSecondary() {
        return secondary;
    }

    public Ability getPrimary() {
        return primary;
    }

    public int getDifficulty(){
        return difficulty;
    }

}