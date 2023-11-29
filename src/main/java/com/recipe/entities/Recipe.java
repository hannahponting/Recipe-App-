package com.recipe.entities;



import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipe.utilities.*;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;



@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
    public Recipe (String name, List<String> ingredientsList, List<String> instructions){
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
    }
    public Recipe(){}

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonProperty(value = "ingredients")
    private List<String> ingredientsList;
    private List<String> instructions;

    @JsonProperty(value = "serving")
    private int servingNo;
    @JsonProperty(value = "time_to_cook")
    private String timeToCook;


    @JsonProperty(value = "cuisine")
    @Enumerated(EnumType.STRING)
    private Cuisine cuisineType;

    @JsonProperty(value = "difficulty_level")
    @Enumerated(EnumType.STRING)
    private Difficulty difficultyLevel;
    @JsonProperty(value = "mealType")
    @Enumerated(EnumType.STRING)
    private MealTime mealType;

    @JsonProperty(value = "cost")
    @Enumerated(EnumType.STRING)
    private Cost costType;

    @JsonProperty(value = "spice_level")
    @Enumerated(EnumType.STRING)
    private SpiceLevel spiceType;



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public int getServingNo() {
        return servingNo;
    }

    public String getTimeToCook() {
        return timeToCook;
    }

    public Cuisine getCuisineType() {
        return cuisineType;
    }

    public Difficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public MealTime getMealType() {
        return mealType;
    }

    public Cost getCostType() {
        return costType;
    }

    public SpiceLevel getSpiceType() {
        return spiceType;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }
}
