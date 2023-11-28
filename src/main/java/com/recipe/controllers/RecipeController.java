package com.recipe.controllers;
import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
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
    @GetMapping("/search/name/{keyword}")
    @Operation(summary = "get recipes by keyword in name")
    public Iterable<Recipe> getRecipeByName(@PathVariable String keyword){
        return recipeService.findByNameContains(keyword);
    }
    @GetMapping("/search/ingredient/{ingredient}")
    @Operation(summary = "get recipes by keyword in ingredients")
    public Iterable<Recipe> getRecipeByIngredient(@PathVariable String ingredient){
        return recipeService.findByIngredientsContain(ingredient);
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "get recipes by id ")
    public Recipe getRecipeById(@PathVariable Long recipeId){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");

        return recipe;
    }

    @PatchMapping("")
    @Operation(summary = "update recipe")
    public Recipe updateRecipe(@RequestBody Recipe incompleteRecipe){
        Recipe oldRecipe = null;
        if(incompleteRecipe.getId()==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        else {
            oldRecipe = recipeService.getRecipeById(incompleteRecipe.getId());
        }
        if (oldRecipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        else {
            if (incompleteRecipe.getName() != null)
                oldRecipe.setName(incompleteRecipe.getName());
            if (incompleteRecipe.getIngredientsList() != null)
                oldRecipe.setIngredientsList(incompleteRecipe.getIngredientsList());
            if (incompleteRecipe.getInstructions() != null)
                oldRecipe.setInstructions(incompleteRecipe.getInstructions());
//            oldRecipe = recipeService.updateRecipe(oldRecipe);
        }
        return oldRecipe;
    }



}
