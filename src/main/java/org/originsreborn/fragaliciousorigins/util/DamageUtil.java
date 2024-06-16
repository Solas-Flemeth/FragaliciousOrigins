package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.alchemist.Alchemist;
import org.originsreborn.fragaliciousorigins.origins.huntsman.Huntsman;

import java.util.List;

public class DamageUtil {
    /**
     * Calculate Damage Taken by
     *
     * @param event
     */
    public static void applySpecialWeaponDamage(EntityDamageByEntityEvent event, Origin origin) {
        boolean sharpnessImmune = origin.getConfig().getSharpnessImmune();
        boolean smiteImmune = origin.getConfig().getSmiteImmune();
        boolean baneOfArthapodsImmune = origin.getConfig().getBaneOfArthopodsImmune();
        double damage = event.getDamage();
        if (event.getDamager() instanceof LivingEntity && ((LivingEntity) event.getDamager()).getEquipment() != null) {
            EntityEquipment equipment = ((LivingEntity) event.getDamager()).getEquipment();
            ItemStack item = equipment.getItemInMainHand();
            if (item.containsEnchantment(Enchantment.SHARPNESS) && sharpnessImmune) {
                int level = item.getItemMeta().getEnchantLevel(Enchantment.SHARPNESS);
                double damageModifier = 0.5 * (1.0 + level);
                event.setDamage(damage - damageModifier);
            }
            if (item.containsEnchantment(Enchantment.SMITE) && !smiteImmune) {
                int level = item.getItemMeta().getEnchantLevel(Enchantment.SMITE);
                double damageModifier = 0.5 * (1.0 * level);
                event.setDamage(damage + damageModifier);
            }
            if (item.containsEnchantment(Enchantment.BANE_OF_ARTHROPODS) && !baneOfArthapodsImmune) {
                int level = item.getItemMeta().getEnchantLevel(Enchantment.BANE_OF_ARTHROPODS);
                double damageModifier = 0.5 * (1.0 * level);
                event.setDamage(damage + damageModifier);
            }
            if(item.getType().equals(Material.TRIDENT)){
                event.setDamage(damage * origin.getConfig().getWaterDamageMultiplier());
            }
        }
        if(event.getDamager().getType().equals(EntityType.TRIDENT)){
            event.setDamage(damage * origin.getConfig().getWaterDamageMultiplier());
        }
    }

    public static void onSpecialDamageEvents(EntityDamageByEntityEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && event.getEntity() instanceof LivingEntity livingEntity) {
            if (event.getDamager() instanceof Projectile arrow) {
                onArrowEvent(event, livingEntity, arrow);
            }
        }
        if(event.getDamager() instanceof Firework firework && firework.getShooter() instanceof Player player){
            Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId());
            if(origin != null && origin instanceof Alchemist && firework.isGlowing()){
                event.setDamage(event.getDamage() * Alchemist.ALCHEMIST_CONFIG.getExplosionDamageModifier());
            }
        }
    }

    public static void calculateDamageMultipliers(EntityDamageEvent event, Origin origin) {
        double damage = event.getDamage();
        switch (event.getCause()) {
            //Melee
            case CONTACT:
            case THORNS:
            case ENTITY_ATTACK:
            case ENTITY_SWEEP_ATTACK:
                event.setDamage(damage * origin.getConfig().getMeleeDamageMultiplier());
                break;
            //Projectile
            case PROJECTILE:
                event.setDamage(damage * origin.getConfig().getProjectileDamageMultiplier());
                break;
            //explosions
            case BLOCK_EXPLOSION:
            case ENTITY_EXPLOSION:
            case SONIC_BOOM:
                event.setDamage(damage * origin.getConfig().getExplosionDamageMultiplier());
                break;
            //fire
            case FIRE:
            case FIRE_TICK:
            case LAVA:
            case HOT_FLOOR:
            case LIGHTNING:
                event.setDamage(damage * origin.getConfig().getFireDamageMultiplier());
                break;
            //water
            case DROWNING:
            case DRYOUT:
            case FREEZE:
                event.setDamage(damage * origin.getConfig().getWaterDamageMultiplier());
                //MAGIC
            case MAGIC:
            case POISON:
            case WITHER:
            case DRAGON_BREATH:
                event.setDamage(damage * origin.getConfig().getMagicDamageMultiplier());
                break;
        }
    }

    private static void onArrowEvent(EntityDamageByEntityEvent event, LivingEntity livingEntity, Projectile arrow) {
        double crossbowToughnessMultiplier = 1.0;
        double damageMultiplier = 1.0;
        double airMultiplier = 1.0;
        boolean heal = false;
        Location location = livingEntity.getLocation();
        location.setY(location.y() - 1.0);
        List<MetadataValue> list = arrow.getMetadata("huntsman_damage_multiplier");
        if (!list.isEmpty()) {
            if (livingEntity.isGliding()) {
                airMultiplier = Huntsman.HUNTSMAN_CONFIG.getAerialEnemyGlideMultiplier();
            }
            String mode = list.get(0).asString();
            damageMultiplier = Huntsman.getDamageMultiplier(mode);
            if(mode.equals("Cupid")) {
                heal = true;
            } else if(mode.equals("Stun")){
                if(Huntsman.HUNTSMAN_CONFIG.getStunArrowMiningFatigueEnabled()) {
                    PotionsUtil.addEffect(livingEntity, PotionEffectType.MINING_FATIGUE, Huntsman.HUNTSMAN_CONFIG.getStunArrowMiningFatigueAmplifier() , Huntsman.HUNTSMAN_CONFIG.getStunArrowMiningFatigueDuration());
                }
                if(Huntsman.HUNTSMAN_CONFIG.getStunArrowDarknessEnabled()){
                    PotionsUtil.addEffect(livingEntity, PotionEffectType.DARKNESS, Huntsman.HUNTSMAN_CONFIG.getStunArrowDarknessAmplifier() , Huntsman.HUNTSMAN_CONFIG.getStunArrowDarknessDuration());
                }
                if(Huntsman.HUNTSMAN_CONFIG.getStunArrowSlownessEnabled()){
                    PotionsUtil.addEffect(livingEntity, PotionEffectType.SLOWNESS, Huntsman.HUNTSMAN_CONFIG.getStunArrowSlownessAmplifier() , Huntsman.HUNTSMAN_CONFIG.getStunArrowSlownessDuration());
                }
            }
        }
        list = arrow.getMetadata("huntsman_toughness_multiplier");
        if (!list.isEmpty()) {
            crossbowToughnessMultiplier = 1.0 + (Huntsman.HUNTSMAN_CONFIG.getCrossbowDamageMultiplierPerToughness() * PlayerUtils.getAttribute(livingEntity, Attribute.GENERIC_ARMOR_TOUGHNESS));
        }
        double newDamage = event.getDamage() * damageMultiplier * airMultiplier * crossbowToughnessMultiplier;
        if(heal && event.getEntity() instanceof LivingEntity target){
            double healthMultiplier = Huntsman.HUNTSMAN_CONFIG.getHealMultiplier();
            double totalHeal = healthMultiplier * damageMultiplier;
            target.heal(totalHeal);
            int duration = 1 + ((int) newDamage * Huntsman.HUNTSMAN_CONFIG.getStrengthTicksPerDamage());
            PotionsUtil.addEffect(target, PotionEffectType.STRENGTH,Huntsman.HUNTSMAN_CONFIG.getStrengthAmplifier(), duration);
            ParticleUtil.generateParticleAtLocation(Particle.HEART, livingEntity.getLocation(), 6);
            event.setDamage(0.01);
        }else{
            event.setDamage(newDamage);
        }

    }
}
