package com.recipe.controllers;
import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import com.recipe.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete recipe")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") long id) {
        recipeService.deleteById(id);
    }

    @PostMapping("")
    @Operation(summary = "create a new recipe")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        Recipe newRecipe;
        try {
            newRecipe = recipeService.addRecipe(recipe);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return newRecipe;
    }

    @GetMapping("")
    @Operation(summary = "get all recipes")
    public Iterable<Recipe> getRecipe(){
        return recipeService.findAll();
    }
    @GetMapping("/search/{keyword}")
    @Operation(summary = "get recipes by keyword in name")
    public Iterable<Recipe> getRecipeByName(@PathVariable String keyword){
        return recipeService.findByNameContains(keyword);
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "get recipes by id ")
    public Recipe getRecipeById(@PathVariable Long recipeId){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");

        return recipe;
    }

    @GetMapping("serving/{servingNo}")
    @Operation(summary = "get recipes by serving number ")
    public Iterable<Recipe> getRecipeByServingNumber(@PathVariable int servingNo){
        return handleEmptyResult(recipeService.getRecipeByServingNumber(servingNo),"Serving Number",servingNo);
    }

    @GetMapping("cooking_time/{timeToCook}")
    @Operation(summary = "get recipes by cooking time ")
    public Iterable<Recipe> getRecipeByCookingTime(@PathVariable String timeToCook){
        return handleEmptyResult(recipeService.getRecipeByCookingTime(timeToCook), "Cooking Time", timeToCook);
    }

    @GetMapping("cuisine/{cuisineType}")
    @Operation(summary = "get recipes by cuisine type ")
    public Iterable<Recipe> getRecipeByCuisineType(@PathVariable Cuisine cuisineType){

        return handleEmptyResult(recipeService.getRecipeByCuisineType(cuisineType), "Cuisine Type", cuisineType);
    }

    @GetMapping("difficulty/{difficultyLevel}")
    @Operation(summary = "get recipes by difficulty level ")
    public Iterable<Recipe> getRecipeByDifficultyLevel(@PathVariable Difficulty difficultyLevel){

        return handleEmptyResult(recipeService.getRecipeByDifficultyLevel(difficultyLevel), "Difficulty Level", difficultyLevel);
    }

    @GetMapping("meal_type/{mealType}")
    @Operation(summary = "get recipes by meal type")
    public Iterable<Recipe> getRecipeByMealType(@PathVariable MealTime mealType){
        return handleEmptyResult(recipeService.getRecipeByMealType(mealType), "Meal Type", mealType);
    }

    @GetMapping("cost/{costType}")
    @Operation(summary = "get recipes by cost type")
    public Iterable<Recipe> getRecipeByCostType(@PathVariable Cost costType){
        return handleEmptyResult(recipeService.getRecipeByCostType(costType), "Cost Type", costType);
    }

    @GetMapping("spice_level/{spiceType}")
    @Operation(summary = "get recipes by spice level")
    public Iterable<Recipe> getRecipeBySpiceType(@PathVariable SpiceLevel spiceType){
        return handleEmptyResult(recipeService.getRecipeBySpiceType(spiceType), "Spice Level", spiceType);
    }









    private Iterable<Recipe> handleEmptyResult(Iterable<Recipe> result, String parameterName, Object parameterValue) {
        if (!result.iterator().hasNext()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, We Don't Have Recipes For " + parameterName + " Of " + parameterValue);
        }
        return result;
    }






    @PatchMapping("")
    @Operation(summary = "update recipe")
    public Recipe updateRecipe(@RequestBody Recipe incompleteRecipe){
        return recipeService.updateRecipe(incompleteRecipe);
    }



}
