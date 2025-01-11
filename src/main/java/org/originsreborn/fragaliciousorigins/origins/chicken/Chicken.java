package org.originsreborn.fragaliciousorigins.origins.chicken;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.MobsUtil;
import org.originsreborn.fragaliciousorigins.util.ParticleUtil;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;
import org.originsreborn.fragaliciousorigins.util.enums.Food;

import java.util.Random;
import java.util.UUID;

public class Chicken extends Origin {
    /*
    Lucky Eggs: Can lay an egg, but a chance for it to be something bad
    SECONDARY: Explosive Egg - Lays an egg that explodes where you are. Causes knockback to all nearby including you.
    Crouch: Gain slowfall
    Passive 1: Herbivore - can only eat vegtables
    Passive 2: Cluckin Fast - can run extremely fast
    Passive 3: Hard Shells - Eggs you throw deal 1 damage
    Passive 4: Egg Popper - lay eggs randomly
     */
    //herbivore - only eats vegtables
    public static final MainOriginConfig MAIN_ORIGIN_CONFIG = new MainOriginConfig(OriginType.CHICKEN);
    public static final ChickenConfig CHICKEN_CONFIG = new ChickenConfig();
    private static final Random random = new Random();
    public Chicken(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.CHICKEN, state, customDataString);
    }

    /**
     * @return
     */
    @Override
    public MainOriginConfig getConfig() {
        return MAIN_ORIGIN_CONFIG;
    }


    /**
     * @param tickNum
     */
    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if(player.isSneaking()){
            PotionsUtil.addEffect(player, PotionEffectType.SLOW_FALLING, 1, 4);
        }
        if(tickNum%100 == 0 && CHICKEN_CONFIG.getEggSpawnChance() > Math.random()){
            player.getInventory().addItem(new ItemStack(Material.EGG));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 0.5f);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onShootProjectileHit(ProjectileHitEvent event) {
        super.onShootProjectileHit(event);
        if(event.getEntity() instanceof Egg egg){
            if(Math.random() < CHICKEN_CONFIG.getSpecialEggChance()){
                Location hitLocation = event.getEntity().getLocation();
                WindCharge windCharge = hitLocation.getWorld().spawn(hitLocation, WindCharge.class);
                egg.addPassenger(windCharge);
            }
            if (event.getHitEntity() instanceof LivingEntity target) {
                target.damage(CHICKEN_CONFIG.getEggDamage(), getPlayer());
            }
        }
    }

    /**
     * @param tickNum
     */
    @Override
    public void originParticle(int tickNum) {
        ParticleUtil.generateSphereParticle(Particle.DUST, ParticleUtil.dustOptions(0xffffff, 1), getPlayer().getLocation(), 1, 2.0);
    }

    /**
     *
     */
    @Override
    public void primaryAbilityLogic() {
        generateRandomBird();
    }

    /**
     *
     */
    @Override
    public void secondaryAbilityLogic() {

    }

    @Override
    public void consume(PlayerItemConsumeEvent event){
        super.consume(event);
        Food food = Food.getFood(event.getItem().getType());
        Player player = getPlayer();
        if(food != null && !food.isPlant()){
            player.sendActionBar(Component.text("Yuck! You can only eat plants").color(errorColor()));
            event.setCancelled(true);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_HURT, 1, 0.5f);
        }
    }

    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        CHICKEN_CONFIG.loadConfig();
    }
    private void generateRandomBird() {
        Player player = getPlayer();
        double chance = Math.random();
        if(chance < CHICKEN_CONFIG.getBossEggChance()){
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_DEATH, 3.5f, 0.3f);
            player.sendActionBar(Component.text("Your egg feels extremely dangerous").color(Origin.errorColor()));
            ParticleUtil.generateSphereParticle(Particle.DUST, ParticleUtil.dustOptions(0xFFE100, 2), getPlayer().getLocation(), 30, 3.5);
            spawnBoss(player);
        }else if(chance < CHICKEN_CONFIG.getBossEggChance() + CHICKEN_CONFIG.getHostileEggChance()){
            player.sendActionBar(Component.text("You do not feel safe near your egg").color(Origin.errorColor()));
            ParticleUtil.generateSphereParticle(Particle.DUST, ParticleUtil.dustOptions(0x350000, 1), getPlayer().getLocation(), 20, 2.5);
            spawnHostiles(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2f, 0.3f);
        }else if(chance < CHICKEN_CONFIG.getBossEggChance() + CHICKEN_CONFIG.getHostileEggChance() + CHICKEN_CONFIG.getSpecialEggChance()){
            player.sendActionBar(Component.text("You sense good fortune with your egg").color(Origin.enableColor()));
            ParticleUtil.generateSphereParticle(Particle.DUST, ParticleUtil.dustOptions(0x46A546, 1), getPlayer().getLocation(), 20, 2.5);
            spawnSpecial(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_AMBIENT, 2f, 1.5f);
        }else{
            player.sendActionBar(Component.text("You sense a happy egg").color(Origin.textColor()));
            spawnPassiveCommon(player);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_AMBIENT, 1f, 0.7f);
            ParticleUtil.generateSphereParticle(Particle.DUST, ParticleUtil.dustOptions(0xBDB08A, 1), getPlayer().getLocation(), 20, 2.5);
        }
    }
    private void spawnPassiveCommon(Player player){
        switch (random.nextInt(99)) {
            case 0:
                MobsUtil.spawnParrot(player, Parrot.Variant.BLUE);
                break;
            case 1:
                MobsUtil.spawnParrot(player, Parrot.Variant.GRAY);
                break;
            case 2:
                MobsUtil.spawnParrot(player, Parrot.Variant.RED);
                break;
            case 3:
                MobsUtil.spawnParrot(player, Parrot.Variant.CYAN);
                break;
            case 4:
                MobsUtil.spawnParrot(player, Parrot.Variant.GREEN);
                break;
            case 5:
                MobsUtil.spawnChicken(player);
                break;
            case 6:
                MobsUtil.spawnHorse(player);
                break;
            case 7:
                MobsUtil.spawnCat(player);
                break;
            case 8:
                MobsUtil.spawnWolf(player);
                break;
            case 9:
                MobsUtil.spawnSheep(player);
                break;
            case 10:
                MobsUtil.spawnCow(player);
                break;
            case 11:
                MobsUtil.spawnPig(player);
                break;
            case 12:
                MobsUtil.spawnSalmon(player);
                break;
            case 13:
                MobsUtil.spawnCod(player);
                break;
            case 14:
                MobsUtil.spawnPufferfish(player);
                break;
            case 15:
                MobsUtil.spawnTropicalFish(player);
                break;
            case 16:
                MobsUtil.spawnTropicalFish(player);
                break;
            default:
                MobsUtil.spawnFox(player);
                break;
        }
    }
    private void spawnBoss(Player player){
        switch (random.nextInt(11)){
            case 1:
            case 2:
                MobsUtil.spawnShulker(player);
                break;
            case 4:
            case 5:
                MobsUtil.spawnBreeze(player);
                break;
            case 6:
                MobsUtil.spawnWarden(player);
                break;
            case 7:
                MobsUtil.spawnWither(player);
                break;
            case 8:
                MobsUtil.spawnElderGuardian(player);
                break;
            case 9:
            case 10:
                MobsUtil.spawnKillerRabbit(player);
                break;
        }
    }
    private void spawnSpecial (Player player){
        switch (random.nextInt(8)){
            case 0:
                MobsUtil.spawnAxolotl(player);
                break;
            case 1:
                MobsUtil.spawnBear(player);
                break;
            case 2:
                MobsUtil.spawnPanda(player);
                break;
            case 3:
                MobsUtil.spawnCamel(player);
                break;
            case 4:
                MobsUtil.spawnWanderingTrader(player);
                break;
            case 5:
                MobsUtil.spawnStrider(player);
                break;
            case 6:
                MobsUtil.spawnFox(player);
                break;
            default:
                MobsUtil.spawnLlama(player);
                break;
        }
    }
    private void spawnHostiles(Player player){
        switch (random.nextInt(8)){
            case 0:
                MobsUtil.spawnPhantom(player);
                break;
            case 1:
                MobsUtil.spawnCreeper(player);
                break;
            case 2:
                MobsUtil.spawnSkeleton(player);
                break;
            case 3:
                MobsUtil.spawnZombie(player);
                break;
            case 4:
                MobsUtil.spawnPhantom(player);
                break;
            case 5:
                MobsUtil.spawnWitch(player);
                break;
            case 6:
                MobsUtil.spawnSpider(player);
                break;
            default:
                MobsUtil.spawnBlaze(player);
                break;
        }
    }
}
