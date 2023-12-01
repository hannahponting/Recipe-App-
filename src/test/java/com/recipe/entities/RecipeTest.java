package com.recipe.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeTest {


    @Test
    void testOfRecipeConstructor(){
        String name = "Vegetarian Fajita Bowl";
        List<String> ingredients = new ArrayList<>();
        ingredients.add("2 bell peppers (sliced)");
        ingredients.add("1 red onion (sliced)");
        List<String> instructions = new ArrayList<>();
        instructions.add("In a large skillet, sauté bell peppers, red onion, zucchini, and cherry tomatoes in olive oil.");
        instructions.add("Sprinkle fajita seasoning over the vegetables and stir to coat evenly.");

        Recipe recipe = new Recipe(name, ingredients, instructions);

        assertEquals(recipe.getName(), name);
        assertEquals(recipe.getIngredientsList(), ingredients);
        assertEquals(recipe.getInstructions(), instructions);
    }
    @Test
    void testParseMinutes(){

        Recipe recipe = new Recipe();
        recipe.setTimeToCook("1.5 hours");
        assertEquals(90, recipe.getCookingMinutes());
        recipe.setTimeToCook("twenty minutes");
        assertEquals(-1, recipe.getCookingMinutes());

    }

}