package org.originsreborn.fragaliciousorigins.origins.enums;

import org.originsreborn.fragaliciousorigins.origins.*;
import org.originsreborn.fragaliciousorigins.origins.complete.Human;
import org.originsreborn.fragaliciousorigins.origins.complete.Shulk;
import org.originsreborn.fragaliciousorigins.origins.wip.*;

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
    FELINE("Feline"),
    GIANT("Cyclops"),
    INCHLING("Inchling"),
    HUNTSMAN("Huntsman"),
    MERLING("Merling"),
    PHANTOM("Phantom"),
    PHYTOKIN("Phytokin"),
    SHULK("Shulk"),
    STONEBORN("Stoneborn"),
    SHAPESHIFTER("Shapeshifter"),
    VAMPIRE("Vampire");

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
            case FELINE:
                return new Feline(uuid,originState,customDataString);
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
            case STONEBORN:
                return new Stoneborn(uuid,originState, customDataString);
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
}
