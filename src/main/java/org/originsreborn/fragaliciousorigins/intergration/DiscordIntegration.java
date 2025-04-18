package org.originsreborn.fragaliciousorigins.intergration;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.originsreborn.fragaliciousorigins.FragaliciousOrigins;
import org.originsreborn.fragaliciousorigins.configs.Ability;
import org.originsreborn.fragaliciousorigins.configs.MainConfig;
import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.Human;
import org.originsreborn.fragaliciousorigins.origins.alchemist.Alchemist;
import org.originsreborn.fragaliciousorigins.origins.arachnid.Arachnid;
import org.originsreborn.fragaliciousorigins.origins.bee.Bee;
import org.originsreborn.fragaliciousorigins.origins.chicken.Chicken;
import org.originsreborn.fragaliciousorigins.origins.elytrian.Elytrian;
import org.originsreborn.fragaliciousorigins.origins.enderian.Enderian;
import org.originsreborn.fragaliciousorigins.origins.fairy.Fairy;
import org.originsreborn.fragaliciousorigins.origins.pawsworn.Pawsworn;
import org.originsreborn.fragaliciousorigins.origins.giant.Giant;
import org.originsreborn.fragaliciousorigins.origins.huntsman.Huntsman;
import org.originsreborn.fragaliciousorigins.origins.inchling.Inchling;
import org.originsreborn.fragaliciousorigins.origins.merling.Merling;
import org.originsreborn.fragaliciousorigins.origins.phantom.Phantom;
import org.originsreborn.fragaliciousorigins.origins.phytokin.Phytokin;
import org.originsreborn.fragaliciousorigins.origins.shulk.Shulk;
import org.originsreborn.fragaliciousorigins.origins.werewolf.Werewolf;

import java.awt.*;
import java.time.Instant;
import java.util.*;
import java.util.List;

public class DiscordIntegration {
    private JDA jda;
    private TextChannel channel;
    private String channelID;
    private String guild;
    private HashMap<String, String> messageMap= new HashMap<String, String>();
    public DiscordIntegration() {
        channelID = MainConfig.getDiscordChannel();
        guild = MainConfig.getDiscordGuild();
        try {
            jda = JDABuilder.createLight(MainConfig.getBotToken(), EnumSet.noneOf(GatewayIntent.class))
                    .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                    .setBulkDeleteSplittingEnabled(false)
                    .build();
            jda.awaitReady();
            getChannel();
            FragaliciousOrigins.INSTANCE.getLogger().fine("Will log messages to " + channel.getName());
        } catch (Exception e) {
            FragaliciousOrigins.INSTANCE.getLogger().severe(" Discord: Could not establish a connection to the discord server. Reason: " + e.getMessage());
        }

    }

    public void clearTextChannel() {
        List<Message> messages;
        while (!(messages = channel.getHistory().retrievePast(100).complete()).isEmpty()) {
            channel.deleteMessages(messages).complete();
        }
    }

    public void updateOriginDiscordChannel() {
        HashMap<String, String>indexMap = new HashMap<>();
        getChannel();
        clearTextChannel();
        String indexID = sendIndex();
        //send origin info
        indexMap.put(sendOriginDiscordEmbeds(Alchemist.MAIN_ORIGIN_CONFIG), Alchemist.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Arachnid.MAIN_ORIGIN_CONFIG), Arachnid.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Bee.MAIN_ORIGIN_CONFIG), Bee.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        //sendOriginDiscordEmbeds(Blazeborn.MAIN_ORIGIN_CONFIG);
        indexMap.put(sendOriginDiscordEmbeds(Elytrian.MAIN_ORIGIN_CONFIG), Elytrian.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Enderian.MAIN_ORIGIN_CONFIG), Enderian.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Fairy.MAIN_ORIGIN_CONFIG),Fairy.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Giant.MAIN_ORIGIN_CONFIG), Giant.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        sendAdditionalForm(Giant.GIANT_ENALRGED);
        indexMap.put(sendOriginDiscordEmbeds(Human.MAIN_ORIGIN_CONFIG), Human.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Huntsman.MAIN_ORIGIN_CONFIG), Huntsman.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Inchling.MAIN_ORIGIN_CONFIG), Inchling.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Merling.MAIN_ORIGIN_CONFIG), Merling.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Pawsworn.MAIN_ORIGIN_CONFIG), Pawsworn.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Phantom.MAIN_ORIGIN_CONFIG), Phantom.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Phytokin.MAIN_ORIGIN_CONFIG), Phytokin.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        //sendOriginDiscordEmbeds(ShapeShifter.MAIN_ORIGIN_CONFIG);
        indexMap.put(sendOriginDiscordEmbeds(Shulk.MAIN_ORIGIN_CONFIG), Shulk.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Chicken.MAIN_ORIGIN_CONFIG), Chicken.MAIN_ORIGIN_CONFIG.getPlaceholdersNodeTitleName());
        indexMap.put(sendOriginDiscordEmbeds(Werewolf.WEREWOLF_HUMAN_FORM_CONFIG), Werewolf.WEREWOLF_HUMAN_FORM_CONFIG.getPlaceholdersNodeTitleName());
        sendAdditionalForm(Werewolf.WEREWOLF_WOLF_FORM_CONFIG);
        //sendOriginDiscordEmbeds(Stoneborn.MAIN_ORIGIN_CONFIG);
        //sendOriginDiscordEmbeds(Vampire.MAIN_ORIGIN_CONFIG);
        editIndex(indexMap, indexID);
    }

    private void editIndex(HashMap<String,String> map, String messageID) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Origin Index:");
        embedBuilder.setColor(new Color(0x6900FF));
        embedBuilder.setDescription("Below is a list of the current Origins (In the beta) and their stats");
        //iterate through map
        map.forEach((id, name) ->
                embedBuilder.addField(name, "https://discord.com/channels/" + guild + "/" + channelID + "/" + id, false)
        );
        embedBuilder.setTimestamp(Instant.now());
        channel.editMessageEmbedsById(messageID,  embedBuilder.build()).queue();
    }

    private String sendIndex() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Origin Index:");
        embedBuilder.setColor(new Color(0x6900FF));
        embedBuilder.setTimestamp(Instant.now());
        return channel.sendMessageEmbeds(embedBuilder.build()).complete().getId();
    }

    private String sendOriginDiscordEmbeds(MainOriginConfig config) {
        String id = "";
        id = sendStats(config, true);
        //Abilities
        sendAbility(config.getPrimary(), "Primary Ability", config.getPrimaryMaxCooldown());
        sendAbility(config.getSecondary(), "Secondary Ability", config.getSecondaryMaxCooldown());
        sendAbility(config.getCrouch(), "Crouch Ability", 0);
        for (Ability ability : config.getAbilities()) {
            sendAbility(ability, "Passive", 0);
        }
        return id;
    }
    private void sendAdditionalForm(MainOriginConfig config) {
        sendStats(config, false);
    }

    private String sendStats(MainOriginConfig config, boolean isPrimary) {
        StringBuilder abilityDescriptionString = new StringBuilder();
        for (String description : config.getPlaceholdersNodeTitleDescription()) {
            abilityDescriptionString.append(description).append("\n");
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if(isPrimary){
            embedBuilder.setTitle("General Stats:" + config.getPlaceholdersNodeTitleName());
            embedBuilder.addField("Difficulty / Complexity:", String.valueOf(config.getDifficulty()), true);
            embedBuilder.setDescription(abilityDescriptionString);
        }else{
            embedBuilder.setTitle("Additional Form Stats:" + config.getPlaceholdersNodeTitleName());
        }
        embedBuilder.setDescription(abilityDescriptionString);
        embedBuilder.addField("Mobility", "**Speed:** " + formatPercentage(config.getMovementSpeed()) + " (**Sneaking**: " + formatPercentage(config.getSneakingSpeed()) + ")" + " **Jump Height:**" +formatPercentage(config.getJumpStrength()) + " ( **Gravity:** " + formatPercentage(config.getGravity()) + ")", false);
        embedBuilder.addField("Luck", String.valueOf(config.getLuck()), true);
        embedBuilder.addField("Size", formatPercentage(config.getScale()), true);
        embedBuilder.addField("Metabolic Adjustment", String.valueOf(config.getExhaustionAdjustment()), true);
        embedBuilder.addField("Mining Capability", "**Base Speed:** " + formatPercentage(config.getBlockBreakSpeed()) + " **Tool Bonus:** " + config.getMiningEfficiency() +
                "  **Water Efficiency:**" + formatPercentage(config.getSubmergedMiningSpeed()), false);
        embedBuilder.addField("Reach", "**Building:** " + formatPercentage(config.getBlockInteractRange()) + "   **Interact:** " + formatPercentage(config.getPlayerEntityInteractRange()), false);
        embedBuilder.addField("Melee Damage:", "**Base:**" + config.getAttackDamage() + "  **Speed:** " + formatPercentage(config.getAttackSpeed()) + "   **Multiplier:** " + formatPercentage(config.getMeleeAttackMultiplier()), false);
        embedBuilder.addField("Health:", String.valueOf(config.getMaxHealth()), true);
        embedBuilder.addField("Armor:", String.valueOf(config.getArmor()), true);
        embedBuilder.addField("Toughness:", String.valueOf(config.getArmorToughness()), true);
        embedBuilder.addField("Immunities:", "**Sharpness:** " + config.getSharpnessImmune() + " **Smite:** " + config.getSmiteImmune() + " **Bane Of Arthropods:** " + config.getBaneOfArthopodsImmune(), false);
        embedBuilder.addField("Dodge Chance", formatPercentage(config.getDodgeChance()), false);
        embedBuilder.addField("Melee Damage Taken", formatPercentage(config.getMeleeDamageMultiplier()), true);
        embedBuilder.addField("Projectiles Damage Taken", formatPercentage(config.getProjectileDamageMultiplier()), true);
        embedBuilder.addField("Fire Damage Taken", formatPercentage(config.getFireDamageMultiplier()), true);
        embedBuilder.addField("Explosion Damage Taken", formatPercentage(config.getExplosionDamageMultiplier()), true);
        embedBuilder.addField("Magic Damage Taken", formatPercentage(config.getMagicDamageMultiplier()), true);
        embedBuilder.addField("Water Damage Taken", formatPercentage(config.getWaterDamageMultiplier()), true);
        embedBuilder.addField("Fall Damage Taken", formatPercentage(config.getFallDamageMultiplier()) + " (**Fall Height:** " + config.getSafeFallDistance() + " blocks)", true);
        embedBuilder.setFooter("Stats may change based upon gameplay conditions.");
        if(isPrimary){
            embedBuilder.setColor(new Color(0x6900FF));
        }else{
            embedBuilder.setColor(new Color(0x6CFFD4));
        }
        embedBuilder.setTimestamp(Instant.now());
        return channel.sendMessageEmbeds(embedBuilder.build()).complete().getId();
    }
    /**
     * Generates a discord embededMessage with a primary ability
     *
     * @param ability
     * @param type
     * @param cooldown
     */
    private void sendAbility(Ability ability, String type, int cooldown) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        switch (ability.getName()) {
            case "primaryAbility":
                embedBuilder.setTitle("No Primary Ability");
                embedBuilder.setColor(new Color(0xFF0000));
                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                break;
            case "secondaryAbility":
                embedBuilder.setTitle("No Secondary Ability");
                embedBuilder.setColor(new Color(0xFF0000));
                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                break;
            case "abilityName":
                break;
            default:
                StringBuilder abilityDescriptionString = new StringBuilder();
                for (String description : ability.getDescription()) {
                    abilityDescriptionString.append(description).append("\n");
                }
                if (cooldown > 0) {
                    embedBuilder.setTitle(type + ": " + ability.getName() + " (" + cooldown + "s)");
                }else{
                    embedBuilder.setTitle(type + ": " + ability.getName());
                }
                // Build an embed message
                embedBuilder.setDescription(abilityDescriptionString);
                embedBuilder.setColor(new Color(0x00ff00));
                switch (type){
                    case "Primary Ability":
                        embedBuilder.setFooter("**ACTIVATION:** You can activate this by using a primary ability caster, swapping hands (default F on java), performing an emote (bedrock), or typing /pa or /a1");
                        break;
                    case "Secondary Ability":
                        embedBuilder.setFooter("**ACTIVATION:** You can activate this by using a secondary ability caster, swapping hands while crouched (default F + shift on java), performing an emote while crouched (bedrock), or typing /sa or /a2");
                        break;
                }
                // Send the complex message
                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                break;
        }
    }

    private String formatPercentage(double amount) {
        return ((int) (amount * 100.0)) + "%";
    }

    private void getChannel() {
        channel = jda.getTextChannelById(channelID);
    }
}