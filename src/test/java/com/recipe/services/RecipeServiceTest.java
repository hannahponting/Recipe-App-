package com.recipe.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.dataaccess.RecipeRepository;
import com.recipe.entities.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.Exceptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@SpringBootTest
class RecipeServiceTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    RecipeService recipeService;

    @MockBean
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp(){
        recipeRepository = mock(RecipeRepository.class);
        reset(this.recipeRepository);
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
}