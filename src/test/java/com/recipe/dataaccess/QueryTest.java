package com.recipe.dataaccess;

import com.recipe.entities.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class QueryTest {
    @Autowired
    Populator populator;

    @Autowired
    RecipeRepository recipeRepository;
    @Test
    public void testQuery() throws IOException {
//        populator.populate();
        RecipePredicatesBuilder builder = new RecipePredicatesBuilder().with("mealType",":","BREAKFAST");
        Iterable<Recipe> results = recipeRepository.findAll(builder.build());
        Assertions.assertNotNull(results);
    }
    @Test
    public void testQueryTwoParamaters() throws IOException {
//        populator.populate();
        RecipePredicatesBuilder builder = new RecipePredicatesBuilder()
                .with("mealType",":","BREAKFAST")
                .with("servingNo",">=",4)
                ;
        Iterable<Recipe> results = recipeRepository.findAll(builder.build());
        Assertions.assertNotNull(results);
    }


}
