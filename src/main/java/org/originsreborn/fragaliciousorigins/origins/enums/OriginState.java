package org.originsreborn.fragaliciousorigins.origins.enums;

import java.util.ArrayList;
import java.util.List;

public enum OriginState {
    EVENT,
    TEMPORARY,
    SHAPESHIFTER,
    NORMAL;
    public static OriginState getState(String str) {
        for (OriginState state : OriginState.values()) {
            if (state.name().equalsIgnoreCase(str)) {
                return state;
            }
        }
       return NORMAL; //default to normal
    }
    public static List<String> getStatesStrings(){
        ArrayList<String> list = new ArrayList<>();
        for(OriginState state : OriginState.values()){
            list.add(state.name());
        }
        return list;
    }
}
