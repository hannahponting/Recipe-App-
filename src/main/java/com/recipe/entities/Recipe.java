package com.recipe.entities;

import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Recipe {
    public Recipe (String name, ArrayList<String> ingredientsList, String instructions){
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
    }
    public Recipe(){}
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private ArrayList<String> ingredientsList;
    private String instructions;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(ArrayList<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
