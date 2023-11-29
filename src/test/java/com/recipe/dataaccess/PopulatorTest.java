package com.recipe.dataaccess;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;



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