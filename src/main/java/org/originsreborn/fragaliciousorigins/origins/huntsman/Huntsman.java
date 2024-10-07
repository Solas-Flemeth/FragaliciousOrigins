package org.originsreborn.fragaliciousorigins.origins.huntsman;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.enums.Food;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Primary Ability - Fade - Turns invisible and next shot deals 3.5x Damage  and inflicts wither for 2 seconds - will uninvis afterward. Last 6 seconds.
 * Secondary Ability - Toggle arrow Type
 * - Standard - 1.5x Damage
 * - Tracking - 1.1x Damage + Applies glow (10 second)
 * - Stun Arrow - 0.7x Damage - applies slowness II (4 second) +  darkness (1.5s)
 * - Bows - Gains 1.5x Effective range.
 * - Crossbows - penetration - deals bonus toughness damage
 * - Swings slow with melee weapons and deals less damage
 * - Gains a speed boost on taking damage
 * - Cannot use explosive crossbows
 * - Crouching gives invis, slowness, and slowfall
 * - Carnivore
 * - Deals 1.25x Damage to targets in the air and 2.5x to gliding
 * - Bonus archery xp
 */
public class Huntsman extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.HUNTSMAN);
    public static final HuntsmanConfig HUNTSMAN_CONFIG = new HuntsmanConfig();
    private int invisDuration = 0;
    private HuntsmanMode mode;
    private static final Random random = new Random();

    public Huntsman(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.HUNTSMAN, state, customDataString);
        if(mode == null){
            mode = HuntsmanMode.BROADHEAD;
        }
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        HUNTSMAN_CONFIG.loadConfig();
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if (getPlayer().isSneaking()) {
            PotionsUtil.addEffect(player, PotionEffectType.INVISIBILITY, 0, HUNTSMAN_CONFIG.getCrouchDuration());
            PotionsUtil.addEffect(player, PotionEffectType.SLOW_FALLING, 0, HUNTSMAN_CONFIG.getCrouchDuration());
        }
        if (tickNum % 10 == 0) {
            if (invisDuration != 0) {
                if(invisDuration == 1){
                    primaryAbilityAnimation();
                }
                setInvisDuration(invisDuration - 1);
                PotionsUtil.addEffect(player, PotionEffectType.INVISIBILITY, 0, 25);
            }
        }
        if(tickNum % 600 == 0 && Math.random() > 0.2){
            player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ARROW).add(random.nextInt(2)));
            player.sendActionBar(Component.text("You find some arrows on the ground").color(TextColor.color(Origin.textColor())));
        }
    }

    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateSphereParticle(Particle.SPORE_BLOSSOM_AIR, getPlayer().getLocation(), 2, 1.25);
    }



    @Override
    public void primaryAbilityLogic() {
        primaryAbilityAnimation();
        setInvisDuration(HUNTSMAN_CONFIG.getPrimaryAbilityDuration());
        Player player = getPlayer();
        if(player.getEquipment().getItemInMainHand().isEmpty()){
            player.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        }
        player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ARROW).add(random.nextInt(20)+5));
    }

    @Override
    public void secondaryAbilityLogic() {
        toggleMode();
        Player player = getPlayer();
        ParticleUtil.generateSphereParticle(mode.getParticle(), player.getLocation(), 10, 1.5);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1.5f, 1);
        player.sendActionBar(Component.text("You changed your arrow tips to ").color(textColor()).append(Component.text(mode.getMode()).color(mode.getColor())));
    }

    /**
     * @param map
     * @return
     */
    @Override
    public @NotNull HashMap<String, Serializable> additionalSerializationOfCustomData(HashMap<String, Serializable> map) {
        map.put("mode", mode.getMode());
        return map;
    }

    /**
     * @param map
     * @throws Exception
     */
    @Override
    public void additionalDeserialization(HashMap<String, Serializable> map) throws Exception {
        setMode(HuntsmanMode.getMode((String) map.get("mode")));
    }


    @Override
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getProjectile().getType().equals(EntityType.FIREWORK_ROCKET)) {
            event.getEntity().sendActionBar(Component.text("You cannot shoot rockets as " + OriginType.HUNTSMAN.name()).color(errorColor()));
            event.setCancelled(true);
            return;
        }
        if(getInvisDuration() > 0){
            setInvisDuration(0);
            primaryAbilityAnimation();
            PotionsUtil.removeEffect(getPlayer(), PotionEffectType.INVISIBILITY);
            event.getProjectile().setMetadata("huntsman_damage_multiplier", new FixedMetadataValue(FragaliciousOrigins.INSTANCE, "Ability"));
        }else{
            event.getProjectile().setMetadata("huntsman_damage_multiplier", new FixedMetadataValue(FragaliciousOrigins.INSTANCE, mode.getMode()));
            if (event.getBow().getType().equals(Material.BOW)) {
                Vector modifiedVector = event.getProjectile().getVelocity().multiply(HUNTSMAN_CONFIG.getOtherBowVelocityMultiplier());
                event.getProjectile().setVelocity(modifiedVector);
            } else if (event.getBow().getType().equals(Material.CROSSBOW)) {
                event.getProjectile().setMetadata("huntsman_toughness_Multiplier", new FixedMetadataValue(FragaliciousOrigins.INSTANCE, true));
            }
        }
    }
    @Override
    public void onDamage(EntityDamageEvent event){
        if(!event.isCancelled()){
            if(event.getFinalDamage() != 0.0){
                PotionsUtil.addEffect(getPlayer(), PotionEffectType.SPEED, HUNTSMAN_CONFIG.getSpeedOnDamageAmplifier(), HUNTSMAN_CONFIG.getSpeedOnDamageDuration());
            }
        }
    }

    @Override
    public void consume(PlayerItemConsumeEvent event){
        Food food = Food.getFood(event.getItem().getType());
        if(food != null && !food.isMeat()
                && !food.getType().equals(Material.GLOW_BERRIES)
                && !food.getType().equals(Material.SWEET_BERRIES)
                && !food.getType().equals(Material.MUSHROOM_STEW)
        ){
            event.getPlayer().sendActionBar(Component.text("Yuck! You can only eat meat and berries").color(errorColor()));
            event.setCancelled(true);
        }
    }
    public int getInvisDuration() {
        return invisDuration;
    }

    public void setInvisDuration(int invisDuration) {
        if (invisDuration < 0) {
            invisDuration = 0;
        }
        this.invisDuration = invisDuration;
    }


    public void setMode(HuntsmanMode mode) {
        this.mode = mode;
    }

    public void toggleMode() {
        switch (mode) {
            case STUN -> setMode(HuntsmanMode.BROADHEAD);
            case BROADHEAD -> setMode(HuntsmanMode.CUPID);
            default -> setMode(HuntsmanMode.STUN);
        }
    }
    public static double getDamageMultiplier(String mode){
        return switch (mode) {
            case "Stun" -> HUNTSMAN_CONFIG.getStunArrowDamageMultiplier();
            case "Ability" -> HUNTSMAN_CONFIG.getPrimaryAbilityDamageMultiplier();
            default -> HUNTSMAN_CONFIG.getBroadArrowDamageMultiplier();
        };
    }
    private void primaryAbilityAnimation(){
        getPlayer().getWorld().playSound(getPlayer().getLocation(),Sound.ENTITY_HORSE_BREATHE, 1, 0.5f);
        ParticleUtil.generateSphereParticle(Particle.CAMPFIRE_COSY_SMOKE, getPlayer().getEyeLocation(),60,1.5);
    }

}
