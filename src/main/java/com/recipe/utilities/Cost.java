package com.recipe.utilities;


public enum Cost {
    LOW("Low"),
    MODERATE("Moderate"),
    PRICEY("Pricey");

    private final String name;

    Cost(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
