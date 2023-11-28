package com.recipe.entities;


import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipe.utilities.*;
import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import javax.swing.text.Utilities;


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
    @ElementCollection
    @CollectionTable(name="INGREDIENTS",joinColumns=@JoinColumn(name="ID"))
    @JsonProperty(value = "ingredients")
    private List<String> ingredientsList;
    private ArrayList<String> instructions;

@JsonProperty(value = "serving")
    private int servingNo;
    @JsonProperty(value = "time_to_cook")
    private String timeToCook;
//    @JsonProperty(value = "cuisine")
//    private String cuisineType;
//    @JsonProperty(value = "difficulty_level")
//    private String difficultyLevel;
    @JsonProperty(value = "mealType")
    private String mealType;

    @JsonProperty(value = "cost")
    @Enumerated(EnumType.STRING)
    private Cost costType;

//    @JsonProperty(value = "spice_level")
//    private String spiceType;



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

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }
}
