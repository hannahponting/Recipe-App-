package com.recipe.services;

import com.recipe.dataaccess.RecipeRepository;
import com.recipe.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService {
    RecipeRepository recipeRepository;
    @Autowired
    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }
    public Recipe addRecipe(Recipe recipe) {
        if (recipe.getId() != null) {
            throw new IllegalArgumentException("The unique ID is generated by the service and does not need to be provided.");
        }
        if (recipe.getName() == null){
            throw new IllegalArgumentException("The recipe must have a name");
        }
        if (recipe.getInstructions() == null){
            throw new IllegalArgumentException("The recipe must have instructions");
        }
        if (recipe.getIngredientsList() == null){
            throw new IllegalArgumentException("The recipe must have instructions");
        }
        recipeRepository.save(recipe);
        return recipe;
    }

}
