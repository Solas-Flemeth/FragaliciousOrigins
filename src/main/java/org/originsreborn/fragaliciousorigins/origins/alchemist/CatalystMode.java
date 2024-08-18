package org.originsreborn.fragaliciousorigins.origins.alchemist;

import net.kyori.adventure.text.format.TextColor;

public enum CatalystMode {
    SPIDER_EYE("Spider Eye", TextColor.color(0xC42067), 1.5f),
    GLOWSTONE("Glowstone", TextColor.color(0xFFD61A), 0.5f),
    REDSTONE("Redstone", TextColor.color(0xE82E27), 0.25f),
    MILK("Milk", TextColor.color(0xffffff), 0f);

    private String mode;
    private TextColor color;
    private float multiplier;
    CatalystMode(String mode, TextColor color, float multiplier) {
        this.mode = mode;
        this.color = color;
        this.multiplier = multiplier;
    }
    public String getMode(){
        return mode;
    }

    public TextColor getColor(){
        return color;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public static CatalystMode getMode(String mode){
        return switch (mode) {
            case "Spider Eye" -> SPIDER_EYE;
            case "Glowstone" -> GLOWSTONE;
            case "Redstone" -> REDSTONE;
            default -> MILK;
        };
    }
    public static CatalystMode getNextMode(CatalystMode mode){
        return switch (mode) {
            case SPIDER_EYE -> GLOWSTONE;
            case GLOWSTONE -> REDSTONE;
            case REDSTONE ->  MILK;
            default -> SPIDER_EYE;
        };
    }
}
