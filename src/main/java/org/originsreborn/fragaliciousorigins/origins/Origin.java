package org.originsreborn.fragaliciousorigins.origins;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.Nullable;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.configs.GeneralOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginDifficulty;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.util.UUID;

import static org.originsreborn.fragaliciousorigins.util.PlayerUtils.setAttribute;

public abstract class Origin {
    private int primaryCooldown;
    private int secondaryCooldown;
    private final UUID uuid;
    private final OriginType type;
    private final OriginState state;
    private boolean primaryEnabled;
    private boolean secondaryEnabled;
    //Initial creation
    public Origin(UUID uuid, OriginType type, OriginState state) {
        this.uuid = uuid;
        this.type = type;
        this.state = state;
        this.primaryCooldown = 0;
        this.secondaryCooldown = 0;
        this.secondaryEnabled = false;
        getPlayer().sendMessage("You are now a " + getType().getDisplay());
    }
    public Origin(UUID uuid, OriginType type, OriginState state, String customDataString) {
        this.uuid = uuid;
        this.type = type;
        this.state = state;
        deserializeCustomData(customDataString);
        getPlayer().sendMessage("You are now a " + getType().getDisplay());
    }
    public UUID getUuid() {
        return uuid;
    }

    public OriginState getState() {
        return state;
    }

    public abstract void originTick(int tickNum);
    public abstract void updateStats();
    public void setDefaultStats(GeneralOriginConfig generalOriginConfig) {
        Player player = this.getPlayer();
        setAttribute(player, Attribute.GENERIC_ARMOR, generalOriginConfig.getArmor());
        setAttribute(player, Attribute.GENERIC_ARMOR_TOUGHNESS, generalOriginConfig.getArmorToughness());
        setAttribute(player, Attribute.GENERIC_ATTACK_DAMAGE, generalOriginConfig.getAttackDamage());
        setAttribute(player, Attribute.GENERIC_ATTACK_KNOCKBACK, generalOriginConfig.getAttackKnockback());
        setAttribute(player, Attribute.GENERIC_ATTACK_DAMAGE, generalOriginConfig.getAttackDamage());
        setAttribute(player, Attribute.GENERIC_FLYING_SPEED, generalOriginConfig.getFlyingSpeed());
        setAttribute(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE, generalOriginConfig.getKnockbackResistance());
        setAttribute(player, Attribute.GENERIC_LUCK, generalOriginConfig.getLuck());
        setAttribute(player, Attribute.GENERIC_SCALE, generalOriginConfig.getScale());
        setAttribute(player, Attribute.GENERIC_STEP_HEIGHT, generalOriginConfig.getStepHeight());
        setAttribute(player, Attribute.GENERIC_JUMP_STRENGTH, generalOriginConfig.getJumpStrength());
        setAttribute(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, generalOriginConfig.getBlockInteractRange());
        setAttribute(player, Attribute.PLAYER_BLOCK_BREAK_SPEED, generalOriginConfig.getBlockBreakSpeed());
        setAttribute(player, Attribute.GENERIC_GRAVITY, generalOriginConfig.getGravity());
        setAttribute(player, Attribute.GENERIC_SAFE_FALL_DISTANCE, generalOriginConfig.getSafeFallDistance());
        setAttribute(player, Attribute.GENERIC_FALL_DAMAGE_MULTIPLIER, generalOriginConfig.getFallDamageMultiplier());
    }

    public abstract void onDeath(PlayerDeathEvent event);

    public static void onReload(){}


    public abstract String serializeCustomData();
    public abstract void deserializeCustomData(String customData);

    public OriginType getType() {
        return type;
    }

    @Nullable
    public Player getPlayer() {
        return FragaliciousOrigins.INSTANCE.getServer().getPlayer(uuid);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public abstract String primaryAbilityName();

    public abstract String secondaryAbilityName();

    public void primaryAbility() {
        getPlayer().sendMessage(Component.text("Your origin does not have a primary ability").color(TextColor.color(0xcc0000)));
    }

    public void secondaryAbility() {
        getPlayer().sendMessage(Component.text("Your origin does not have a secondary ability").color(TextColor.color(0xcc0000)));
    }

    public int getPrimaryCooldown() {
        return primaryCooldown;
    }

    public int getSecondaryCooldown() {
        return secondaryCooldown;
    }

    public void setPrimaryCooldown(int cooldown) {
        if (cooldown < 0) {
            this.primaryCooldown = 0;
        } else this.primaryCooldown = Math.min(cooldown, getPrimaryMaxCooldown());
    }

    public void setSecondaryCooldown(int cooldown) {
        if (cooldown < 0) {
            this.secondaryCooldown = 0;
        } else this.secondaryCooldown = Math.min(cooldown, getSecondaryMaxCooldown());
    }
    public abstract int getPrimaryMaxCooldown();

    public abstract int getSecondaryMaxCooldown();
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
    }

    public void onHurtByEntity(EntityDamageByEntityEvent event) {
    }

    public void onAttackEntity(EntityDamageByEntityEvent event) {
    }

    public void onToggleSwim(EntityToggleSwimEvent event) {
    }

    public void onToggleGlide(EntityToggleGlideEvent event) {
    }

    public void onResurrectEvent(EntityResurrectEvent event) {
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

    public void primaryAbilityTimerCooldownMsg() {
        getPlayer().sendMessage(Component.text("Your ability ").color(errorColor())
                .append(Component.text(primaryAbilityName()).color(errorColor()))
                .append(Component.text(" will be ready in ").color(textColor()))
                .append(Component.text(primaryCooldown).color(errorColor()))
                .append(Component.text(" seconds.").color(textColor())).asComponent());
    }

    public void secondaryAbilityTimerCooldownMsg() {
        getPlayer().sendMessage(Component.text("Your ability ").color(errorColor())
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
     * @return
     */
    public boolean isSecondaryEnabled() {
        return secondaryEnabled;
    }
    /**
     * Changes the state of primary enabled to the opposite of its current state.
     */
    public void secondaryToggle(){
        secondaryEnabled = !secondaryEnabled;
    }

    /**
     * Changes the state of primary enabled to the opposite of its current state based upon user input.
     * @param secondaryEnabled
     */
    public void secondaryToggle(boolean secondaryEnabled){
        this.secondaryEnabled = secondaryEnabled;
    }

    /**
     * Returns whether your primary is toggled enabled
     * @return
     */
    public boolean isPrimaryEnabled() {
        return primaryEnabled;
    }

    /**
     * Changes the state of primary enabled to the opposite of its current state.
     */
    public void primaryToggle(){
        primaryEnabled = !primaryEnabled;
    }
    /**
     * Changes the state of primary enabled to the opposite of its current state based upon user input.
     * @param primaryEnabled
     */
    public void primaryToggle(boolean primaryEnabled){
        this.primaryEnabled = primaryEnabled;
    }

}
