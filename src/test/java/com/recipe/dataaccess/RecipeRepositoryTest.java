package com.recipe.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration

class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository;


//    @Test
//    public void recipeImageSpikeTest() throws IOException {
//        Recipe recipe = new Recipe();
//        recipe.setName("Test1");
//
//        recipeRepository.save(recipe);
//
//        File imageFile = new File("src/test/resources/veggie_fajita.jpg");
//        if(!imageFile.exists()) throw new FileNotFoundException("Can't find image file");
//
//        byte[] bytes = Files.readAllBytes(imageFile.toPath());
//        String imageString = Base64.getEncoder().encodeToString(bytes);
//        recipe.setImage(imageString);
//        recipeRepository.save(recipe);
//
//        Recipe resultRecipe = recipeRepository.findRecipeById(recipe.getId());
//
//        assertEquals(recipe.getImage(), resultRecipe.getImage());
//
//        recipeRepository.delete(recipe);
//
//    }

}