package org.originsreborn.fragaliciousorigins.origins.huntsman;

public enum HuntsmanMode {
    STANDARD("Standard"),
    TRACING("Tracing"),
    STUN("Stun");

    private String mode;
    HuntsmanMode(String mode) {
        this.mode = mode;
    }
    public String getMode(){
        return mode;
    }
    public static HuntsmanMode getMode(String mode){
        switch (mode) {
            case "Standard":
                return STANDARD;
            case "Tracing":
                return TRACING;
            default:
                return STUN;
        }
    }
}
