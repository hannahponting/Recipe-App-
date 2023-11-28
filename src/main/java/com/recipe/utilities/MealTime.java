package com.recipe.utilities;



//@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum MealTime {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner");

    public String getName() {
        return name;
    }

    private final String name;

    MealTime(String name){
        this.name = name;
    }


}
