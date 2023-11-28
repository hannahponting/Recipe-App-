package com.recipe.utilities;


public enum Cuisine {
    FRENCH("French"),
    CHINESE("Chinese"),
    JAPANESE("Japanese"),
    ITALIAN("Italian"),
    GREEK("Greek"),
    SPANISH("Spanish"),
    INDIAN("Indian"),
    AMERICAN("American"),
    INTERNATIONAL("International"),
    MEDITERRANEAN("Mediterranean"),
    MEXICAN("Mexican"),
    ASIAN("Asian"),
    BRITISH("British"),
    SOUTHERN("Southern"),
    THAI("Thai");


    public String getName() {
        return name;
    }

    Cuisine(String name){
        this.name = name;
    }

    private final String name;

}
