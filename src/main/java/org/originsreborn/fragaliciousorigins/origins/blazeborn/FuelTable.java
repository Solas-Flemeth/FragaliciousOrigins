package org.originsreborn.fragaliciousorigins.origins.blazeborn;

import org.bukkit.Material;

import java.util.HashMap;

public class FuelTable {
    private HashMap<Material, Integer> fuelSources  = new HashMap<>();

    public FuelTable(){
        generateFuelSourceMap();
    }

    public boolean isFuel(Material material){
        return fuelSources.containsKey(material);
    }
    public int getFuelTime(Material material){
        return fuelSources.getOrDefault(material, 0);
    }
    private void generateFuelSourceMap(){
        fuelSources.put(Material.LAVA_BUCKET, 20000);
        fuelSources.put(Material.COAL_BLOCK, 16000);
        fuelSources.put(Material.DRIED_KELP_BLOCK, 4000);
        fuelSources.put(Material.BLAZE_ROD, 2400);
        fuelSources.put(Material.COAL, 1600);
        fuelSources.put(Material.BAMBOO_MOSAIC, 300);
        fuelSources.put(Material.BAMBOO_MOSAIC_SLAB, 150);
        fuelSources.put(Material.BAMBOO_MOSAIC_STAIRS, 300);
        fuelSources.put(Material.BAMBOO_BLOCK, 300);
        fuelSources.put(Material.BAMBOO, 50);
        fuelSources.put(Material.SCAFFOLDING, 50);
        fuelSources.put(Material.STICK, 100);
        fuelSources.put(Material.BOWL, 100);
        fuelSources.put(Material.WOODEN_AXE, 200);
        fuelSources.put(Material.WOODEN_PICKAXE, 200);
        fuelSources.put(Material.WOODEN_HOE, 200);
        fuelSources.put(Material.WOODEN_SWORD, 200);
        fuelSources.put(Material.WOODEN_SHOVEL, 200);
        //OAK
        fuelSources.put(Material.OAK_BOAT, 1200);
        fuelSources.put(Material.OAK_CHEST_BOAT, 1200);
        fuelSources.put(Material.OAK_PLANKS, 300);
        fuelSources.put(Material.OAK_LOG, 300);
        fuelSources.put(Material.OAK_WOOD, 300);
        fuelSources.put(Material.OAK_SLAB, 150);
        fuelSources.put(Material.OAK_STAIRS, 300);
        fuelSources.put(Material.OAK_BUTTON, 100);
        fuelSources.put(Material.OAK_FENCE, 300);
        fuelSources.put(Material.OAK_FENCE_GATE, 300);
        fuelSources.put(Material.OAK_PRESSURE_PLATE, 300);
        fuelSources.put(Material.OAK_TRAPDOOR, 300);
        fuelSources.put(Material.OAK_DOOR, 200);
        fuelSources.put(Material.OAK_SIGN, 200);
        fuelSources.put(Material.OAK_HANGING_SIGN, 299);
        fuelSources.put(Material.OAK_SAPLING, 100);

        //DARK_OAK
        fuelSources.put(Material.DARK_OAK_BOAT, 1200);
        fuelSources.put(Material.DARK_OAK_CHEST_BOAT, 1200);
        fuelSources.put(Material.DARK_OAK_PLANKS, 300);
        fuelSources.put(Material.DARK_OAK_LOG, 300);
        fuelSources.put(Material.DARK_OAK_WOOD, 300);
        fuelSources.put(Material.DARK_OAK_SLAB, 150);
        fuelSources.put(Material.DARK_OAK_STAIRS, 300);
        fuelSources.put(Material.DARK_OAK_BUTTON, 100);
        fuelSources.put(Material.DARK_OAK_FENCE, 300);
        fuelSources.put(Material.DARK_OAK_FENCE_GATE, 300);
        fuelSources.put(Material.DARK_OAK_PRESSURE_PLATE, 300);
        fuelSources.put(Material.DARK_OAK_TRAPDOOR, 300);
        fuelSources.put(Material.DARK_OAK_DOOR, 200);
        fuelSources.put(Material.DARK_OAK_SIGN, 200);
        fuelSources.put(Material.DARK_OAK_HANGING_SIGN, 299);
        fuelSources.put(Material.DARK_OAK_SAPLING, 100);

        //ACACIA
        fuelSources.put(Material.ACACIA_BOAT, 1200);
        fuelSources.put(Material.ACACIA_CHEST_BOAT, 1200);
        fuelSources.put(Material.ACACIA_PLANKS, 300);
        fuelSources.put(Material.ACACIA_LOG, 300);
        fuelSources.put(Material.ACACIA_WOOD, 300);
        fuelSources.put(Material.ACACIA_SLAB, 150);
        fuelSources.put(Material.ACACIA_STAIRS, 300);
        fuelSources.put(Material.ACACIA_BUTTON, 100);
        fuelSources.put(Material.ACACIA_FENCE, 300);
        fuelSources.put(Material.ACACIA_FENCE_GATE, 300);
        fuelSources.put(Material.ACACIA_PRESSURE_PLATE, 300);
        fuelSources.put(Material.ACACIA_TRAPDOOR, 300);
        fuelSources.put(Material.ACACIA_DOOR, 200);
        fuelSources.put(Material.ACACIA_SIGN, 200);
        fuelSources.put(Material.ACACIA_HANGING_SIGN, 299);
        fuelSources.put(Material.ACACIA_SAPLING, 100);

        //SPRUCE
        fuelSources.put(Material.SPRUCE_BOAT, 1200);
        fuelSources.put(Material.SPRUCE_CHEST_BOAT, 1200);
        fuelSources.put(Material.SPRUCE_PLANKS, 300);
        fuelSources.put(Material.SPRUCE_LOG, 300);
        fuelSources.put(Material.SPRUCE_WOOD, 300);
        fuelSources.put(Material.SPRUCE_SLAB, 150);
        fuelSources.put(Material.SPRUCE_STAIRS, 300);
        fuelSources.put(Material.SPRUCE_BUTTON, 100);
        fuelSources.put(Material.SPRUCE_FENCE, 300);
        fuelSources.put(Material.SPRUCE_FENCE_GATE, 300);
        fuelSources.put(Material.SPRUCE_PRESSURE_PLATE, 300);
        fuelSources.put(Material.SPRUCE_TRAPDOOR, 300);
        fuelSources.put(Material.SPRUCE_DOOR, 200);
        fuelSources.put(Material.SPRUCE_SIGN, 200);
        fuelSources.put(Material.SPRUCE_HANGING_SIGN, 299);
        fuelSources.put(Material.SPRUCE_SAPLING, 100);

        //BIRCH
        fuelSources.put(Material.BIRCH_BOAT, 1200);
        fuelSources.put(Material.BIRCH_CHEST_BOAT, 1200);
        fuelSources.put(Material.BIRCH_PLANKS, 300);
        fuelSources.put(Material.BIRCH_LOG, 300);
        fuelSources.put(Material.BIRCH_WOOD, 300);
        fuelSources.put(Material.BIRCH_SLAB, 150);
        fuelSources.put(Material.BIRCH_STAIRS, 300);
        fuelSources.put(Material.BIRCH_BUTTON, 100);
        fuelSources.put(Material.BIRCH_FENCE, 300);
        fuelSources.put(Material.BIRCH_FENCE_GATE, 300);
        fuelSources.put(Material.BIRCH_PRESSURE_PLATE, 300);
        fuelSources.put(Material.BIRCH_TRAPDOOR, 300);
        fuelSources.put(Material.BIRCH_DOOR, 200);
        fuelSources.put(Material.BIRCH_SIGN, 200);
        fuelSources.put(Material.BIRCH_HANGING_SIGN, 299);
        fuelSources.put(Material.BIRCH_SAPLING, 100);

        //JUNGLE
        fuelSources.put(Material.JUNGLE_BOAT, 1200);
        fuelSources.put(Material.JUNGLE_CHEST_BOAT, 1200);
        fuelSources.put(Material.JUNGLE_PLANKS, 300);
        fuelSources.put(Material.JUNGLE_LOG, 300);
        fuelSources.put(Material.JUNGLE_WOOD, 300);
        fuelSources.put(Material.JUNGLE_SLAB, 150);
        fuelSources.put(Material.JUNGLE_STAIRS, 300);
        fuelSources.put(Material.JUNGLE_BUTTON, 100);
        fuelSources.put(Material.JUNGLE_FENCE, 300);
        fuelSources.put(Material.JUNGLE_FENCE_GATE, 300);
        fuelSources.put(Material.JUNGLE_PRESSURE_PLATE, 300);
        fuelSources.put(Material.JUNGLE_TRAPDOOR, 300);
        fuelSources.put(Material.JUNGLE_DOOR, 200);
        fuelSources.put(Material.JUNGLE_SIGN, 200);
        fuelSources.put(Material.JUNGLE_HANGING_SIGN, 299);
        fuelSources.put(Material.JUNGLE_SAPLING, 100);

        //MANGROVE
        fuelSources.put(Material.MANGROVE_BOAT, 1200);
        fuelSources.put(Material.MANGROVE_CHEST_BOAT, 1200);
        fuelSources.put(Material.MANGROVE_PLANKS, 300);
        fuelSources.put(Material.MANGROVE_LOG, 300);
        fuelSources.put(Material.MANGROVE_WOOD, 300);
        fuelSources.put(Material.MANGROVE_SLAB, 150);
        fuelSources.put(Material.MANGROVE_STAIRS, 300);
        fuelSources.put(Material.MANGROVE_BUTTON, 100);
        fuelSources.put(Material.MANGROVE_FENCE, 300);
        fuelSources.put(Material.MANGROVE_FENCE_GATE, 300);
        fuelSources.put(Material.MANGROVE_PRESSURE_PLATE, 300);
        fuelSources.put(Material.MANGROVE_TRAPDOOR, 300);
        fuelSources.put(Material.MANGROVE_DOOR, 200);
        fuelSources.put(Material.MANGROVE_SIGN, 200);
        fuelSources.put(Material.MANGROVE_HANGING_SIGN, 299);
        fuelSources.put(Material.MANGROVE_ROOTS, 100);

        //CHERRY
        fuelSources.put(Material.CHERRY_BOAT, 1200);
        fuelSources.put(Material.CHERRY_CHEST_BOAT, 1200);
        fuelSources.put(Material.CHERRY_PLANKS, 300);
        fuelSources.put(Material.CHERRY_LOG, 300);
        fuelSources.put(Material.CHERRY_WOOD, 300);
        fuelSources.put(Material.CHERRY_SLAB, 150);
        fuelSources.put(Material.CHERRY_STAIRS, 300);
        fuelSources.put(Material.CHERRY_BUTTON, 100);
        fuelSources.put(Material.CHERRY_FENCE, 300);
        fuelSources.put(Material.CHERRY_FENCE_GATE, 300);
        fuelSources.put(Material.CHERRY_PRESSURE_PLATE, 300);
        fuelSources.put(Material.CHERRY_TRAPDOOR, 300);
        fuelSources.put(Material.CHERRY_DOOR, 200);
        fuelSources.put(Material.CHERRY_SIGN, 200);
        fuelSources.put(Material.CHERRY_HANGING_SIGN, 299);
        fuelSources.put(Material.CHERRY_SAPLING, 100);

        //PALE_OAK
        fuelSources.put(Material.PALE_OAK_BOAT, 1200);
        fuelSources.put(Material.PALE_OAK_CHEST_BOAT, 1200);
        fuelSources.put(Material.PALE_OAK_PLANKS, 300);
        fuelSources.put(Material.PALE_OAK_LOG, 300);
        fuelSources.put(Material.PALE_OAK_WOOD, 300);
        fuelSources.put(Material.PALE_OAK_SLAB, 150);
        fuelSources.put(Material.PALE_OAK_STAIRS, 300);
        fuelSources.put(Material.PALE_OAK_BUTTON, 100);
        fuelSources.put(Material.PALE_OAK_FENCE, 300);
        fuelSources.put(Material.PALE_OAK_FENCE_GATE, 300);
        fuelSources.put(Material.PALE_OAK_PRESSURE_PLATE, 300);
        fuelSources.put(Material.PALE_OAK_TRAPDOOR, 300);
        fuelSources.put(Material.PALE_OAK_DOOR, 200);
        fuelSources.put(Material.PALE_OAK_SIGN, 200);
        fuelSources.put(Material.PALE_OAK_HANGING_SIGN, 299);
        fuelSources.put(Material.PALE_OAK_SAPLING, 100);

        //BAMBOO
        fuelSources.put(Material.BAMBOO_PLANKS, 300);
        fuelSources.put(Material.BAMBOO_SLAB, 150);
        fuelSources.put(Material.BAMBOO_STAIRS, 300);
        fuelSources.put(Material.BAMBOO_BUTTON, 100);
        fuelSources.put(Material.BAMBOO_FENCE, 300);
        fuelSources.put(Material.BAMBOO_FENCE_GATE, 300);
        fuelSources.put(Material.BAMBOO_PRESSURE_PLATE, 300);
        fuelSources.put(Material.BAMBOO_TRAPDOOR, 300);
        fuelSources.put(Material.BAMBOO_DOOR, 200);
        fuelSources.put(Material.BAMBOO_SIGN, 200);
        fuelSources.put(Material.BAMBOO_HANGING_SIGN, 299);
        //WOOL

        fuelSources.put(Material.RED_WOOL, 100);
        fuelSources.put(Material.ORANGE_WOOL, 100);
        fuelSources.put(Material.YELLOW_WOOL, 100);
        fuelSources.put(Material.LIME_WOOL, 100);
        fuelSources.put(Material.GREEN_WOOL, 100);
        fuelSources.put(Material.CYAN_WOOL, 100);
        fuelSources.put(Material.BLUE_WOOL, 100);
        fuelSources.put(Material.PURPLE_WOOL, 100);
        fuelSources.put(Material.MAGENTA_WOOL, 100);
        fuelSources.put(Material.PINK_WOOL, 100);
        fuelSources.put(Material.BLACK_WOOL, 100);
        fuelSources.put(Material.GRAY_WOOL, 100);
        fuelSources.put(Material.LIGHT_GRAY_WOOL, 100);
        fuelSources.put(Material.WHITE_WOOL, 100);
        fuelSources.put(Material.BROWN_WOOL, 100);

        //carpet - all woool variants
        fuelSources.put(Material.RED_CARPET, 67);
        fuelSources.put(Material.ORANGE_CARPET, 67);
        fuelSources.put(Material.YELLOW_CARPET, 67);
        fuelSources.put(Material.LIME_CARPET, 67);
        fuelSources.put(Material.GREEN_CARPET, 67);
        fuelSources.put(Material.CYAN_CARPET, 67);
        fuelSources.put(Material.BLUE_CARPET, 67);
        fuelSources.put(Material.PURPLE_CARPET, 67);
        fuelSources.put(Material.MAGENTA_CARPET, 67);
        fuelSources.put(Material.PINK_CARPET, 67);
        fuelSources.put(Material.BLACK_CARPET, 67);
        fuelSources.put(Material.GRAY_CARPET, 67);
        fuelSources.put(Material.LIGHT_GRAY_CARPET, 67);
        fuelSources.put(Material.WHITE_CARPET, 67);
        fuelSources.put(Material.BROWN_CARPET, 67);

    }
}
