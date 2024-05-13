package org.originsreborn.fragaliciousorigins.origins.enums;

import org.originsreborn.fragaliciousorigins.origins.Human;
import org.originsreborn.fragaliciousorigins.origins.Inchling;
import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.origins.Shulk;

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
    GIANT("Giant"),
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
            case SHULK:
                return new Shulk(uuid,originState,customDataString);
            case INCHLING:
                return new Inchling(uuid,originState);
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
