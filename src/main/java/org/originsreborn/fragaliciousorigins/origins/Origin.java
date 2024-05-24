package org.originsreborn.fragaliciousorigins.origins;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.DamageUtil;
import org.originsreborn.fragaliciousorigins.util.PermissionsUtil;

import java.util.UUID;

import static org.originsreborn.fragaliciousorigins.util.PlayerUtils.setAttribute;

public abstract class Origin {
    private final UUID uuid;
    private final OriginType type;
    private final OriginState state;
    private int primaryCooldown = 0;
    private int secondaryCooldown = 0;
    private boolean primaryEnabled = false;
    private boolean secondaryEnabled = false;

    //Initial creation
    public Origin(UUID uuid, OriginType type, OriginState state) {
        this.uuid = uuid;
        this.type = type;
        this.state = state;
        setDefaultStats();
        getPlayer().sendMessage("You are now a " + getType().getDisplay());
    }

    public Origin(UUID uuid, OriginType type, OriginState state, String customDataString) {
        this.uuid = uuid;
        this.type = type;
        this.state = state;
        setDefaultStats();
        deserializeCustomData(customDataString);
        getPlayer().sendMessage("You are now a " + getType().getDisplay());
    }

    public static void onReload() {

    }

    public static TextColor errorColor() {
        return TextColor.color(0xFF0029);
    }

    public static TextColor textColor() {
        return TextColor.color(0xFFD700);
    }

    public static TextColor enableColor() {
        return TextColor.color(0x18FF00);
    }

    public void updateStats() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public OriginState getState() {
        return state;
    }

    public abstract MainOriginConfig getConfig();

    /**
     * Runs logic that the origin may need to perform at set intervals
     *
     * @param tickNum
     */
    public abstract void originTick(int tickNum);

    /**
     * Generates the Origin particle of the said origin
     *
     * @param tickNum
     */
    public abstract void originParticle(int tickNum);

    /**
     * Sets the players default stats as suggested in the config
     */
    public void setDefaultStats() {
        Player player = this.getPlayer();
        MainOriginConfig config = getConfig();
        //permissions
        PermissionsUtil.resetPermissions(player);
        if (getConfig().getPermissions() != null && !config.getPermissions().isEmpty()) {
            PermissionsUtil.registerPermission(player, config.getPermissions());
        }
        //config
        setAttribute(player, Attribute.GENERIC_ARMOR, config.getArmor());
        setAttribute(player, Attribute.GENERIC_ARMOR_TOUGHNESS, config.getArmorToughness());
        setAttribute(player, Attribute.GENERIC_ATTACK_DAMAGE, config.getAttackDamage());
        setAttribute(player, Attribute.GENERIC_ATTACK_DAMAGE, config.getAttackDamage());
        setAttribute(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE, config.getKnockbackResistance());
        setAttribute(player, Attribute.GENERIC_LUCK, config.getLuck());
        setAttribute(player, Attribute.GENERIC_MAX_HEALTH, config.getMaxHealth());
        setAttribute(player, Attribute.GENERIC_SCALE, config.getScale());
        setAttribute(player, Attribute.GENERIC_STEP_HEIGHT, config.getStepHeight());
        setAttribute(player, Attribute.GENERIC_JUMP_STRENGTH, config.getJumpStrength());
        setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, config.getMovementSpeed());
        setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, config.getBlockInteractRange());
        setAttribute(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, config.getPlayerEntityInteractRange());
        setAttribute(player, Attribute.PLAYER_BLOCK_BREAK_SPEED, config.getBlockBreakSpeed());
        setAttribute(player, Attribute.GENERIC_GRAVITY, config.getGravity());
        setAttribute(player, Attribute.GENERIC_SAFE_FALL_DISTANCE, config.getSafeFallDistance());
        setAttribute(player, Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER, config.getFallDamageMultiplier());
    }

    public void onDeath(PlayerDeathEvent event) {
        setDefaultStats();
    }

    public abstract String serializeCustomData();

    public abstract void deserializeCustomData(String customData);

    public OriginType getType() {
        return type;
    }

    public Player getPlayer() {
        return FragaliciousOrigins.INSTANCE.getServer().getPlayer(uuid);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String primaryAbilityName() {
        return getConfig().getPlaceholdersPrimaryAbilityName();
    }

    public String secondaryAbilityName() {
        return getConfig().getPlaceholdersSecondaryAbilityName();
    }

    public void primaryAbility() {
        if (getConfig().getPlaceholdersPrimaryAbilityName().equals("primaryAbility")) {
            getPlayer().sendMessage(Component.text("Your origin does not have a primary ability").color(TextColor.color(errorColor())));
        }
        if (getPrimaryCooldown() > 0) {
            primaryAbilityTimerCooldownMsg();
        } else {
            primaryAbilityLogic();
            setPrimaryCooldown(getPrimaryMaxCooldown());
        }
    }

    public abstract void primaryAbilityLogic();

    public void secondaryAbility() {
        if (getConfig().getPlaceholdersSecondaryAbilityName().equals("secondaryAbility")) {
            getPlayer().sendMessage(Component.text("Your origin does not have a secondary ability").color(TextColor.color(errorColor())));
        }
        if (getSecondaryCooldown() > 0) {
            secondaryAbilityTimerCooldownMsg();
        } else {
            secondaryAbilityLogic();
            setSecondaryCooldown(getSecondaryMaxCooldown());
        }
    }

    public abstract void secondaryAbilityLogic();


    public int getPrimaryCooldown() {
        return primaryCooldown;
    }

    public void setPrimaryCooldown(int cooldown) {
        if (cooldown < 0) {
            this.primaryCooldown = 0;
        } else {
            this.primaryCooldown = cooldown;
        }
    }

    public int getSecondaryCooldown() {
        return secondaryCooldown;
    }

    public void setSecondaryCooldown(int cooldown) {
        if (cooldown < 0) {
            this.secondaryCooldown = 0;
        } else {
            this.secondaryCooldown = cooldown;
        }
    }

    public void cooldownTick() {
        if (getPrimaryCooldown() > 0) {
            setPrimaryCooldown(getPrimaryCooldown() - 1);
        }
        if (getSecondaryCooldown() > 0) {
            setSecondaryCooldown(getSecondaryCooldown() - 1);
        }
    }

    public int getPrimaryMaxCooldown() {
        return getConfig().getPrimaryMaxCooldown();
    }

    public int getSecondaryMaxCooldown() {
        return getConfig().getSecondaryMaxCooldown();
    }

    public abstract OriginDifficulty getDifficulty();

    //////////////////////
    // Player Events
    // See https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/player/package-summary.html
    ////////////////////
    public void onBedEnter(PlayerBedEnterEvent event) {
    }

    public void onBedLeaveEvent(PlayerBedLeaveEvent event) {
    }

    public void onPreCommandSend(PlayerCommandPreprocessEvent event) {
    }

    public void onDropitem(PlayerDropItemEvent event) {
    }

    public void throwEgg(PlayerEggThrowEvent event) {
    }

    public void experienceChange(PlayerExpChangeEvent event) {
    }

    public void playerFish(PlayerFishEvent event) {
    }

    public void changeGamemode(PlayerGameModeChangeEvent event) {
    }

    public void harvestBlock(PlayerHarvestBlockEvent event) {
    }

    public void onIgnite(EntityCombustEvent event) {
        event.setDuration((int) (((double) event.getDuration()) * getConfig().getBurnDurationMultiplier()));
    }

    public void interactStaticEntity(PlayerInteractAtEntityEvent event) {
    }

    public void interactEntity(PlayerInteractEntityEvent event) {
    }

    public void rightClickEvent(PlayerInteractEvent event) {
    }

    public void toolBreak(PlayerItemBreakEvent event) {
    }

    public void consume(PlayerItemConsumeEvent event) {
    }

    public void itemDamage(PlayerItemDamageEvent event) {
    }

    public void mendEvent(PlayerItemMendEvent event) {
    }

    public void onMove(PlayerMoveEvent event) {
    }

    public void pickupArrow(PlayerPickupArrowEvent event) {
    }

    public void onRespawn(PlayerRespawnEvent event) {
    }

    public void onRiptide(PlayerRiptideEvent event) {
    }

    public void onShear(PlayerShearEntityEvent event) {
    }

    public void onTeleport(PlayerTeleportEvent event) {
    }

    public void onToggleFlight(PlayerToggleFlightEvent event) {
    }

    public void onToggleSneak(PlayerToggleSneakEvent event) {
    }

    public void onToggleSprint(PlayerToggleSprintEvent event) {
        //Insert Sprint code here
    }

    public void velocityChange(PlayerVelocityEvent event) {
    }

    public void onPotionEffect(EntityPotionEffectEvent event) {
    }

    public void onShootProjectileHit(ProjectileHitEvent event) {
    }

    public void onPickupItem(EntityPickupItemEvent event) {
    }

    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(Math.random() < getConfig().getDodgeChance());
        if (!event.isCancelled()) {
            DamageUtil.calculateDamageMultipliers(event, this);
        }
    }

    public void onBowShoot(EntityShootBowEvent event) {

    }

    public void onHurtByEntity(EntityDamageByEntityEvent event) {
        DamageUtil.applyEnchantmentImmunities(event, this);
    }

    public void onAttackEntity(EntityDamageByEntityEvent event) {
    }

    public void onToggleSwim(EntityToggleSwimEvent event) {
    }

    public void onToggleGlide(EntityToggleGlideEvent event) {
    }

    public void onResurrectEvent(EntityResurrectEvent event) {
    }

    public void onRocketLaunch(ProjectileLaunchEvent event) {
    }

    public void onArrowLaunch(ProjectileLaunchEvent event) {
    }

    public void onSnowballLaunch(ProjectileLaunchEvent event) {
    }

    public void onTridentLaunch(ProjectileLaunchEvent event) {
    }

    public void onPotionLaunch(ProjectileLaunchEvent event) {
    }

    public void primaryAbilityTimerCooldownMsg() {
        getPlayer().sendMessage(Component.text("Your ability ").color(textColor())
                .append(Component.text(primaryAbilityName()).color(errorColor()))
                .append(Component.text(" will be ready in ").color(textColor()))
                .append(Component.text(primaryCooldown).color(errorColor()))
                .append(Component.text(" seconds.").color(textColor())).asComponent());
    }

    public void secondaryAbilityTimerCooldownMsg() {
        getPlayer().sendMessage(Component.text("Your ability ").color(textColor())
                .append(Component.text(secondaryAbilityName()).color(errorColor()))
                .append(Component.text(" will be ready in ").color(textColor()))
                .append(Component.text(secondaryCooldown).color(errorColor()))
                .append(Component.text(" seconds.").color(textColor())).asComponent());
    }


    public void enableSecondaryAbilityMsg() {
        getPlayer().sendMessage(Component.text("Your ability ").color(errorColor())
                .append(Component.text(secondaryAbilityName()).color(enableColor()))
                .append(Component.text(" is now enabled ").color(textColor())));
    }

    public void disableSecondaryAbilityMsg() {
        getPlayer().sendMessage(Component.text("Your ability ").color(errorColor())
                .append(Component.text(secondaryAbilityName()).color(errorColor()))
                .append(Component.text(" is now disabled ").color(textColor())));
    }

    /**
     * Returns whether your secondary is toggled enabled
     *
     * @return
     */
    public boolean isSecondaryEnabled() {
        return secondaryEnabled;
    }

    /**
     * Changes the state of primary enabled to the opposite of its current state.
     */
    public void secondaryToggle() {
        secondaryEnabled = !secondaryEnabled;
    }

    /**
     * Changes the state of primary enabled to the opposite of its current state based upon user input.
     *
     * @param secondaryEnabled
     */
    public void secondaryToggle(boolean secondaryEnabled) {
        this.secondaryEnabled = secondaryEnabled;
    }

    /**
     * Returns whether your primary is toggled enabled
     *
     * @return
     */
    public boolean isPrimaryEnabled() {
        return primaryEnabled;
    }

    /**
     * Changes the state of primary enabled to the opposite of its current state.
     */
    public void primaryToggle() {
        primaryEnabled = !primaryEnabled;
    }

    /**
     * Changes the state of primary enabled to the opposite of its current state based upon user input.
     *
     * @param primaryEnabled
     */
    public void primaryToggle(boolean primaryEnabled) {
        this.primaryEnabled = primaryEnabled;
    }

}
