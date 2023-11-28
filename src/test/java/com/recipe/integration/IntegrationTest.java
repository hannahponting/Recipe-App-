package com.recipe.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.entities.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@Sql("classpath:test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})

class IntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void testPostingRecipe() throws Exception {
        String bakedPotato = """
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
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.post("/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bakedPotato))
                        .andExpect(status().isCreated())
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe actualRecipe = mapper.readValue(contentAsJson, Recipe.class);
        Assertions.assertEquals("baked potato",actualRecipe.getName());
        Assertions.assertEquals("bake the potato",actualRecipe.getInstructions().get(0));
        Assertions.assertEquals("1 potato",actualRecipe.getIngredientsList().get(0));


    }


    @Test
    void testDeletingRecipe() throws Exception {
        String id = "101";

        MvcResult result =
                this.mockMvc.perform(get("/recipes/"+id))
                .andExpect(status().isOk())
                        .andReturn();

        MvcResult updatedResult =
                mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/"+id))
                        .andExpect(status().isOk())
                        .andReturn();

        result =
                this.mockMvc.perform(get("/recipes/"+id))
                        .andExpect(status().isNotFound())
                        .andReturn();

    }


    @Test
    void testUpdatingRecipe() throws Exception {
        String jsonToUpdate= """
                          {
                          "id": 101,
                          "name": "Spicy Lemon Herb Chicken"}
                          """;
        MvcResult result =
                this.mockMvc.perform(get("/recipes/101"))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe actualRecipe = mapper.readValue(contentAsJson, Recipe.class);
        Assertions.assertNotSame("Spicy Lemon Herb Chicken", actualRecipe.getName());

        MvcResult updatedResult =
                this.mockMvc.perform(MockMvcRequestBuilders.patch("/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonToUpdate))
                        .andReturn();

        result =
                this.mockMvc.perform(get("/recipes/101"))
                        .andReturn();

        contentAsJson = result.getResponse().getContentAsString();
        actualRecipe = mapper.readValue(contentAsJson, Recipe.class);
        Assertions.assertEquals("Spicy Lemon Herb Chicken", actualRecipe.getName());

    }



    @Test
    void testGetRecipeByServingNumber() throws Exception {
        int serving = 4;
        String servingTest = "serving/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"+servingTest+serving)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe[] actualRecipe = mapper.readValue(contentAsJson,Recipe[].class);

        assertEquals(2,actualRecipe.length);
        assertEquals(4, actualRecipe[0].getServingNo());
        assertEquals(4, actualRecipe[1].getServingNo());

    }


    @Test
    void testGetRecipeByServingNumberIfNone() throws Exception {
        int serving = 10000;
        String servingTest = "serving/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"+servingTest+serving)))
                        .andExpect(status().isNotFound())
                        .andReturn();

        String message = result.getResponse().getErrorMessage();

        String expectedError = "Sorry, We Don't Have Recipes For Serving Number Of "+serving;
        assertEquals(expectedError, message);
    }

    @Test
    void testGetRecipeByCookingTime() throws Exception {
        String timeToCook = "20 minutes";
        String test = "cooking_time/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"+test+timeToCook)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe[] actualRecipe = mapper.readValue(contentAsJson,Recipe[].class);

        assertEquals(1,actualRecipe.length);
        assertEquals("20 minutes", actualRecipe[0].getTimeToCook());

    }

    @Test
    void testGetRecipeByCookingTimeIfNone() throws Exception {
        String timeToCook = "1000 minutes";
        String test = "cooking_time/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"+test+timeToCook)))
                        .andExpect(status().isNotFound())
                        .andReturn();

        String message = result.getResponse().getErrorMessage();

        String expectedError = "Sorry, We Don't Have Recipes For Cooking Time Of "+timeToCook;
        assertEquals(expectedError, message);
    }

    @Test
    void testGetRecipeByCuisineType() throws Exception {
        String cuisine = "MEXICAN";
        String test = "cuisine/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"+test+cuisine)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe[] actualRecipe = mapper.readValue(contentAsJson,Recipe[].class);

        assertEquals(1,actualRecipe.length);
        assertEquals("Mexican", actualRecipe[0].getCuisineType().getName());

    }

    @Test
    void testGetRecipeByCuisineTypeIfNone() throws Exception {
        String cuisine = "THAI";
        String test = "cuisine/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"+test+cuisine)))
                        .andExpect(status().isNotFound())
                        .andReturn();

        String message = result.getResponse().getErrorMessage();
        String expectedError = "Sorry, We Don't Have Recipes For Cuisine Type Of "+cuisine;
        assertEquals(expectedError, message);
    }




}
