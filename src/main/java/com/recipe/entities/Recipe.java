package com.recipe.entities;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.recipe.utilities.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static com.recipe.utilities.Cuisine.MEDITERRANEAN;


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
    @JsonView(ReadUpdateDelete.class)
    @Schema(description = "Unique ID of the recipe", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @JsonView(CreateReadUpdateDelete.class)
    @Schema(description = "Title of the recipe", example = "Mediterranean Vegetable Lasagne", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @ElementCollection
    @CollectionTable(name="INGREDIENTS",joinColumns=@JoinColumn(name="ID"))
    @JsonProperty(value = "ingredients")
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(description = "List of ingredients", example = """
            [
            "9 lasagna noodles",
            "2 cups ricotta cheese",
            "2 cups mozzarella cheese (shredded)",
            "1 cup Parmesan cheese (grated)",
            "2 cups marinara sauce",
            "1 egg",
            "Assorted vegetables (zucchini, bell peppers, spinach)",
            "Fresh basil for garnish"
            ]"""
            , requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> ingredientsList;
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(description = "List of cooking instructions", example = """
            [
                   "Cook lasagna noodles according to package instructions.",
                   "In a bowl, mix ricotta, half of the mozzarella, Parmesan, and egg.",
                   "In a baking dish, layer noodles, ricotta mixture, vegetables, and marinara sauce.",
                   "Repeat layers, ending with a layer of sauce and mozzarella on top.",
                   "Bake until bubbly and golden brown.",
                   "Garnish with fresh basil and let it rest before slicing."
                 ]"""
            , requiredMode = Schema.RequiredMode.REQUIRED)
    @ElementCollection
    @CollectionTable(name="INSTRUCTIONS",joinColumns=@JoinColumn(name="ID"))
    private List<String> instructions;


    @JsonProperty(value = "serving")
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(description = "Number of servings", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private int servingNo;


    @JsonProperty(value = "time_to_cook")
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(description = "Cooking time", example = "30 minutes", requiredMode = Schema.RequiredMode.REQUIRED)
    private String timeToCook;

    @JsonProperty(value = "cuisine")
    @Enumerated(EnumType.STRING)
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(description = "Cuisine type", implementation = Cuisine.class, requiredMode = Schema.RequiredMode.REQUIRED, example = "MEDITERRANEAN")
    private Cuisine cuisineType;

    @JsonProperty(value = "difficulty_level")
    @Enumerated(EnumType.STRING)
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(implementation = Difficulty.class, example = "EASY", requiredMode = Schema.RequiredMode.REQUIRED)
    private Difficulty difficultyLevel;

    @JsonProperty(value = "mealType")
    @Enumerated(EnumType.STRING)
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(implementation = MealTime.class, example = "DINNER", requiredMode = Schema.RequiredMode.REQUIRED)
    private MealTime mealType;

    @JsonProperty(value = "cost")
    @Enumerated(EnumType.STRING)
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(implementation = Cost.class, example = "MODERATE",requiredMode = Schema.RequiredMode.REQUIRED)
    private Cost costType;



    @JsonProperty(value = "spice_level")
    @Enumerated(EnumType.STRING)
    @JsonView(CreateReadUpdateDelete.class)
    @Schema(implementation = SpiceLevel.class, example = "NONE",requiredMode = Schema.RequiredMode.REQUIRED)
    private SpiceLevel spiceType;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public List<String> getInstructions() {
        return instructions;
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

    public MealTime getMealType() {
        return mealType;
    }

    public Cost getCostType() {
        return costType;
    }

    public SpiceLevel getSpiceType() {
        return spiceType;
    }

    public Difficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public void setServingNo(int servingNo) {
        this.servingNo = servingNo;
    }

    public void setTimeToCook(String timeToCook) {
        this.timeToCook = timeToCook;
    }

    public void setCuisineType(Cuisine cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setDifficultyLevel(Difficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setMealType(MealTime mealType) {
        this.mealType = mealType;
    }

    public void setCostType(Cost costType) {
        this.costType = costType;
    }

    public void setSpiceType(SpiceLevel spiceType) {
        this.spiceType = spiceType;
    }

    public interface ReadUpdateDelete{}
    public interface CreateReadUpdateDelete{}

}
