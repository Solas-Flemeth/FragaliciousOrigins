package org.originsreborn.fragaliciousorigins.origins.phytokin;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PlayerUtils;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

import java.util.UUID;

/**
 * Active Ability - Fertility Bloom - Breeds nearby animals and bonemeals nearby plants. Bees Origins will gain saturation when you use this ability
 * Secondary Ability - Adaptation - Scans nearby areas to determine a handful of adaptations.
 *   -You may have 1 type of bark based on dimension
 *     - Sulfuric Bark - Regenerates food from lava slowly and is immune to fire. Exposure to Water can cause the phytokin to randomly explode.  Cannot perform photosynthesis - yellow particle
 *     - Chorus Bark - naturally regenerates food and saturation over time. If you have saturation. Small chance to teleport randomly and remove a small portion of saturation - purple particle
 *     - Oak Bark - Exposure to sunlight or standing directly ontop of a Beacon regenerates food. Phytokin takes 2x fire damage and 3x burn duration, but gains natural toughness - green particle
 *   - Environmental Factors. You can have as many as you like
 *     - Water - Grows larger, and attacks for more damage, but attack slower. No more weakness
 *     - Plants - Grows additional thorns that applies wither on being hit but cannot regen food
 *     - Stone - Moves slower but has bonus toughness
 *  - Peaceful - has constant weakness II, but deals wither for 2s on attack
 *  - Thorns - has poisonous thorns that deal poison damage when attacked
 *  - Bonus Herbalism  + woodcutting XP
 *  - New drops for specific herbalism / wood cutting materials (Golden carrots or Golden Apples, glistening melons)
 *  - Has a chance to drop 2x loot when harvesting plants
 */
public class Phytokin extends Origin {
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.PHYTOKIN);
    public static final PhytokinConfig PHYTOKIN_CONFIG = new PhytokinConfig();
    public Phytokin(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.PHYTOKIN, state, customDataString);
    }

    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }

    @Override
    public void originTick(int tickNum) {
        if(tickNum % PHYTOKIN_CONFIG.getTimePerTick() == 0){
            Player player = getPlayer();
            if(player.getLocation().getBlock().getLightFromSky()== 15){
                PlayerUtils.addSaturation(player, PHYTOKIN_CONFIG.getSaturationPerTick());
                PlayerUtils.addHunger(player, PHYTOKIN_CONFIG.getHungerPerTick());
                PlayerUtils.addExhaustion(player, PHYTOKIN_CONFIG.getExhaustPerTick());
            }
        }
    }

    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateSphereParticle(Particle.CHERRY_LEAVES, getPlayer().getLocation(), 1, 3.5);
    }

    /**
     * @param event
     */
    @Override
    public void consume(PlayerItemConsumeEvent event) {
        super.consume(event);
        if(!event.isCancelled() && !event.getItem().getType().equals(Material.OMINOUS_BOTTLE)){
            event.setCancelled(true);
            getPlayer().sendActionBar(Component.text("You can't eat").color(errorColor()));
        }
    }

    /**
     * @param event
     */
    @Override
    public void onPotionEffect(EntityPotionEffectEvent event) {
        super.onPotionEffect(event);
        if(event.isCancelled()){
            return;
        }
        if(event.getNewEffect().getType().equals(PotionEffectType.POISON) || event.getNewEffect().getType().equals(PotionEffectType.WITHER)){
            event.setCancelled(true);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onPostAttackEntity(EntityDamageByEntityEvent event) {
        super.onPostAttackEntity(event);
        if(event.getEntity() instanceof LivingEntity livingEntity && !event.getEntity().equals(event.getDamager()) && !event.isCancelled()){
            PotionsUtil.addEffect(livingEntity, PotionEffectType.WITHER, PHYTOKIN_CONFIG.getWitherDuration(), PHYTOKIN_CONFIG.getWitherAmplifier());
        }
    }

    /**
     * @param event
     */
    @Override
    public void onPostHurtByEntity(EntityDamageByEntityEvent event) {
        super.onPostHurtByEntity(event);
        if (!event.isCancelled() && event.getDamager() instanceof LivingEntity livingEntity && !event.getEntity().equals(event.getDamager())){
            DamageSource damageSource = DamageSource.builder(DamageType.THORNS).build();
            livingEntity.damage(PHYTOKIN_CONFIG.getThornDamage(), damageSource);
        }
    }

    @Override
    public String serializeCustomData() {
        return "";
    }

    @Override
    public void deserializeCustomData(String customData) {

    }

    @Override
    public void primaryAbilityLogic() {

    }

    @Override
    public void secondaryAbilityLogic() {

    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
    }
}
