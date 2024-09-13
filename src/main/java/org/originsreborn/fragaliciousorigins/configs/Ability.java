package org.originsreborn.fragaliciousorigins.configs;

import java.util.List;

public class Ability {
    private String name;
    private List<String> description;
    private int customModelID;

    public Ability(String name, List<String> description, int CustomModelID) {
        this.name = name;
        this.description = description;
        this.customModelID = CustomModelID;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public int getCustomModelID() {
        return customModelID;
    }
}
