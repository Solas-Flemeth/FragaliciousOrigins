package org.originsreborn.fragaliciousorigins.origins.enums;

import org.originsreborn.fragaliciousorigins.configs.MainOriginConfig;
import org.originsreborn.fragaliciousorigins.origins.*;
import org.originsreborn.fragaliciousorigins.origins.Human;
import org.originsreborn.fragaliciousorigins.origins.alchemist.Alchemist;
import org.originsreborn.fragaliciousorigins.origins.arachnid.Arachnid;
import org.originsreborn.fragaliciousorigins.origins.bee.Bee;
import org.originsreborn.fragaliciousorigins.origins.blazeborn.Blazeborn;
import org.originsreborn.fragaliciousorigins.origins.chicken.Chicken;
import org.originsreborn.fragaliciousorigins.origins.elytrian.Elytrian;
import org.originsreborn.fragaliciousorigins.origins.fairy.Fairy;
import org.originsreborn.fragaliciousorigins.origins.pawsworn.Pawsworn;
import org.originsreborn.fragaliciousorigins.origins.phantom.Phantom;
import org.originsreborn.fragaliciousorigins.origins.enderian.Enderian;
import org.originsreborn.fragaliciousorigins.origins.giant.Giant;
import org.originsreborn.fragaliciousorigins.origins.huntsman.Huntsman;
import org.originsreborn.fragaliciousorigins.origins.inchling.Inchling;
import org.originsreborn.fragaliciousorigins.origins.merling.Merling;
import org.originsreborn.fragaliciousorigins.origins.phytokin.Phytokin;
import org.originsreborn.fragaliciousorigins.origins.shulk.Shulk;
import org.originsreborn.fragaliciousorigins.origins.werewolf.Werewolf;
import org.originsreborn.fragaliciousorigins.origins.wip.ShapeShifter;
import org.originsreborn.fragaliciousorigins.origins.wip.postrelease.Vampire;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum OriginType {
    HUMAN("Human"), //default
    ALCHEMIST("Alchemist"),
    ARACHNID("Arachnid"),
    BEE("Bee"),
    BLAZEBORN("Blazeborn"),
    ENDERIAN("Enderian"),
    ELYTRIAN("Elytrian"),
    FAIRY("Fairy"),
    PAWSWORN("Pawsworn"),
    GIANT("Giant"),
    INCHLING("Inchling"),
    HUNTSMAN("Huntsman"),
    MERLING("Merling"),
    PHANTOM("Phantom"),
    PHYTOKIN("Phytokin"),
    SHULK("Shulk"),
    SHAPESHIFTER("Shapeshifter"), //TODO: FINISH POST S2
    VAMPIRE("Vampire"),  //TODO: FINISH POST S2
    WEREWOLF("Werewolf"),
    CHICKEN("Chicken");


    private String display;
    OriginType(String display){
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    /**
     * Get the OriginType by the name. Defaults to human if unknown
     * @param name
     * @return
     */
    public static OriginType getByDisplayName(String name) {
        for (OriginType originType : OriginType.values()) {
            if (originType.display.equalsIgnoreCase(name)) {
                return originType;
            }
        }
        return HUMAN;
    }

    public Origin generateOrigin(UUID uuid, OriginState originState, String customDataString){
        switch (this){
            case ALCHEMIST:
                return new Alchemist(uuid,originState,customDataString);
            case ARACHNID:
                return new Arachnid(uuid,originState,customDataString);
            case BEE:
                return new Bee(uuid,originState,customDataString);
            case BLAZEBORN:
                return new Blazeborn(uuid,originState,customDataString);
            case ENDERIAN:
                return new Enderian(uuid,originState,customDataString);
            case ELYTRIAN:
                return new Elytrian(uuid,originState,customDataString);
            case FAIRY:
                return new Fairy(uuid, originState, customDataString);
            case PAWSWORN:
                return new Pawsworn(uuid,originState,customDataString);
            case GIANT:
                return new Giant(uuid,originState,customDataString);
            case INCHLING:
                return new Inchling(uuid,originState, customDataString);
            case HUNTSMAN:
                return new Huntsman(uuid,originState,customDataString);
            case MERLING:
                return new Merling(uuid,originState, customDataString);
            case PHANTOM:
                return new Phantom(uuid,originState, customDataString);
            case PHYTOKIN:
                return new Phytokin(uuid,originState, customDataString);
            case SHULK:
                return new Shulk(uuid,originState, customDataString);
            case WEREWOLF:
                return new Werewolf(uuid,originState, customDataString);
            case CHICKEN:
                return new Chicken(uuid,originState, customDataString);
            case SHAPESHIFTER:
                return new ShapeShifter(uuid,originState,customDataString);
            case VAMPIRE:
                return new Vampire(uuid,originState, customDataString);
            case HUMAN:
            default:
                return new Human(uuid);
        }
    }
    public Origin generateOrigin(String uuidString, String state, String customDataString){
        UUID uuid = UUID.fromString(uuidString);
        OriginState originState = OriginState.getState(state);
        return generateOrigin(uuid, originState, customDataString);
    }
    public static List<String> getTypesStrings(){
        ArrayList<String> list = new ArrayList<>();
        for(OriginType type : OriginType.values()){
            list.add(type.name());
        }
        return list;
    }
    public MainOriginConfig getConfig(){
        switch (this){
            case ALCHEMIST:
                return Alchemist.MAIN_ORIGIN_CONFIG;
            case ARACHNID:
                return Arachnid.MAIN_ORIGIN_CONFIG;
            case BEE:
                return Bee.MAIN_ORIGIN_CONFIG;
            case BLAZEBORN:
                return Blazeborn.HEAT_CONFIG_2;
            case ENDERIAN:
                return Enderian.MAIN_ORIGIN_CONFIG;
            case ELYTRIAN:
                return Elytrian.MAIN_ORIGIN_CONFIG;
            case FAIRY:
                return Fairy.MAIN_ORIGIN_CONFIG;
            case PAWSWORN:
                return Pawsworn.MAIN_ORIGIN_CONFIG;
            case GIANT:
                return Giant.MAIN_ORIGIN_CONFIG;
            case INCHLING:
                return Inchling.MAIN_ORIGIN_CONFIG;
            case HUNTSMAN:
                return Huntsman.MAIN_ORIGIN_CONFIG;
            case MERLING:
                return Merling.MAIN_ORIGIN_CONFIG;
            case PHANTOM:
                return Phantom.MAIN_ORIGIN_CONFIG;
            case PHYTOKIN:
                return  Phytokin.MAIN_ORIGIN_CONFIG;
            case SHULK:
                return  Shulk.MAIN_ORIGIN_CONFIG;
            case WEREWOLF:
                return  Werewolf.WEREWOLF_HUMAN_FORM_CONFIG;
            case CHICKEN:
                return  Chicken.MAIN_ORIGIN_CONFIG;
            case SHAPESHIFTER:
                return ShapeShifter.MAIN_ORIGIN_CONFIG;
            case VAMPIRE:
                return Vampire.MAIN_ORIGIN_CONFIG;
            case HUMAN:
            default:
                return Human.MAIN_ORIGIN_CONFIG;
        }
    }
}
