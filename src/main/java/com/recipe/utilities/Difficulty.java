package com.recipe.utilities;

public enum Difficulty {
    EASY("Easy"),
    MODERATE("Moderate"),
    HIGH("High");

    public String getName() {
        return name;
    }

    private final String name;

    Difficulty(String name){
        this.name=name;
    }

}
