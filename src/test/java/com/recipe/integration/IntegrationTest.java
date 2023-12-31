package com.recipe.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.entities.Recipe;
import com.recipe.utilities.Cost;
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
@TestPropertySource(locations="classpath:test.properties")

class IntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void testPostingRecipe() throws Exception {
        String bakedPotato = """
                {
                    "name": "Mediterranean Vegetable Lasagne",
                    "instructions": [
                      "Cook lasagna noodles according to package instructions.",
                      "In a bowl, mix ricotta, half of the mozzarella, Parmesan, and egg.",
                      "In a baking dish, layer noodles, ricotta mixture, vegetables, and marinara sauce.",
                      "Repeat layers, ending with a layer of sauce and mozzarella on top.",
                      "Bake until bubbly and golden brown.",
                      "Garnish with fresh basil and let it rest before slicing."
                    ],
                    "ingredients": [
                      "9 lasagna noodles",
                      "2 cups ricotta cheese",
                      "2 cups mozzarella cheese (shredded)",
                      "1 cup Parmesan cheese (grated)",
                      "2 cups marinara sauce",
                      "1 egg",
                      "Assorted vegetables (zucchini, bell peppers, spinach)",
                      "Fresh basil for garnish"
                    ],
                    "serving": 2,
                    "time_to_cook": "30 minutes",
                    "cuisine": "MEDITERRANEAN",
                    "difficulty_level": "EASY",
                    "mealType": "DINNER",
                    "cost": "MODERATE",
                    "spice_level": "NONE"
                  }
                """;
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bakedPotato))
                        .andExpect(status().isCreated())
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe actualRecipe = mapper.readValue(contentAsJson, Recipe.class);
        Assertions.assertEquals("Mediterranean Vegetable Lasagne",actualRecipe.getName());
        Assertions.assertEquals("Cook lasagna noodles according to package instructions.",actualRecipe.getInstructions().get(0));
        Assertions.assertEquals("9 lasagna noodles",actualRecipe.getIngredientsList().get(0));


    }


    @Test
    void testDeletingRecipe() throws Exception {
        String id = "102";

        MvcResult result =
                this.mockMvc.perform(get("/api/recipes/"+id))
                .andExpect(status().isOk())
                        .andReturn();

        MvcResult updatedResult =
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/"+id))
                        .andExpect(status().isOk())
                        .andReturn();

        result =
                this.mockMvc.perform(get("/api/recipes/"+id))
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
                this.mockMvc.perform(get("/api/recipes/101"))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe actualRecipe = mapper.readValue(contentAsJson, Recipe.class);
        Assertions.assertNotSame("Spicy Lemon Herb Chicken", actualRecipe.getName());

        MvcResult updatedResult =
                this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonToUpdate))
                        .andReturn();

        result =
                this.mockMvc.perform(get("/api/recipes/101"))
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
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/"+servingTest+serving)))
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
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/"+servingTest+serving)))
                        .andExpect(status().isNotFound())
                        .andReturn();

        String message = result.getResponse().getErrorMessage();


        String expectedError = "Sorry, we don't have any recipes for Serving Number of "+serving;

        assertEquals(expectedError, message);
    }

    @Test
    void testGetRecipeByCookingTime() throws Exception {
        String timeToCook = "20 minutes";
        String test = "cooking_time/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/"+test+timeToCook)))
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
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/"+test+timeToCook)))
                        .andExpect(status().isNotFound())
                        .andReturn();

        String message = result.getResponse().getErrorMessage();

        String expectedError = "Sorry, we don't have any recipes for Cooking Time of "+timeToCook;
        assertEquals(expectedError, message);
    }

    @Test
    void testGetRecipeByCuisineType() throws Exception {
        String cuisine = "MEXICAN";
        String test = "cuisine/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/"+test+cuisine)))
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
        String cuisine = "FRENCH";
        String test = "cuisine/";

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/"+test+cuisine)))
                        .andExpect(status().isNotFound())
                        .andReturn();

        String message = result.getResponse().getErrorMessage();

        String expectedError = "Sorry, we don't have any recipes for Cuisine Type of "+cuisine;

        assertEquals(expectedError, message);
    }

    @Test
    void testGetCoffee() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/coffee")))
                        .andExpect(status().isIAmATeapot())
                        .andReturn();

        String message = result.getResponse().getErrorMessage();

        String expectedError = "Sorry, I don't know how to make coffee";

        assertEquals(expectedError, message);
    }

    @Test
    void testCustomQueryMultipleCriteria() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/search/custom?query=name=quinoa&cookingMinutes<=30&servingNo>=4&id=102")))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Recipe[] actualRecipe = mapper.readValue(contentAsJson,Recipe[].class);

        assertEquals(102, actualRecipe[0].getId());
        assertEquals(Cost.MODERATE,actualRecipe[0].getCostType());
    }
}
