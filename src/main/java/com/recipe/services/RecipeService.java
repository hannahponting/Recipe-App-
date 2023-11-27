package com.recipe.services;

import com.recipe.dataaccess.RecipeRepository;
import com.recipe.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class RecipeService {
    RecipeRepository recipeRepository;
    @Autowired
    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }
    public Recipe addRecipe(Recipe recipe) {
        return null;
    }

    public List<Recipe> findAll(){
        return this.recipeRepository.findAll();
    }

    public Recipe getRecipeById(long recipeId) {
        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);
        return recipe.orElse(null);

    }





}
