package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.originsreborn.fragaliciousorigins.origins.Origin;

public class DamageUtil {
    /**
     * Calculate Damage Taken by
     *
     * @param event
     */
    public static void applyEnchantmentImmunities(EntityDamageByEntityEvent event, Origin origin) {
        boolean sharpnessImmune = origin.getConfig().getSharpnessImmune();
        boolean smiteImmune = origin.getConfig().getSmiteImmune();
        boolean baneOfArthapodsImmune = origin.getConfig().getBaneOfArthopodsImmune();
        if (!sharpnessImmune && smiteImmune && baneOfArthapodsImmune) { //does not need modified
            return;
        }
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
                double damageModifier = 0.5 * (1.0 *  level);
                event.setDamage(damage + damageModifier);
            }
            if (item.containsEnchantment(Enchantment.BANE_OF_ARTHROPODS) && !baneOfArthapodsImmune) {
                int level = item.getItemMeta().getEnchantLevel(Enchantment.BANE_OF_ARTHROPODS);
                double damageModifier = 0.5 * (1.0 *  level);
                event.setDamage(damage + damageModifier);
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

}
