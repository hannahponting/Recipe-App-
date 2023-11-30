package com.recipe.utilities;


public enum Cuisine {
    FRENCH("French"),
    ITALIAN("Italian"),
    SPANISH("Spanish"),
    INDIAN("Indian"),
    AMERICAN("American"),
    INTERNATIONAL("International"),
    MEDITERRANEAN("Mediterranean"),
    MEXICAN("Mexican"),
    ASIAN("Asian"),
    BRITISH("British");


    public String getName() {
        return name;
    }

    Cuisine(String name){
        this.name = name;
    }

    private final String name;

}
