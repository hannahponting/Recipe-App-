package com.recipe.utilities;

public enum SpiceLevel {

    NONE("None"), MILD("Mild"), MEDIUM("Medium"), SPICY("Spicy");

    private final String name;

    SpiceLevel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
