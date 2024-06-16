package org.originsreborn.fragaliciousorigins.origins.huntsman;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Particle;

public enum HuntsmanMode {
    BROADHEAD("Standard", TextColor.color(0xEEFFEF), Particle.CRIT),
    CUPID("Cupid", TextColor.color(0xFF79CE), Particle.HEART),
    STUN("Stun", TextColor.color(0x884D11), Particle.HAPPY_VILLAGER);

    private final String mode;
    private final TextColor color;
    private final Particle particle;

    HuntsmanMode(String mode, TextColor color, Particle particle) {
        this.mode = mode;
        this.color = color;
        this.particle = particle;
    }

    public static HuntsmanMode getMode(String mode) {
        switch (mode) {
            case "Standard":
                return BROADHEAD;
            case "Cupid":
                return CUPID;
            default:
                return STUN;
        }
    }

    public String getMode() {
        return mode;
    }

    public TextColor getColor() {
        return color;
    }

    public Particle getParticle() {
        return particle;
    }
}
