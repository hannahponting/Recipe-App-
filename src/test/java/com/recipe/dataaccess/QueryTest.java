package com.recipe.dataaccess;

import com.recipe.entities.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.io.IOException;

@SpringBootTest
public class QueryTest {
    @Autowired
    Populator populator;

    @Autowired
    RecipeRepository recipeRepository;
    @Test
    public void testQuery() throws IOException {
        RecipePredicatesBuilder builder = new RecipePredicatesBuilder().with("mealType",":","BREAKFAST");
        Iterable<Recipe> results = recipeRepository.findAll(builder.build());
        Assertions.assertNotNull(results);
    }
    @Test
    public void testQueryMultipleParameters() throws IOException {
        RecipePredicatesBuilder builder = new RecipePredicatesBuilder()
                .with("name","=","quinoa")
                .with("cookingMinutes","<=",30)
                .with("servingNo",">=",4)
                .with("id","=",102)
                ;
        Iterable<Recipe> results = recipeRepository.findAll(builder.build());
        Assertions.assertNotNull(results);
    }
    @Test
    public void testQueryNoParameters() throws IOException {
        RecipePredicatesBuilder builder = new RecipePredicatesBuilder();
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class,()-> recipeRepository.findAll(builder.build()));
    }

}
