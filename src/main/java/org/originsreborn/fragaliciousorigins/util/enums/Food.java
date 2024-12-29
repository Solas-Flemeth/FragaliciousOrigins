package org.originsreborn.fragaliciousorigins.util.enums;

import org.bukkit.Material;

public enum Food {

    APPLE(Material.APPLE, 4, 2.4, false, true, false, true),
    BAKED_POTATO(Material.BAKED_POTATO, 5, 6, true, true, false, false),
    BEETROOT(Material.BEETROOT, 1, 1.2, false, true, false, true),
    BEETROOT_SOUP(Material.BEETROOT_SOUP, 6, 7.2, false, true, false, true),
    BREAD(Material.BREAD, 5, 6, false, true, false, false),
    CAKE(Material.CAKE, 2, 0.4, false, true, false, true),
    CARROT(Material.CARROT, 3, 3.6, false, true, false, false),
    CHORUS_FRUIT(Material.CHORUS_FRUIT, 4, 2.4, false, true, false, false),
    COOKED_CHICKEN(Material.COOKED_CHICKEN, 6, 7.2, true, false, true, false),
    COOKED_COD(Material.COOKED_COD, 5, 6, true, false, true, false),
    COOKED_MUTTON(Material.COOKED_MUTTON, 6, 9.6, true, false, true, false),
    COOKED_PORKCHOP(Material.COOKED_PORKCHOP, 8, 12.8, true, false, true, false),
    COOKED_RABBIT(Material.COOKED_RABBIT, 5, 6, true, false, true, false),
    COOKED_SALMON(Material.COOKED_SALMON, 6, 9.6, true, false, true, false),
    COOKIE(Material.COOKIE, 2, 0.4, false, true, false, true),
    DRIED_KELP(Material.DRIED_KELP, 1, 0.6, true, true, false, false),
    ENCHANTED_GOLDEN_APPLE(Material.ENCHANTED_GOLDEN_APPLE, 4, 9.6, false, true, false, true),
    GOLDEN_APPLE(Material.GOLDEN_APPLE, 4, 9.6, false, true, false, true),
    GLOW_BERRIES(Material.GLOW_BERRIES, 2, 0.4, false, true, false, false),
    GOLDEN_CARROT(Material.GOLDEN_CARROT, 6, 14.4, false, true, false, true),
    HONEY_BOTTLE(Material.HONEY_BOTTLE, 6, 1.2, false, true, false, true),
    MELON_SLICE(Material.MELON_SLICE, 2, 1.2, false, true, false, true),
    MUSHROOM_STEW(Material.MUSHROOM_STEW, 6, 7.2, false, true, false, false),
    POISONOUS_POTATO(Material.POISONOUS_POTATO, 2, 1.2, false, true, false, false),
    POTATO(Material.POTATO, 1, 0.6, false, true, false, false),
    PUFFERFISH(Material.PUFFERFISH, 1, 0.2, false, false, true, false),
    PUMPKIN_PIE(Material.PUMPKIN_PIE, 8, 4.8, false, true, false, true),
    RABBIT_STEW(Material.RABBIT_STEW, 10, 12, false, false, true, false),
    BEEF(Material.BEEF, 3, 1.8, false, false, true, false),
    CHICKEN(Material.CHICKEN, 2, 1.2, false, false, true, false),
    COD(Material.COD, 2, 0.4, false, false, true, false),
    MUTTON(Material.MUTTON, 2, 1.2, false, false, true, false),
    PORKCHOP(Material.PORKCHOP, 3, 1.8, false, false, true, false),
    RABBIT(Material.RABBIT, 3, 1.8, false, false, true, false),
    SALMON(Material.SALMON, 2, 0.4, false, false, true, false),
    ROTTEN_FLESH(Material.ROTTEN_FLESH, 4, 0.8, false, false, true, false),
    SPIDER_EYE(Material.SPIDER_EYE, 2, 3.2, false, false, true, false),
    COOKED_BEEF(Material.COOKED_BEEF, 8, 12.8, true, false, true, false),
    SUSPICIOUS_STEW(Material.SUSPICIOUS_STEW, 6, 7.2, false, true, false, true),
    SWEET_BERRIES(Material.SWEET_BERRIES, 2, 0.4, false, true, false, true),
    TROPICAL_FISH(Material.TROPICAL_FISH, 1, 0.2, false, false, true, false),
    OMINOUS_BOTTLE(Material.OMINOUS_BOTTLE, 0, 0, true, true, true, true);


    private final Material type;
    private final int hunger;
    private final double saturation;
    private final boolean isCooked;
    private final boolean isPlant;
    private final boolean isMeat;
    private final boolean isSweet;

    Food(Material type, int hunger, double saturation, boolean isCooked, boolean isPlant, boolean isMeat, boolean isSweet) {
        this.type = type;
        this.hunger = hunger;
        this.saturation = saturation;
        this.isCooked = isCooked;
        this.isPlant = isPlant;
        this.isMeat = isMeat;
        this.isSweet = isSweet;
    }

    /**
     * Get the type of Food from a material
     * @param material
     * @return
     */
    public static Food getFood(Material material) {
        try {
            return Food.valueOf(material.toString());
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    public Material getType() {
        return type;
    }

    public int getHunger() {
        return hunger;
    }

    public double getSaturation() {
        return saturation;
    }

    public boolean isCooked() {
        return isCooked;
    }

    public boolean isPlant() {
        return isPlant;
    }

    public boolean isMeat() {
        return isMeat;
    }

    public boolean isSweet() {
        return isSweet;
    }
}
