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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class IntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void testPostingRecipe() throws Exception {
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
    public void testDeletingRecipe() throws Exception {
        String id = "1";

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
    public void testUpdatingRecipe() throws Exception {
        String jsonToUpdate= """
                          {
                          "id": 1,
                          "name": "Spicy Lemon Herb Chicken"}
                          """;
        MvcResult result =
                this.mockMvc.perform(get("/recipes/1"))
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
                this.mockMvc.perform(get("/recipes/1"))
                        .andReturn();

        contentAsJson = result.getResponse().getContentAsString();
        actualRecipe = mapper.readValue(contentAsJson, Recipe.class);
        Assertions.assertEquals("Spicy Lemon Herb Chicken", actualRecipe.getName());

    }

}
