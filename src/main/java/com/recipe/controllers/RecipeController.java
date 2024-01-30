package com.recipe.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.recipe.entities.Rating;
import com.recipe.entities.Recipe;
import com.recipe.services.RatingService;
import com.recipe.services.RecipeService;
import com.recipe.utilities.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

//import static com.recipe.entities.QRecipe.recipe;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    RecipeService recipeService;
    RatingService ratingService;

    @Autowired
    public RecipeController(RecipeService recipeService, RatingService ratingService) {
        this.recipeService = recipeService;
        this.ratingService = ratingService;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete recipe by ID")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable("id") long id) {
        try {
            return recipeService.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());}
    }

    @PostMapping("")
    @Operation(summary = "create a new recipe")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe addRecipe(@RequestBody @JsonView(Recipe.CreateReadUpdateDelete.class) Recipe recipe) {
        Recipe newRecipe;
        try {
            newRecipe = recipeService.addRecipe(recipe);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return newRecipe;
    }

    @GetMapping("")
    @Operation(summary = "get all recipes")
    @JsonView(Recipe.NonImage.class)
    public Iterable<Recipe> getRecipe(){
        return recipeService.findAll();
    }
    @GetMapping("/page/{num}/{size}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get all recipes paginated")
    public Page<Recipe> getRecipePage(@PathVariable int num, @PathVariable int size){
        return recipeService.paginatedRecipes(PageRequest.of(num -1, size));
    }
    @GetMapping("/search/name/{keyword}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by keyword in recipe name")
    public Iterable<Recipe> getRecipeByName(@PathVariable String keyword){
        return recipeService.findByNameContains(keyword);
    }
    @GetMapping("search/custom")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes using custom query",
            description = "Search on any parameter of the recipe whose value is a string or number. \n" +
                    "The parameter name is the underlying name of the field, such as servingNo, rather than the Json property name given in the schema. \n" +
                    "An = indicates equals or contains. Other options are <= or >=. \n" +
                    "Multiple queries are separated by an ampersand. \n" +
                    "Non-string/number fields such as ingredients list cannot be searched in this way.")
    public Iterable<Recipe> getRecipeByCustomQuery(@RequestParam(value = "query", defaultValue = "servingNo>=2&cookingMinutes<=30&cuisineType=mexican") String query){
        return handleEmptyResult(recipeService.findRecipeByCustomQuery(query),"custom query", query);
    }
    @GetMapping("/search/ingredient/{ingredient}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by keyword in ingredients")
    public Iterable<Recipe> getRecipeByIngredient(@PathVariable String ingredient){
        return handleEmptyResult(recipeService.findByIngredientsContain(ingredient),"ingredient",ingredient);
    }
    @GetMapping("/search/ingredients/{ingredients}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by keyword in ingredients")
    public Iterable<Recipe> getRecipeByMultipleIngredients(@RequestParam(value = "query", defaultValue = "lemon&garlic") String ingredients){
        return handleEmptyResult(recipeService.findRecipeByMultipleIngredients(ingredients), "ingredients",ingredients);
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "get recipes by id ")
    @JsonView(Recipe.NonImage.class)
    public Recipe getRecipeById(@PathVariable Long recipeId){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");

        return recipe;
    }

    @GetMapping("serving/{servingNo}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by number of servings")
    public Iterable<Recipe> getRecipeByServingNumber(@PathVariable int servingNo){
        return handleEmptyResult(recipeService.getRecipeByServingNumber(servingNo),"Serving Number",servingNo);
    }

    @GetMapping("cooking_time/{timeToCook}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by cooking time ")
    public Iterable<Recipe> getRecipeByCookingTime(@PathVariable String timeToCook){
        return handleEmptyResult(recipeService.getRecipeByCookingTime(timeToCook), "Cooking Time", timeToCook);
    }
    @GetMapping("cooking_time_minutes/{minutes}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes with a cooking time less than or equal to a number of minutes")
    public Iterable<Recipe> getRecipeByCookingTimeLessThanOrEqual(@PathVariable Double minutes){
        return handleEmptyResult(recipeService.getRecipeByCookingTimeLessThanOrEqual(minutes), "Cooking Time", minutes);
    }
//    @GetMapping("rating/{rating}")
//    @JsonView(Recipe.NonImage.class)
//    @Operation(summary = "get recipes with a rating greater than or equal to a number from 1 to 5")
//    public Iterable<Recipe> getRecipeByRatingGreaterThanOrEqual(@PathVariable Double rating){
//        return handleEmptyResult(recipeService.getRecipeByRatingGreaterThanOrEqual(rating), "rating", rating);
//    }

    @GetMapping("cuisine/{cuisineType}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by cuisine type ")
    public Iterable<Recipe> getRecipeByCuisineType(@PathVariable Cuisine cuisineType){

        return handleEmptyResult(recipeService.getRecipeByCuisineType(cuisineType), "Cuisine Type", cuisineType);
    }

    @GetMapping("difficulty/{difficultyLevel}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by difficulty level ")
    public Iterable<Recipe> getRecipeByDifficultyLevel(@PathVariable Difficulty difficultyLevel){

        return handleEmptyResult(recipeService.getRecipeByDifficultyLevel(difficultyLevel), "Difficulty Level", difficultyLevel);
    }

    @GetMapping("meal_type/{mealType}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by meal type")
    public Iterable<Recipe> getRecipeByMealType(@PathVariable MealTime mealType){
        return handleEmptyResult(recipeService.getRecipeByMealType(mealType), "Meal Type", mealType);
    }

    @GetMapping("cost/{costType}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by cost type")
    public Iterable<Recipe> getRecipeByCostType(@PathVariable Cost costType){
        return handleEmptyResult(recipeService.getRecipeByCostType(costType), "Cost Type", costType);
    }

    @GetMapping("spice_level/{spiceType}")
    @JsonView(Recipe.NonImage.class)
    @Operation(summary = "get recipes by spice level")
    public Iterable<Recipe> getRecipeBySpiceType(@PathVariable SpiceLevel spiceType){
        return handleEmptyResult(recipeService.getRecipeBySpiceType(spiceType), "Spice Level", spiceType);
    }

    private Iterable<Recipe> handleEmptyResult(Iterable<Recipe> result, String parameterName, Object parameterValue) {
        if (!result.iterator().hasNext()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, we don't have any recipes for " + parameterName + " of " + parameterValue);
        }
        return result;
    }

    @PatchMapping("")
    @Operation(summary = "update recipe")
    public Recipe updateRecipe(@RequestBody Recipe incompleteRecipe){
        return recipeService.updateRecipe(incompleteRecipe);
    }
//    @PatchMapping("/rating")
//    @Operation(summary = "rate recipe")
//    public Recipe rateRecipe(@RequestBody @JsonView({Recipe.Rate.class}) Rating rating){
//        return recipeService.rateRecipe(rating);
//    }
    @GetMapping("/coffee")
    @Operation(summary = "get coffee")
    public void getCoffee(){
        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Sorry, I don't know how to make coffee");
    }


    @GetMapping(value = "/image/{recipeId}")
    public ResponseEntity<byte[]> getRecipeImage(@PathVariable Long recipeId) {
        Optional <Recipe> recipeImage = recipeService.getRecipeForImage(recipeId);
        if (recipeImage.isPresent()) {
            Recipe recipe = recipeImage.get();
            byte[] imageBytes = java.util.Base64.getDecoder().decode(recipe.getImage());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/min/{rating}")
    @Operation(summary = "get recipes rated at least the number provided")
    @JsonView(Recipe.NonImage.class)
    public Iterable<Recipe> getRecipesRatedAtLeast(@PathVariable Double rating){
        return handleEmptyResult(ratingService.getRecipeByRatingGreaterThanOrEqual(rating), "Rating", rating);
    }
    @GetMapping("/favourite/{person}")
    @Operation(summary = "get recipes the user has marked as favourite")
    @JsonView(Recipe.NonImage.class)
    public Iterable<Recipe> getFavouriteRecipes(@PathVariable Long person){
        return handleEmptyResult(ratingService.getFavouriteRecipesByUser(person),"favourite",person);
    }

}
