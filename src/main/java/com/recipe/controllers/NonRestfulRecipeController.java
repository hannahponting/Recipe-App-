package com.recipe.controllers;


import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import com.recipe.utilities.Cost;
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
    public String getRecipeByCostType(@RequestParam(value= "category", defaultValue = "Low") String keyword, Model model){
        Iterable<Recipe> recipes = handleEmptyResult(recipeService.getRecipeByCostType(Cost.valueOf(keyword)), "Cost Type", keyword);
        model.addAttribute("recipes", recipes);
        return "cost";
    }









    @GetMapping("/search")
    public String searchRecipeByName(@RequestParam(value = "keyword", defaultValue = "chicken") String keyword, Model model){
    Iterable<Recipe> recipes =  recipeService.findByNameContains(keyword);
       model.addAttribute("recipes", recipes);
        return "searchresults";
    }





    private Iterable<Recipe> handleEmptyResult(Iterable<Recipe> result, String parameterName, Object parameterValue) {
        if (!result.iterator().hasNext()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, We Don't Have Recipes For " + parameterName + " Of " + parameterValue);
        }
        return result;
    }



}
