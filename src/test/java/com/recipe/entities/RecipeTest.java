package com.recipe.entities;

import com.recipe.utilities.Cuisine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeTest {
    Recipe recipe;
    String name = "Vegetarian Fajita Bowl";
    List<String> ingredients = new ArrayList<>();
    List<String> instructions = new ArrayList<>();


    @BeforeEach
    void setUp(){
        ingredients.add("2 bell peppers (sliced)");
        ingredients.add("1 red onion (sliced)");
        instructions.add("In a large skillet, sauté bell peppers, red onion, zucchini, and cherry tomatoes in olive oil.");
        instructions.add("Sprinkle fajita seasoning over the vegetables and stir to coat evenly.");
        recipe = new Recipe(name, ingredients, instructions);
    }


    @Test
    void testOfRecipeConstructor(){
        assertEquals(recipe.getName(), name);
        assertEquals(recipe.getIngredientsList(), ingredients);
        assertEquals(recipe.getInstructions(), instructions);
    }

    @Test
    void testOfGetCookingMinutes(){
        recipe.setCookingMinutes(15);
        double minutes = recipe.getCookingMinutes();
        assertEquals(15, minutes);
    }

    @Test
    void testOfGetServingNumber(){
        recipe.setServingNo(4);
        double serving = recipe.getServingNo();
        assertEquals(4, serving);
    }

    @Test
    void testOfGetTimeToCook(){
        recipe.setTimeToCook("10");
        String time = recipe.getTimeToCook();
        assertEquals("10", time);
    }

    @Test
    void testOfGetCuisineType(){
        recipe.setCuisineType(Cuisine.AMERICAN);
        String cuisine = recipe.getCuisineType().getName();
        assertEquals("American", cuisine);
    }



}