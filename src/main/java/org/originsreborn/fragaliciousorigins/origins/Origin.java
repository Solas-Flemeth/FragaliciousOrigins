package org.originsreborn.fragaliciousorigins.origins;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousanomaly.events.DayStateChangeEvent;
import org.originsreborn.fragaliciousanomaly.objects.enums.MoonState;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.intergration.disguiselib.DisguiseLibHook;
import org.originsreborn.fragaliciousorigins.intergration.disguiselib.DisguiseUtil;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static org.bukkit.damage.DamageType.*;
import static org.originsreborn.fragaliciousorigins.util.PlayerUtils.setAttribute;

public abstract class Origin {
    private static final Random RANDOM = new Random();
    private final UUID uuid;
    private final OriginType type;
    private final OriginState state;
    private int primaryCooldown = 0;
    private int secondaryCooldown = 0;
    private boolean primaryEnabled = false;
    private boolean secondaryEnabled = false;
    private int tempTimeRemaining = 0;
    private boolean useSwapHand = true;
    private UUID bondedUUID = null;

    //Initial creation
    public Origin(UUID uuid, @NotNull OriginType type, OriginState state) {
        this.uuid = uuid;
        this.type = type;
        this.state = state;
        setDefaultStats();
        Player player = getPlayer();
        player.sendMessage("You are now a " + getType().getDisplay());
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    public Origin(UUID uuid, @NotNull OriginType type, OriginState state, String customDataString) {
        this.uuid = uuid;
        this.type = type;
        this.state = state;
        if (customDataString != null && !customDataString.isEmpty()) {
            deserializeCustomData(customDataString);
        }
        setDefaultStats();
        Player player = getPlayer();
        player.sendMessage("You are now a " + getType().getDisplay());
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        DisguiseLibHook.undisguisedPlayer(player);
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

    public static Random getRandom() {
        return RANDOM;
    }

    /**
     * Generates a percentage between 0 and 1
     *
     * @param amount
     * @param total
     * @return
     */
    public static float calculatePercentage(int amount, int total) {
        float amountf = amount;
        float totalf = total;
        float percentage = amountf / totalf;
        if (percentage < 0.0) {
            return 0;
        } else if (percentage > 1.0f) {
            return 1.0f;
        } else {
            return percentage;
        }
    }

    /**
     * Generates a percentage between 0 and 1
     *
     * @param amount
     * @param total
     * @return
     */
    public static float calculatePercentage(double amount, double total) {
        float percentage = (float) (amount / total);
        if (percentage < 0.0) {
            return 0;
        } else if (percentage > 1.0f) {
            return 1.0f;
        } else {
            return percentage;
        }
    }

    /**
     * Formats the float as a percentage
     *
     * @param percentage
     * @return
     */
    public static String formatFloatPercentage(float percentage) {
        int formatPercentage = (int) (percentage * 100);
        return formatPercentage + "%";
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

    public void applyExhaustion(){
        MainOriginConfig config = getConfig();
        Player player = getPlayer();
        player.setExhaustion(player.getExhaustion() + config.getExhaustionAdjustment());
    }
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
        player.setGravity(true);
        //config
        setAttribute(player, Attribute.ARMOR, config.getArmor());
        setAttribute(player, Attribute.ARMOR_TOUGHNESS, config.getArmorToughness());
        setAttribute(player, Attribute.ATTACK_DAMAGE, config.getAttackDamage());
        setAttribute(player, Attribute.ATTACK_DAMAGE, config.getAttackDamage());
        setAttribute(player, Attribute.KNOCKBACK_RESISTANCE, config.getKnockbackResistance());
        setAttribute(player, Attribute.LUCK, config.getLuck());
        setAttribute(player, Attribute.MAX_HEALTH, config.getMaxHealth());
        setAttribute(player, Attribute.SCALE, config.getScale());
        setAttribute(player, Attribute.STEP_HEIGHT, config.getStepHeight());
        setAttribute(player, Attribute.JUMP_STRENGTH, config.getJumpStrength());
        setAttribute(player, Attribute.MOVEMENT_SPEED, config.getMovementSpeed());
        setAttribute(player, Attribute.BLOCK_INTERACTION_RANGE, config.getBlockInteractRange());
        setAttribute(player, Attribute.ENTITY_INTERACTION_RANGE, config.getPlayerEntityInteractRange());
        setAttribute(player, Attribute.BLOCK_BREAK_SPEED, config.getBlockBreakSpeed());
        setAttribute(player, Attribute.GRAVITY, config.getGravity());
        setAttribute(player, Attribute.SAFE_FALL_DISTANCE, config.getSafeFallDistance());
        setAttribute(player, Attribute.FALL_DAMAGE_MULTIPLIER, config.getFallDamageMultiplier());
        setAttribute(player, Attribute.BURNING_TIME, config.getBurnDurationMultiplier());
        setAttribute(player, Attribute.EXPLOSION_KNOCKBACK_RESISTANCE, config.getExplosionKnockbackResistance()); // cap at 1
        setAttribute(player, Attribute.MINING_EFFICIENCY, config.getMiningEfficiency());
        setAttribute(player, Attribute.MOVEMENT_EFFICIENCY, config.getMovementEfficiency()); // cap at 1
        setAttribute(player, Attribute.OXYGEN_BONUS, config.getOxygenBonus());
        setAttribute(player, Attribute.SNEAKING_SPEED, config.getSneakingSpeed()); // cap at 1
        setAttribute(player, Attribute.SUBMERGED_MINING_SPEED, config.getSubmergedMiningSpeed());
        setAttribute(player, Attribute.SWEEPING_DAMAGE_RATIO, config.getSweepingDamageRatio()); // cap at 1
        setAttribute(player, Attribute.WATER_MOVEMENT_EFFICIENCY, config.getWaterMovementEfficiency()); // cap at 1
        PlayerUtils.setFlySpeed(player, (float) config.getFlyingSpeed());
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    public void onDeath(PlayerDeathEvent event) {

    }

    @NotNull
    public HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        return map;
    }

    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {
    }

    /**
     * Serializes the data from hashmap generateSerializedHashmap().
     * Do NOT override this method. Please add to the additionalSerializationOfCustomData()
     *
     * @return
     */
    public String serializeCustomData() {
        HashMap<String, Serializable> hashMap = new HashMap<String, Serializable>();
        hashMap.put("primaryCooldown", getPrimaryCooldown());
        hashMap.put("secondaryCooldown", getSecondaryCooldown());
        hashMap.put("primaryEnabled", isPrimaryEnabled());
        hashMap.put("secondaryEnabled", isSecondaryEnabled());
        hashMap.put("timeRemaining", getTempTimeRemaining());
        hashMap.put("bondedUUID", getBondedUUID());
        hashMap.put("useSwapHand", isUseSwapHand());
        try {
            hashMap = additionalSerializationOfCustomData(hashMap);
        } catch (Exception e) {
            FragaliciousOrigins.INSTANCE.getLogger().severe("Error Serializing Custom Data for Origin " + getType().name());
            FragaliciousOrigins.INSTANCE.getLogger().severe("Error:" + e.getMessage());
        }
        try {
            return SerializationUtils.serializeHashMapToString(hashMap);
        } catch (IOException e) {
            FragaliciousOrigins.INSTANCE.getLogger().severe("Error Serializing Default Data for Origin type of " + getType().name());
            FragaliciousOrigins.INSTANCE.getLogger().severe("Error:" + e.getMessage());
            return "";
        }
    }

    /**
     * Deserializes data from a custom string.
     * Do NOT overide this method. Please add to the additionalDeserialization
     *
     * @param customData
     */
    public void deserializeCustomData(String customData) {
        try {
            HashMap<String, Serializable> hashMap = SerializationUtils.unserializeStringToHashMap(customData);
            setPrimaryCooldown((Integer) hashMap.get("primaryCooldown"));
            setSecondaryCooldown((Integer) hashMap.get("secondaryCooldown"));
            primaryToggle((boolean) hashMap.get("primaryEnabled"));
            secondaryToggle((boolean) hashMap.get("secondaryEnabled"));
            setTempTimeRemaining((int) (hashMap.get("timeRemaining")));
            setBondedUUID((UUID) hashMap.get("bondedUUID"));
            setUseSwapHand((boolean) hashMap.get("useSwapHand"));
            try {
                additionalDeserialization(hashMap);
            } catch (Exception e) {
                FragaliciousOrigins.INSTANCE.getLogger().severe("Error Deserializing Custom Data for Origin " + getType().name());
                FragaliciousOrigins.INSTANCE.getLogger().severe("Error:" + e.getMessage());
            }
        } catch (Exception e) {
            FragaliciousOrigins.INSTANCE.getLogger().severe("Error Deserializing Default Data for Origin type of " + getType().name());
            FragaliciousOrigins.INSTANCE.getLogger().severe("Error:" + e.getMessage());
        }
    }

    public void onRemoveOrigin() {

    }
    @NotNull
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
        Player player = getPlayer();
        if (getConfig().getPlaceholdersPrimaryAbilityName().equals("primaryAbility")) {
            playNegativeSound(player);
            player.sendActionBar(Component.text("Your origin does not have a primary ability").color(TextColor.color(errorColor())));
        }
        if (getPrimaryCooldown() > 0) {
            player.sendActionBar(primaryAbilityTimerCooldownMsg());
            playNegativeSound(player);
        } else if (player.getGameMode().equals(GameMode.SURVIVAL) && primaryConditionCheck()) {
            primaryAbilityLogic();
            setPrimaryCooldown(getPrimaryMaxCooldown());
        }
    }

    public boolean primaryConditionCheck() {
        return true;
    }

    public boolean secondaryConditionCheck() {
        return true;
    }

    public abstract void primaryAbilityLogic();

    public void secondaryAbility() {
        Player player = getPlayer();
        if (getConfig().getPlaceholdersSecondaryAbilityName().equals("secondaryAbility")) {
            playNegativeSound(player);
            player.sendActionBar(Component.text("Your origin does not have a secondary ability").color(TextColor.color(errorColor())));
        }
        if (getSecondaryCooldown() > 0) {
            player.sendActionBar(secondaryAbilityTimerCooldownMsg());
            playNegativeSound(player);
        } else if (player.getGameMode().equals(GameMode.SURVIVAL) && primaryConditionCheck()) {
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
            if (primaryCooldown == 0) {
                getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 0.3f);
                getPlayer().sendActionBar(Component.text("Your ability ").color(textColor()).append(Component.text(primaryAbilityName()).color(enableColor())).append(Component.text(" is ready").color(textColor())));
            }
        }
        if (getSecondaryCooldown() > 0) {
            setSecondaryCooldown(getSecondaryCooldown() - 1);
            if (secondaryCooldown == 0) {
                getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 0.3f);
                getPlayer().sendActionBar(Component.text("Your ability ").color(textColor()).append(Component.text(secondaryAbilityName()).color(enableColor())).append(Component.text(" is ready").color(textColor())));
            }
        }
    }

    public int getPrimaryMaxCooldown() {
        return getConfig().getPrimaryMaxCooldown();
    }

    public int getSecondaryMaxCooldown() {
        return getConfig().getSecondaryMaxCooldown();
    }

    public boolean isUseSwapHand() {
        return useSwapHand;
    }

    public void toggleUseSwapHand() {
        this.useSwapHand = !useSwapHand;
    }

    public void setUseSwapHand(boolean useSwapHand) {
        this.useSwapHand = useSwapHand;
    }

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
    }

    public void interactStaticEntity(PlayerInteractAtEntityEvent event) {
    }

    public void interactEntity(PlayerInteractEntityEvent event) {
    }

    public void clickEvent(PlayerInteractEvent event) {
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

    public void onPotionLingering(LingeringPotionSplashEvent event) {
    }

    public void onPotionSplash(PotionSplashEvent event) {

    }

    public void pickupArrow(PlayerPickupArrowEvent event) {
    }

    public void onRespawn(PlayerRespawnEvent event) {
        setDefaultStats();
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

    public void onHungerChange(FoodLevelChangeEvent event) {

    }
    @SuppressWarnings("UnstableApiUsage")
    public void onDamage(EntityDamageEvent event) {
        if (!event.isCancelled()) {
            DamageType damageType = event.getDamageSource().getDamageType();
            if( damageType == IN_FIRE //do nothing if fire or magic
                || damageType == HOT_FLOOR
                || damageType == LAVA
                || damageType == OUTSIDE_BORDER
                || damageType == MAGIC
                || damageType == WITHER
                || damageType == DRAGON_BREATH
                || damageType == OUT_OF_WORLD
                || damageType == CAMPFIRE
                || damageType == INDIRECT_MAGIC
                || damageType == IN_WALL
                || damageType == DROWN
            ){}else{
                    event.setCancelled(Math.random() < getDodgeChance());
            }
            if (event.isCancelled()) {
                getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 2.0f);
            }
            DamageUtil.calculateDamageMultipliers(event, this);
        }
    }

    public void onBowShoot(EntityShootBowEvent event) {

    }

    public void onHurtByEntity(EntityDamageByEntityEvent event) {
        DamageUtil.applySpecialWeaponDamage(event, this);
    }

    public void onAttackEntity(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * getConfig().getMeleeAttackMultiplier());
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

    public void onUpdateArmor(PlayerArmorChangeEvent event) {

    }

    public void onWorldChange(PlayerChangedWorldEvent event) {

    }

    public Component primaryAbilityTimerCooldownMsg() {
        return Component.text("Your ability ").color(textColor())
                .append(Component.text(primaryAbilityName()).color(errorColor()))
                .append(Component.text(" will be ready in ").color(textColor()))
                .append(Component.text(primaryCooldown).color(errorColor()))
                .append(Component.text(" seconds.").color(textColor()));
    }

    public Component secondaryAbilityTimerCooldownMsg() {
        return Component.text("Your ability ").color(textColor())
                .append(Component.text(secondaryAbilityName()).color(errorColor()))
                .append(Component.text(" will be ready in ").color(textColor()))
                .append(Component.text(secondaryCooldown).color(errorColor()))
                .append(Component.text(" seconds.").color(textColor()));
    }

    public Component enablePrimaryAbilityMsg() {
        return Component.text("Your ability ").color(textColor())
                .append(Component.text(primaryAbilityName()).color(enableColor()))
                .append(Component.text(" is now enabled ").color(textColor()));
    }

    public Component disablePrimaryAbilityMsg() {
        return Component.text("Your ability ").color(textColor())
                .append(Component.text(primaryAbilityName()).color(errorColor()))
                .append(Component.text(" is now disabled ").color(textColor()));
    }

    public Component enableSecondaryAbilityMsg() {
        return Component.text("Your ability ").color(textColor())
                .append(Component.text(secondaryAbilityName()).color(enableColor()))
                .append(Component.text(" is now enabled ").color(textColor()));
    }

    public Component disableSecondaryAbilityMsg() {
        return Component.text("Your ability ").color(textColor())
                .append(Component.text(secondaryAbilityName()).color(errorColor()))
                .append(Component.text(" is now disabled ").color(textColor()));
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
        if (this.secondaryEnabled) {
            getPlayer().sendActionBar(enableSecondaryAbilityMsg());
        } else {
            getPlayer().sendActionBar(disableSecondaryAbilityMsg());
        }
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
        if (primaryEnabled) {
            getPlayer().sendActionBar(enablePrimaryAbilityMsg());
        } else {
            getPlayer().sendActionBar(disablePrimaryAbilityMsg());
        }
    }

    /**
     * Changes the state of primary enabled to the opposite of its current state based upon user input.
     *
     * @param primaryEnabled
     */
    public void primaryToggle(boolean primaryEnabled) {
        this.primaryEnabled = primaryEnabled;
    }

    public int getTempTimeRemaining() {
        return tempTimeRemaining;
    }

    public void setTempTimeRemaining(int tempTimeRemaining) {
        if (tempTimeRemaining < 0) {
            tempTimeRemaining = 0;
        }
        this.tempTimeRemaining = tempTimeRemaining;
    }

    /**
     * Gets the players dodge chance
     *
     * @return
     */
    public double getDodgeChance() {
        return getConfig().getDodgeChance();
    }

    public UUID getBondedUUID() {
        return bondedUUID;
    }

    public void setBondedUUID(UUID bonded) {
        this.bondedUUID = bonded;
    }

    public void onEnchant(EnchantItemEvent event) {
    }

    public void onAnvilClick(InventoryClickEvent event) {
    }

    public void onTradeClick(InventoryClickEvent event) {
    }

    public void onOpenInventory(InventoryOpenEvent event) {
    }

    public void onJump(PlayerJumpEvent event) {

    }

    public void onPostHurtByEntity(EntityDamageByEntityEvent event) {
    }

    public void onPostAttackEntity(EntityDamageByEntityEvent event) {
    }

    public void onSculkSensorTrigger(BlockReceiveGameEvent event) {
        if (!getConfig().hasStepSounds()) {
            event.setCancelled(true);
        }
    }

    public void onTimeChange(DayStateChangeEvent dayStateChangeEvent) {
        switch (dayStateChangeEvent.getCurrentState()) {
            case SUNRISE:
                onSunrise(dayStateChangeEvent.getMoonState());
                break;
            case DAY:
                onDay(dayStateChangeEvent.getMoonState());
                break;
            case SUNSET:
                onSunset(dayStateChangeEvent.getMoonState());
                break;
            case NIGHT:
                onNight(dayStateChangeEvent.getMoonState());
                break;
        }
    }

    public void onSunrise(MoonState moonCycle) {
    }

    public void onDay(MoonState moonCycle) {

    }

    public void onSunset(MoonState moonCycle) {

    }

    public void onNight(MoonState moonCycle) {

    }

    public void onBlockbreak(BlockBreakEvent event) {

    }
    public static void playNegativeSound(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
    }
}
