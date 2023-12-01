package com.recipe.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.dataaccess.RecipeRepository;
import com.recipe.entities.Recipe;
import com.recipe.utilities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@SpringBootTest
class RecipeServiceTest {

    String jsonStringID = """
            {
            "name": "Vegetarian Fajita Bowl",
            "id": 1000,
            "mealType": "LUNCH",
            "cuisine": "MEXICAN",
            "serving": 4,
            "time_to_cook": "25 minutes",
            "difficulty_level": "EASY",
            "cost": "LOW",
            "spice_level": "MEDIUM",
            "ingredients": [
            "2 bell peppers (sliced)",
            "1 red onion (sliced)",
            "1 zucchini (sliced)",
            "1 cup cherry tomatoes (halved)",
            "2 tablespoons olive oil",
            "1 tablespoon fajita seasoning",
            "1 can black beans (drained and rinsed)",
            "Cooked rice for serving",
            "Optional toppings: avocado, salsa, sour cream"
            ],
            "instructions": [
            "In a large skillet, sauté bell peppers, red onion, zucchini, and cherry tomatoes in olive oil.",
            "Sprinkle fajita seasoning over the vegetables and stir to coat evenly.",
            "Add black beans and cook until heated through.",
            "Serve over cooked rice with optional toppings.",
            "Enjoy your vegetarian fajita bowl!"
            ]
}
""";

    String jsonStringNoID = """
            {
            "name": "Vegetarian Fajita Bowl",
            "mealType": "LUNCH",
            "cuisine": "MEXICAN",
            "serving": 4,
            "time_to_cook": "25 minutes",
            "difficulty_level": "EASY",
            "cost": "LOW",
            "spice_level": "MEDIUM",
            "ingredients": [
            "2 bell peppers (sliced)",
            "1 red onion (sliced)",
            "1 zucchini (sliced)",
            "1 cup cherry tomatoes (halved)",
            "2 tablespoons olive oil",
            "1 tablespoon fajita seasoning",
            "1 can black beans (drained and rinsed)",
            "Cooked rice for serving",
            "Optional toppings: avocado, salsa, sour cream"
            ],
            "instructions": [
            "In a large skillet, sauté bell peppers, red onion, zucchini, and cherry tomatoes in olive oil.",
            "Sprinkle fajita seasoning over the vegetables and stir to coat evenly.",
            "Add black beans and cook until heated through.",
            "Serve over cooked rice with optional toppings.",
            "Enjoy your vegetarian fajita bowl!"
            ]
}
""";

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    RecipeService recipeService;

    @MockBean
    RecipeRepository recipeRepository;

    Recipe recipe;
    Recipe recipe1;

    @BeforeEach
    void setUp() throws Exception{
        recipeRepository = mock(RecipeRepository.class);
        reset(this.recipeRepository);
        recipe = mapper.readValue(jsonStringID, Recipe.class);
        recipe1 = mapper.readValue(jsonStringNoID, Recipe.class);
    }

    @Test
    void testOfUpdateRecipeNoID()throws Exception{
        String jsonRecipe = """
                {
                   "name": "baked potato",
                   "instructions": [
                     "bake the potato"
                   ],
                   "ingredients": [
                     "1 potato"
                   ]
                 }
                """;
        Recipe recipe = mapper.readValue(jsonRecipe, Recipe.class);
        assertThrows(IllegalArgumentException.class, () -> {
            this.recipeService.updateRecipe(recipe);
        });
    }

    @Test
    void testOfUpdateRecipeNoMatchingID()throws Exception{
        String jsonRecipe = """
                {
                   "id" : 10000,
                   "name": "baked potato",
                   "instructions": [
                     "bake the potato"
                   ],
                   "ingredients": [
                     "1 potato"
                   ]
                 }
                """;
        Recipe recipe = mapper.readValue(jsonRecipe, Recipe.class);
        assertThrows(ResponseStatusException.class, () -> {
            this.recipeService.updateRecipe(recipe);
        });
    }

    @Test
    void testOfUpdateRecipeNullRecipe()throws Exception{
      Recipe recipe = null;
        assertThrows(NullPointerException.class, () -> {
            this.recipeService.updateRecipe(recipe);
        });
    }


    @Test
    void testOfAddRecipeNoNameRecipe()throws Exception{
        recipe.setName(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.recipeService.addRecipe(recipe);
        });
    }

    @Test
    void testOfAddRecipeWithIDRecipe()throws Exception{
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.recipeService.addRecipe(recipe);
        });
    }


    @Test
    void testOfAddRecipeNoInstructionsRecipe()throws Exception{
        recipe1.setInstructions(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.recipeService.addRecipe(recipe);
        });
    }

    @Test
    void testOfAddRecipeNoIngredientsList()throws Exception{
        recipe1.setIngredientsList(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.recipeService.addRecipe(recipe);
        });
        exception.getMessage();
    }


}