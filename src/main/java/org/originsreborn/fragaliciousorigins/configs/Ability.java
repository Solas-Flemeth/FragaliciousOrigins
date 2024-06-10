package org.originsreborn.fragaliciousorigins.configs;

import java.util.List;

public class Ability {
    private String name;
    private List<String> description;

    public Ability(String name, List<String> description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }
}
