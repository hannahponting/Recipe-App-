package com.recipe.dataaccess;



import com.recipe.entities.Recipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class PopulatorTest {


        @Autowired
        Populator populator;

        @Autowired
        RecipeRepository recipeRepository;




    @Test
    void testOfPopulator() throws Exception{
        long id = 1;
        populator.populate();
        assertNotNull(recipeRepository.findRecipeById(id));

    }
}