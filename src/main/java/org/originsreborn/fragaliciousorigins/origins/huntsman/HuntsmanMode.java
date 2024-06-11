package org.originsreborn.fragaliciousorigins.origins.huntsman;

import net.kyori.adventure.text.format.TextColor;

public enum HuntsmanMode {
    BROADHEAD("Standard", TextColor.color(0xEEFFEF)),
    CUPID("Cupid", TextColor.color(0xFF79CE)),
    STUN("Stun", TextColor.color(0x884D11));

    private String mode;
    private TextColor color;
    HuntsmanMode(String mode, TextColor color) {
        this.mode = mode;
        this.color = color;
    }
    public String getMode(){
        return mode;
    }
    public TextColor getColor(){
        return color;
    }
    public static HuntsmanMode getMode(String mode){
        switch (mode) {
            case "Standard":
                return BROADHEAD;
            case "Cupid":
                return CUPID;
            default:
                return STUN;
        }
    }
}
