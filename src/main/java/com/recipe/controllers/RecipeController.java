package com.recipe.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import com.recipe.utilities.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
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
    public Iterable<Recipe> getRecipe(){
        return recipeService.findAll();
    }
    @GetMapping("/search/name/{keyword}")
    @Operation(summary = "get recipes by keyword in recipe name")
    public Iterable<Recipe> getRecipeByName(@PathVariable String keyword){
        return recipeService.findByNameContains(keyword);
    }
    @GetMapping("search/custom")
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
    @Operation(summary = "get recipes by keyword in ingredients")
    public Iterable<Recipe> getRecipeByIngredient(@PathVariable String ingredient){
        return handleEmptyResult(recipeService.findByIngredientsContain(ingredient),"ingredient",ingredient);
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "get recipes by id ")
    public Recipe getRecipeById(@PathVariable Long recipeId){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");

        return recipe;
    }

    @GetMapping("serving/{servingNo}")
    @Operation(summary = "get recipes by number of servings")
    public Iterable<Recipe> getRecipeByServingNumber(@PathVariable int servingNo){
        return handleEmptyResult(recipeService.getRecipeByServingNumber(servingNo),"Serving Number",servingNo);
    }

    @GetMapping("cooking_time/{timeToCook}")
    @Operation(summary = "get recipes by cooking time ")
    public Iterable<Recipe> getRecipeByCookingTime(@PathVariable String timeToCook){
        return handleEmptyResult(recipeService.getRecipeByCookingTime(timeToCook), "Cooking Time", timeToCook);
    }
    @GetMapping("cooking_time_minutes/{minutes}")
    @Operation(summary = "get recipes with a cooking time less than or equal to a number of minutes")
    public Iterable<Recipe> getRecipeByCookingTimeLessThanOrEqual(@PathVariable Double minutes){
        return handleEmptyResult(recipeService.getRecipeByCookingTimeLessThanOrEqual(minutes), "Cooking Time", minutes);
    }

    @GetMapping("cuisine/{cuisineType}")
    @Operation(summary = "get recipes by cuisine type ")
    public Iterable<Recipe> getRecipeByCuisineType(@PathVariable Cuisine cuisineType){

        return handleEmptyResult(recipeService.getRecipeByCuisineType(cuisineType), "Cuisine Type", cuisineType);
    }

    @GetMapping("difficulty/{difficultyLevel}")
    @Operation(summary = "get recipes by difficulty level ")
    public Iterable<Recipe> getRecipeByDifficultyLevel(@PathVariable Difficulty difficultyLevel){

        return handleEmptyResult(recipeService.getRecipeByDifficultyLevel(difficultyLevel), "Difficulty Level", difficultyLevel);
    }

    @GetMapping("meal_type/{mealType}")
    @Operation(summary = "get recipes by meal type")
    public Iterable<Recipe> getRecipeByMealType(@PathVariable MealTime mealType){
        return handleEmptyResult(recipeService.getRecipeByMealType(mealType), "Meal Type", mealType);
    }

    @GetMapping("cost/{costType}")
    @Operation(summary = "get recipes by cost type")
    public Iterable<Recipe> getRecipeByCostType(@PathVariable Cost costType){
        return handleEmptyResult(recipeService.getRecipeByCostType(costType), "Cost Type", costType);
    }

    @GetMapping("spice_level/{spiceType}")
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
    @GetMapping("/coffee")
    @Operation(summary = "get coffee")
    public void getCoffee(){
        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Sorry, I don't know how to make coffee");
    }
}
