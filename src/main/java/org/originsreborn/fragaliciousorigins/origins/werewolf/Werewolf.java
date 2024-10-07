package org.originsreborn.fragaliciousorigins.origins.werewolf;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.DisguiseUtil;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PlayerUtils;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.enums.Food;
import org.originsreborn.fragaliciousorigins.util.enums.MoonCycle;
import org.originsreborn.fragaliciousorigins.util.enums.PremadeDisguise;

import java.util.Objects;
import java.util.UUID;

import static org.originsreborn.fragaliciousorigins.util.enums.MoonCycle.*;

public class Werewolf extends Origin {
    public static final MainOriginConfig WEREWOLF_HUMAN_FORM_CONFIG = new MainOriginConfig(OriginType.WEREWOLF, "humanForm");
    public static final MainOriginConfig WEREWOLF_WOLF_FORM_CONFIG = new MainOriginConfig(OriginType.WEREWOLF, "wolfForm");
    public static final WerewolfConfig WEREWOLF_CONFIG = new WerewolfConfig();

    //wolf form  = secondaryEnabled = true  - Not wolfform = secondaryEnabled = false
    public Werewolf(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.WEREWOLF, state, customDataString);
        if (isSecondaryEnabled()) {
            setDefaultStats();
            DisguiseUtil.disguisePlayer(getPlayer(), PremadeDisguise.WEREWOLF);
        } else {
            DisguiseUtil.undisguisedPlayer(getPlayer());
        }
    }

    public static void onReload() {
        WEREWOLF_HUMAN_FORM_CONFIG.loadConfig();
        WEREWOLF_WOLF_FORM_CONFIG.loadConfig();
        WEREWOLF_CONFIG.loadConfig();
    }

    /**
     * @return
     */
    @Override
    public MainOriginConfig getConfig() {
        if (isSecondaryEnabled()) {
            return WEREWOLF_WOLF_FORM_CONFIG;
        } else {
            return WEREWOLF_HUMAN_FORM_CONFIG;
        }
    }

    /**
     *
     */
    @Override
    public void onRemoveOrigin() {
        setDefaultStats();
        DisguiseUtil.undisguisedPlayer(getPlayer());
        super.onRemoveOrigin();

    }

    /**
     *
     */
    @Override
    public void setDefaultStats() {
        updateArmor();
        if (isSecondaryEnabled()) {
            super.setDefaultStats();
            DisguiseUtil.undisguisedPlayer(getPlayer());
            DisguiseUtil.disguisePlayer(getPlayer(), PremadeDisguise.WEREWOLF);
        } else {
            super.setDefaultStats();
            DisguiseUtil.undisguisedPlayer(getPlayer());
        }
    }

    /**
     * @param tickNum
     */
    @Override
    public void originTick(int tickNum) {
        if (isSecondaryEnabled()) {
            if (tickNum % 20 == 0) {
                PotionsUtil.addEffect(getPlayer(), PotionEffectType.NIGHT_VISION, 0, 300);
            }
            if(getPlayer().isSneaking()){
                PotionsUtil.addEffect(getPlayer(),PotionEffectType.JUMP_BOOST, 2, 4);
            }
        }
    }

    /**
     * @param tickNum
     */
    @Override
    public void originParticle(int tickNum) {
        if (isSecondaryEnabled()) {
            Particle.DustTransition dustTransition = ParticleUtil.dustTransition(0xFFFFFF, 0x000000, 0.5f);
            ParticleUtil.generateParticleAtLocation(Particle.DUST, dustTransition, getPlayer().getLocation(), 1);
            if (tickNum % 2 == 0) {
                ParticleUtil.generateParticleAtLocation(Particle.ASH, getPlayer().getLocation(), 1);
            } else {
                ParticleUtil.generateParticleAtLocation(Particle.WHITE_SMOKE, getPlayer().getLocation(), 1);
            }
        }
    }

    /**
     *
     */
    @Override
    public void primaryAbilityLogic() {
        if (isSecondaryEnabled()) {
            primaryAbilityWerewolf();
        } else {
            primaryAbilityHuman();
        }
    }

    private void primaryAbilityHuman() {
        Player player = getPlayer();
        Location playerLocation = player.getLocation();
        player.getWorld().playSound(playerLocation, Sound.ENTITY_WOLF_HOWL, 1f, 1f);
        Wolf wolf = (Wolf) player.getWorld().spawnEntity(playerLocation, EntityType.WOLF, true);
        wolf.setOwner(player);
        PlayerUtils.setAttribute(wolf, Attribute.GENERIC_MAX_HEALTH, 40f);
        PlayerUtils.setAttribute(wolf, Attribute.GENERIC_SCALE, 2f);
        PotionsUtil.addEffect(wolf, PotionEffectType.REGENERATION, 1);

    }

    private void primaryAbilityWerewolf() {
        Player player = getPlayer();
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WOLF_HOWL, 3f, 0.3f);
        player.getNearbyEntities(WEREWOLF_CONFIG.getRadius(), WEREWOLF_CONFIG.getRadius(), WEREWOLF_CONFIG.getRadius()).forEach(entity -> {
            if (entity instanceof Player entityPlayer) {
                if (FragaliciousOrigins.ORIGINS.getOrigin(entityPlayer.getUniqueId()) instanceof Werewolf) {
                    PotionsUtil.addEffect(entityPlayer, PotionEffectType.STRENGTH, WEREWOLF_CONFIG.getPlayerStrengthAmplifier(), WEREWOLF_CONFIG.getPlayerStrengthDuration());
                    PotionsUtil.addEffect(entityPlayer, PotionEffectType.REGENERATION, WEREWOLF_CONFIG.getPlayerRegenerationAmplifier(), WEREWOLF_CONFIG.getPlayerRegenerationDuration());
                    PotionsUtil.addEffect(entityPlayer, PotionEffectType.RESISTANCE, WEREWOLF_CONFIG.getPlayerResistanceAmplifier(), WEREWOLF_CONFIG.getPlayerResistanceDuration());
                    PotionsUtil.addEffect(entityPlayer, PotionEffectType.SPEED, WEREWOLF_CONFIG.getPlayerSpeedAmplifier(), WEREWOLF_CONFIG.getPlayerSpeedDuration());
                }
            } else if (entity instanceof Wolf wolf && Objects.equals(wolf.getOwner(), player)) {
                PotionsUtil.addEffect(wolf, PotionEffectType.STRENGTH, WEREWOLF_CONFIG.getWolfStrengthAmplifier(), WEREWOLF_CONFIG.getWolfStrengthDuration());
                PotionsUtil.addEffect(wolf, PotionEffectType.RESISTANCE, WEREWOLF_CONFIG.getWolfResistanceAmplifier(), WEREWOLF_CONFIG.getWolfResistanceDuration());
                PotionsUtil.addEffect(wolf, PotionEffectType.SPEED, WEREWOLF_CONFIG.getWolfSpeedAmplifier(), WEREWOLF_CONFIG.getWolfSpeedDuration());
            }
        });
        PotionsUtil.addEffect(player, PotionEffectType.STRENGTH, WEREWOLF_CONFIG.getPlayerStrengthAmplifier(), WEREWOLF_CONFIG.getPlayerStrengthDuration());
        PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, WEREWOLF_CONFIG.getPlayerRegenerationAmplifier(), WEREWOLF_CONFIG.getPlayerRegenerationDuration());
        PotionsUtil.addEffect(player, PotionEffectType.RESISTANCE, WEREWOLF_CONFIG.getPlayerResistanceAmplifier(), WEREWOLF_CONFIG.getPlayerResistanceDuration());
        PotionsUtil.addEffect(player, PotionEffectType.SPEED, WEREWOLF_CONFIG.getPlayerSpeedAmplifier(), WEREWOLF_CONFIG.getPlayerSpeedDuration());
    }

    /**
     * Update the players wolf armor
     */
    private void updateArmor() {
        Player player = getPlayer();
        if(isSecondaryEnabled()){
            PlayerUtils.setAttribute(player, Attribute.GENERIC_ATTACK_SPEED, WEREWOLF_HUMAN_FORM_CONFIG.getAttackSpeed());
            PlayerUtils.setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, WEREWOLF_HUMAN_FORM_CONFIG.getMovementSpeed());
            return;
        }
        double toughness = PlayerUtils.getAttribute(player, Attribute.GENERIC_ARMOR_TOUGHNESS);
        double armor = PlayerUtils.getAttribute(player, Attribute.GENERIC_ARMOR);
        double configAttackSpeed = WEREWOLF_WOLF_FORM_CONFIG.getAttackSpeed();
        double configMovementSpeed = WEREWOLF_WOLF_FORM_CONFIG.getMovementSpeed();
        double newAttackSpeed = 1.0 - (WEREWOLF_CONFIG.getAttackPenaltyArmor() * armor + toughness * WEREWOLF_CONFIG.getAttackPenaltyToughness());
        double newMovementSpeed = 1.0 - (WEREWOLF_CONFIG.getSpeedPenaltyArmor() * armor + toughness * WEREWOLF_CONFIG.getSpeedPenaltyToughness());
        if (newAttackSpeed < 0.1) {
            newAttackSpeed = 0.1;
        }
        if (newMovementSpeed < 0.1) {
            newMovementSpeed = 0.1;
        }
        PlayerUtils.setAttribute(player, Attribute.GENERIC_ATTACK_SPEED, configAttackSpeed * newAttackSpeed);
        PlayerUtils.setAttribute(player, Attribute.GENERIC_MOVEMENT_SPEED, configMovementSpeed * newMovementSpeed);
    }

    /**
     * @param event
     */
    @Override
    public void onShear(PlayerShearEntityEvent event) {
        super.onShear(event);
        if (isSecondaryEnabled()) {
            event.setCancelled(true);
            cannotDoActivity("use shears");
        }
    }

    /**
     * @param event
     */
    @Override
    public void onBowShoot(EntityShootBowEvent event) {
        super.onBowShoot(event);
        if (isSecondaryEnabled()) {
            event.setCancelled(true);
            cannotDoActivity("use bows");
        }
    }

    /**
     * @param event
     */
    @Override
    public void onTridentLaunch(ProjectileLaunchEvent event) {
        super.onTridentLaunch(event);
        if (isSecondaryEnabled()) {
            event.setCancelled(true);
            cannotDoActivity("use tridents");
        }
    }

    /**
     * @param event
     */
    @Override
    public void onSnowballLaunch(ProjectileLaunchEvent event) {
        super.onSnowballLaunch(event);
        if (isSecondaryEnabled()) {
            event.setCancelled(true);
            cannotDoActivity("use snowballs");
        }
    }

    /**
     * @param event
     */
    @Override
    public void onRocketLaunch(ProjectileLaunchEvent event) {
        super.onRocketLaunch(event);
        if (isSecondaryEnabled()) {
            event.setCancelled(true);
            cannotDoActivity("use rockets");
        }
    }

    /**
     * @param event
     */
    @Override
    public void onUpdateArmor(PlayerArmorChangeEvent event) {
        super.onUpdateArmor(event);
        updateArmor();
    }

    /**
     * @param event
     */
    @Override
    public void onBlockbreak(BlockBreakEvent event) {
        super.onBlockbreak(event);
        boolean isHandEmpty = getPlayer().getEquipment().getItemInMainHand().isEmpty();
        if (isSecondaryEnabled()) {
            if (!isHandEmpty) {
                event.setCancelled(true);
                cannotDoActivity("use tools");
            }
        }
    }

    @Override
    public void consume(PlayerItemConsumeEvent event) {
        super.consume(event);
        Food food = Food.getFood(event.getItem().getType());
        if (food != null && !food.isMeat()) {
            event.getPlayer().sendActionBar(Component.text("Yuck! You can only eat meat").color(errorColor()));
            event.setCancelled(true);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onAttackEntity(EntityDamageByEntityEvent event) {
        //verifies not a living entity
        if (!(event.getEntity() instanceof LivingEntity)) {
            super.onAttackEntity(event);
            return;
        }
        boolean isHandEmpty = getPlayer().getEquipment().getItemInMainHand().isEmpty();
        LivingEntity entity = (LivingEntity) event.getEntity();
        Player player = getPlayer();
        //calculate enemy gear
        double armor = PlayerUtils.getAttribute(entity, Attribute.GENERIC_ARMOR);
        double toughness = PlayerUtils.getAttribute(entity, Attribute.GENERIC_ARMOR_TOUGHNESS);
        //do logic for damage
        if (isSecondaryEnabled()) {
            //deal werewolf damage
            if (isHandEmpty) {
                double damage = WEREWOLF_CONFIG.getWolfBaseMelee()
                        * (1.0 + (WEREWOLF_CONFIG.getWolfDamageMultiplierPerArmour() * armor))
                        * (1.0 + (toughness * WEREWOLF_CONFIG.getWolfDamageMultiplierPerToughness()));
                event.setDamage(damage);
                if (Math.random() < 0.05) {
                    player.setSaturation(player.getSaturation() + 4f);
                    player.setFoodLevel(getPlayer().getFoodLevel() + 1);
                }
                if (Math.random() < 0.05) {
                    player.heal(1f);
                    PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, 2, 15);
                }
            } else {
                event.setCancelled(true);
                cannotDoActivity("attack with items");
            }
        } else if (isHandEmpty) {
            double damage = WEREWOLF_CONFIG.getHumanBaseMelee()
                    * (1.0 + (WEREWOLF_CONFIG.getHumanDamageMultiplierPerArmour() * armor))
                    * (1.0 + (toughness * WEREWOLF_CONFIG.getHumanDamageMultiplierPerToughness()));
            event.setDamage(damage);
            if (Math.random() < 0.015) {
                player.setSaturation(player.getSaturation() + 4f);
                player.setFoodLevel(getPlayer().getFoodLevel() + 1);
            }
            if (Math.random() < 0.015) {
                player.heal(1f);
                PotionsUtil.addEffect(player, PotionEffectType.REGENERATION, 2, 15);
            }
        }
        super.onAttackEntity(event);
    }

    /**
     * @param event
     */
    @Override
    public void onHurtByEntity(EntityDamageByEntityEvent event) {
        super.onHurtByEntity(event);
        if (isSecondaryEnabled() && !event.isCancelled()) {
            Player player = getPlayer();
            double armor = PlayerUtils.getAttribute(player, Attribute.GENERIC_ARMOR);
            double toughness = PlayerUtils.getAttribute(player, Attribute.GENERIC_ARMOR_TOUGHNESS);
            double damage = event.getDamage()
                    * (1.0 + (WEREWOLF_CONFIG.getDamagePenaltyArmor() * armor))
                    * (1.0 + (toughness * WEREWOLF_CONFIG.getDamagePenaltyToughness()));
            event.setDamage(damage);
        }
    }

    /**
     * @param moonCycle
     */
    @Override
    public void onSunrise(MoonCycle moonCycle) {
        super.onSunrise(moonCycle);
        if (getRandom().nextDouble() < MoonCycle.getPercentageTillFull(moonCycle) && isSecondaryEnabled()) {
            transform();
            getPlayer().sendActionBar(Component.text("The rise of the sun caused you to transform back to human form").color(Origin.textColor()));
        }
    }

    /**
     * @param moonCycle
     */
    @Override
    public void onSunset(MoonCycle moonCycle) {
        super.onSunset(moonCycle);
        if (getRandom().nextDouble() < MoonCycle.getPercentageTillFull(moonCycle) && !isSecondaryEnabled()) {
            transform();
            getPlayer().sendActionBar(Component.text("The moon causes you to turn back to human form").color(Origin.textColor()));
        }
    }

    /**
     * @return
     */
    @Override
    public boolean secondaryConditionCheck() {
        Player player = getPlayer();
        MoonCycle moonCycle = getMoonCycle(player.getWorld());
        if (moonCycle == FULL_MOON || moonCycle == NEW_MOON) {
            if (moonCycle == FULL_MOON) {
                player.sendActionBar(Component.text("You cannot control transformation during a Full Moon").color(Origin.textColor()));
            } else {
                player.sendActionBar(Component.text("You cannot control transformation during a New Moon").color(Origin.textColor()));
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
            return false;
        }
        return super.secondaryConditionCheck();
    }

    private void transform() {
        secondaryToggle();
        if (isSecondaryEnabled()) {
            DisguiseUtil.disguisePlayer(getPlayer(), PremadeDisguise.WEREWOLF);
            getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_WOLF_HOWL, 1f, 0.5f);
        } else {
            getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.3f);
            DisguiseUtil.undisguisedPlayer(getPlayer());
        }
        setDefaultStats();
    }

    /**
     *
     */
    @Override
    public void secondaryAbilityLogic() {
        transform();
    }

    private void cannotDoActivity(String action) {
        Player player = getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
        player.sendActionBar(Component.text("You cannot " + action + " while transformed").color(TextColor.color(errorColor())));
    }
}
