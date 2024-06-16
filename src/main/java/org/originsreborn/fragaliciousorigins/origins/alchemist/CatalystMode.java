package org.originsreborn.fragaliciousorigins.origins.alchemist;

import net.kyori.adventure.text.format.TextColor;

public enum CatalystMode {
    SPIDER_EYE("Spider Eye", TextColor.color(0xAA3452)),
    GLOWSTONE("Glowstone", TextColor.color(0xFFD61A)),
    REDSTONE("Redstone", TextColor.color(0xE82E27));

    private String mode;
    private TextColor color;
    CatalystMode(String mode, TextColor color) {
        this.mode = mode;
        this.color = color;
    }
    public String getMode(){
        return mode;
    }
    public TextColor getColor(){
        return color;
    }
    public static CatalystMode getMode(String mode){
        return switch (mode) {
            case "Spider Eye" -> SPIDER_EYE;
            case "Glowstone" -> GLOWSTONE;
            default -> REDSTONE;
        };
    }
    public static CatalystMode getNextMode(CatalystMode mode){
        return switch (mode) {
            case SPIDER_EYE -> GLOWSTONE;
            case GLOWSTONE -> REDSTONE;
            default -> SPIDER_EYE;
        };
    }
}
