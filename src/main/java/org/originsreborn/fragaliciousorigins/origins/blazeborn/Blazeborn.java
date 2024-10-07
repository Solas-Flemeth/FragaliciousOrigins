package org.originsreborn.fragaliciousorigins.origins.blazeborn;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;
import org.originsreborn.fragaliciousorigins.util.PotionsUtil;

import java.util.UUID;

/**
 * Has heat tiers that buff or debuff
 * Primary ability - eats food
 * Secondary Ability - Purges negative effects, give fire resistance to nearby players for 15 seconds, and grants heat
 * - Water damages
 * - Magic resistant (33%?)
 * - On fire gives damage bonus
 * - All melee and range attacks ignites targets if above certain heat
 * - Crouching will light a fire below you if you can build there
 * - Immune to sharpness - bonus damage from Brine (For now smite) (Custom Enchant 1.21)
 */
public class Blazeborn extends Origin {
    public static final MainOriginConfig HEAT_CONFIG_1 = new MainOriginConfig(OriginType.BLAZEBORN, "l1");
    public static final MainOriginConfig HEAT_CONFIG_2 = new MainOriginConfig(OriginType.BLAZEBORN); //L2
    public static final MainOriginConfig HEAT_CONFIG_3 = new MainOriginConfig(OriginType.BLAZEBORN, "l3");
    public static final MainOriginConfig HEAT_CONFIG_4 = new MainOriginConfig(OriginType.BLAZEBORN, "l4");
    public static final MainOriginConfig HEAT_CONFIG_5 = new MainOriginConfig(OriginType.BLAZEBORN, "l5");
    private int heatValue = -1;
    public Blazeborn(UUID uuid, OriginState state, String customDataString) {
        super(uuid, OriginType.BLAZEBORN, state, customDataString);
        if(heatValue == -1){

        }
    }

    @Override
    public MainOriginConfig getConfig() {
        if(heatValue == -1){
            return HEAT_CONFIG_2;
        }
        return HEAT_CONFIG_2;
    }

    @Override
    public void originTick(int tickNum) {
        Player player = getPlayer();
        if(!player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)){
            if(player.getFireTicks() > 0){
                if(player.isSneaking()){
                    PotionsUtil.addEffect(player, PotionEffectType.LEVITATION, 1, 5);
                }
            }
        }
    }

    @Override
    public void originParticle(int tickNum) {

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
        HEAT_CONFIG_1.loadConfig();
        HEAT_CONFIG_2.loadConfig();
        HEAT_CONFIG_3.loadConfig();
        HEAT_CONFIG_4.loadConfig();
        HEAT_CONFIG_5.loadConfig();
    }
    private boolean hasFireResistance(){
        return getPlayer().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE);
    }
}
