package com.recipe.controllers;


import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import com.recipe.utilities.Cost;
import com.recipe.utilities.Cuisine;
import com.recipe.utilities.MealTime;
import com.recipe.utilities.SpiceLevel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/recipes")
public class NonRestfulRecipeController {

    RecipeService recipeService;

    @Autowired
    public NonRestfulRecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/search")
    @ResponseBody
    public Iterable<Recipe> searchRecipeByName(@RequestParam(value = "keyword", defaultValue = "chicken") String keyword){
        return recipeService.findByNameContains(keyword);
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "get recipes by id ")
    public String  getRecipeById(@PathVariable Long recipeId, Model model){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        model.addAttribute("recipe", recipe);
        return "old";
    }

    @GetMapping("cost")
    @Operation(summary = "get recipes by cost type")
    public String getRecipeByCostType(@RequestParam(value= "cost", defaultValue = "Low") String keyword, Model model){
        Iterable<Recipe> recipes = handleEmptyResult(recipeService.getRecipeByCostType(Cost.valueOf(keyword)), "Cost Type", keyword);
        model.addAttribute("recipes", recipes);
        return "cost";
    }

    @GetMapping("cuisine")
    @Operation(summary = "get recipes by cuisine type")
    public String getRecipeByCuisineType(@RequestParam(value= "cuisine", defaultValue = "French") String keyword, Model model){
        Iterable<Recipe> recipes = handleEmptyResult(recipeService.getRecipeByCuisineType(Cuisine.valueOf(keyword)), "Cuisine", keyword);
        model.addAttribute("recipes", recipes);
        return "cuisine";
    }

    @GetMapping("spice_level")
    @Operation(summary = "get recipes by spice Level")
    public String getRecipeBySpiceLevel(@RequestParam(value= "spice_level", defaultValue = "None") String keyword, Model model){
        Iterable<Recipe> recipes = handleEmptyResult(recipeService.getRecipeBySpiceType(SpiceLevel.valueOf(keyword)), "Spice Level", keyword);
        model.addAttribute("recipes", recipes);
        return "spiceLevel";
    }

    @GetMapping("meal_type")
    @Operation(summary = "get recipes by meal_type")
    public String getRecipeByMealType(@RequestParam(value= "meal_type", defaultValue = "Breakfast") String keyword, Model model){
        Iterable<Recipe> recipes = handleEmptyResult(recipeService.getRecipeByMealType(MealTime.valueOf(keyword)), "Meal Type", keyword);
        model.addAttribute("recipes", recipes);
        return "mealType";
    }






    private Iterable<Recipe> handleEmptyResult(Iterable<Recipe> result, String parameterName, Object parameterValue) {
        if (!result.iterator().hasNext()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, We Don't Have Recipes For " + parameterName + " Of " + parameterValue);
        }
        return result;
    }



}
