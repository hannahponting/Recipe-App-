package com.recipe.dataaccess;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

@Component
public class Populator {

    RecipeRepository recipeRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    File recipeFile = new File ("src/main/resources/recipes.json");
    @Autowired
    public Populator(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    //@EventListener(ContextRefreshedEvent.class)
    public void populate() throws IOException {
        ArrayList <Recipe> recipes = objectMapper.readValue(recipeFile, new TypeReference<>() {});
        recipeRepository.saveAll(recipes);

    }

    //@EventListener(ContextRefreshedEvent.class)
    public void addImageToExistingRecipe() throws IOException {

                int imageCounter = 1702;

            for (int i = 1; i < 79; i++) {
                RecipeService recipeService = new RecipeService(recipeRepository);

                Recipe recipe = recipeService.getRecipeById(i);

                File imageFile = new File("src/main/resources/static/images/FoodPictures/" + imageCounter + ".jpg");
                if(!imageFile.exists()) throw new FileNotFoundException("Can't find image file");

                byte[] bytes = Files.readAllBytes(imageFile.toPath());
                String imageString = Base64.getEncoder().encodeToString(bytes);
                recipe.setImage(imageString);
                recipeRepository.save(recipe);

                imageCounter++;

            }


    }
}
