package com.recipe.dataaccess;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.entities.CredentialPair;
import com.recipe.entities.Person;
import com.recipe.entities.Rating;
import com.recipe.entities.Recipe;
import com.recipe.services.CredentialService;
import com.recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    PersonRepository personRepository;
    RatingRepository ratingRepository;
    CredentialService credentialService;

    ObjectMapper objectMapper = new ObjectMapper();

    File recipeFile = new File ("src/main/resources/recipes.json");
    File personFile = new File("src/main/resources/persons.json");
    File ratingFile = new File("src/main/resources/ratings.json");
    File credentialFile = new File("src/main/resources/credentials.json");
    @Autowired
    public Populator(RecipeRepository recipeRepository, PersonRepository personRepository, RatingRepository ratingRepository, CredentialService credentialService) {
        this.recipeRepository = recipeRepository;
        this.personRepository = personRepository;
        this.ratingRepository = ratingRepository;
        this.credentialService = credentialService;
    }


    //@EventListener(ContextRefreshedEvent.class)
    public void populate() throws IOException {
        ArrayList <Recipe> recipes = objectMapper.readValue(recipeFile, new TypeReference<>() {});
        recipeRepository.saveAll(recipes);

    }
//    @EventListener(ContextRefreshedEvent.class)
    public void populateUsers() throws IOException {
        ArrayList <Person> persons = objectMapper.readValue(personFile, new TypeReference<>() {});
        personRepository.saveAll(persons);

    }
//            @EventListener(ContextRefreshedEvent.class)
    public void populateCredentials() throws IOException {
        ArrayList<CredentialPair> credentialPairs = objectMapper.readValue(credentialFile, new TypeReference<>() {
        });
        for (CredentialPair credentialPair : credentialPairs) {
            credentialService.generateCredential(credentialPair.getEmail(), credentialPair.getPassword());
        }
    }


//        @EventListener(ContextRefreshedEvent.class)
    public void populateRatings() throws IOException {
        ArrayList <Rating> ratings = objectMapper.readValue(ratingFile, new TypeReference<>() {});
        ratingRepository.saveAll(ratings);

    }


//    @EventListener(ContextRefreshedEvent.class)
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
