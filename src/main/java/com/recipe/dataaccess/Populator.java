package com.recipe.dataaccess;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class Populator {
    RecipeRepository recipeRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    File recipeFile = new File ("src/main/resources/recipes.json");



    @EventListener(ContextRefreshedEvent.class)
    public void populate() throws IOException {
        ArrayList <Recipe> recipes = objectMapper.readValue(recipeFile, new TypeReference<ArrayList<Recipe>>() {});
        for (Recipe recipe: recipes) {
            recipeRepository.save(recipe);
        }

    }
}
