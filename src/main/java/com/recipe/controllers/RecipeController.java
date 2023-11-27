package com.recipe.controllers;

import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("")
    public Iterable<Recipe> getRecipe(){
        return recipeService.findAll();
    }

    @GetMapping("/{recipeId}")
    public Recipe getRecipeById(@PathVariable Long recipeId){
        Recipe recipe = recipeService.getrecipeById(recipeId);
        if (recipe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");

        return recipe;
    }







}
