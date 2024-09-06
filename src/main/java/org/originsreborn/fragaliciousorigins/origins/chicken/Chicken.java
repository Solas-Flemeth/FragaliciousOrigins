package org.originsreborn.fragaliciousorigins.origins.chicken;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

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
        }

    }

    /**
     * @param tickNum
     */
    @Override
    public void originParticle(int tickNum) {

    }

    /**
     *
     */
    @Override
    public void primaryAbilityLogic() {

    }

    /**
     *
     */
    @Override
    public void secondaryAbilityLogic() {

    }
    public static void onReload() {
        MAIN_ORIGIN_CONFIG.loadConfig();
        CHICKEN_CONFIG.loadConfig();
    }
}
