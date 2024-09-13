package org.originsreborn.fragaliciousorigins.intergration;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;


/*
    FORMAT: origin.value.(optional_iterator)
 */
public class OriginPlaceholders extends PlaceholderExpansion {
    /**
     * @return
     */
    @Override
    public @NotNull String getIdentifier() {
        return "FragaliciousOrigins";
    }

    /**
     * @return
     */
    @Override
    public @NotNull String getAuthor() {
        return "Sir Fragalicious";
    }

    /**
     * @return
     */
    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        params = params.toLowerCase();
        String[] args = params.split("\\.");
        Origin origin = FragaliciousOrigins.ORIGINS.getOrigin(player.getUniqueId());
        if (args[0].equals("icon") && origin != null) {
            return origin.getConfig().getPlaceholdersNodeTitleIcon();
        }
        OriginType type = OriginType.getByDisplayName(args[0]);
        if (type.equals(OriginType.HUMAN) && !args[0].equals("human") && args.length == 1) {
            return "Unknown";
        }
        try {
            MainOriginConfig config = type.getConfig();
            switch (args[1]) {
                case "info":
                    switch (args[2]) {
                        case "name":
                            return config.getPlaceholdersNodeTitleName();
                        case "icon":
                            return config.getPlaceholdersNodeTitleIcon();
                        case "description":
                            return config.getPlaceholdersNodeTitleDescription().get(Integer.parseInt(args[3]));
                        case "difficulty":
                            return convertDifficulty(config.getDifficulty());
                        case "model":
                            return String.valueOf(config.getModel());
                        case "count":
                            return String.valueOf(type.getConfig().getPlaceholdersNodeTitleDescription().size());
                    }
                case "primary":
                    switch (args[2]) {
                        case "name":
                            return config.getPlaceholdersPrimaryAbilityName();
                        case "description":
                            return config.getPlaceholdersPrimaryAbilityDescription().get(Integer.parseInt(args[3]));
                        case "count":
                            return String.valueOf(type.getConfig().getPlaceholdersPrimaryAbilityDescription().size());
                        case "model":
                            return String.valueOf(config.getPrimary().getCustomModelID());
                        case "enabled":
                            return String.valueOf(!config.getPlaceholdersPrimaryAbilityName().equals("primaryAbility"));
                    }
                case "secondary":
                    switch (args[2]) {
                        case "name":
                            return config.getPlaceholdersSecondaryAbilityName();
                        case "description":
                            return config.getPlaceholdersSecondaryAbilityDescription().get(Integer.parseInt(args[3]));
                        case "count":
                            return String.valueOf(type.getConfig().getPlaceholdersSecondaryAbilityDescription().size());
                        case "model":
                            return String.valueOf(config.getSecondary().getCustomModelID());
                        case "enabled":
                                return String.valueOf(!config.getPlaceholdersSecondaryAbilityName().equals("secondaryAbility"));
                    }
                    case "crouch":
                        switch (args[2]) {
                            case "name":
                                return config.getPlaceholdersCrouchAbilityName();
                            case "description":
                                return config.getPlaceholdersCrouchAbilityDescription().get(Integer.parseInt(args[3]));
                            case "count":
                                return String.valueOf(type.getConfig().getPlaceholdersCrouchAbilityDescription().size());
                            case "model":
                                return String.valueOf(config.getCrouch().getCustomModelID());
                            case "enabled":
                                return String.valueOf(!config.getPlaceholdersCrouchAbilityName().equals("abilityName"));
                    }
                case "ability":
                    int abilityNum = Integer.parseInt(args[2]);
                    switch (args[3]) {
                        case "name":
                            return config.getPlaceholderAbilityName(abilityNum);
                        case "description":
                            return config.getAbilityDescription(abilityNum).get(Integer.parseInt(args[4]));
                        case "count":
                            return String.valueOf(config.getAbilityDescription(abilityNum).size());
                        case "model":
                            return String.valueOf(config.getAbilities().get(abilityNum).getCustomModelID());
                        case "enabled":
                            return String.valueOf(!config.getPlaceholderAbilityName(abilityNum).equals("abilityName"));
                    }
            }
        } catch (Exception ignored) {
        }
        return "";
    }
    private static String convertDifficulty(int difficulty){
        return switch (difficulty) {
            case 2 -> "2";
            case 3 -> "3";
            case 4 -> "4";
            default -> "1";
        };
    }
}
