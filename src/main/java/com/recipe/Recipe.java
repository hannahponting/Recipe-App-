package com.recipe;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
    public Recipe (String name, ArrayList<String> ingredientsList, ArrayList<String> instructions){
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
    private ArrayList<String> ingredientsList;
    private ArrayList<String> instructions;

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

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }
}
