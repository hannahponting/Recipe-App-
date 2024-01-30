package com.recipe.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.recipe.dataaccess.RecipePredicatesBuilder;
import com.recipe.dataaccess.RecipeRepository;
import com.recipe.entities.Rating;
import com.recipe.entities.Recipe;
import com.recipe.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


@Service
public class RecipeService {

    RecipeRepository recipeRepository;
      
    @Autowired
    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Recipe addRecipe(Recipe recipe) {
        if (Stream.of(recipe.getName(),recipe.getIngredientsList(),recipe.getInstructions(),recipe.getCuisineType(),recipe.getCostType(),recipe.getServingNo(),recipe.getTimeToCook(),recipe.getDifficultyLevel(),recipe.getMealType(),recipe.getSpiceType()).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("All fields are mandatory other than ID which is generated by the service.");
        }
        if (recipe.getId() != null) {
            throw new IllegalArgumentException("The unique ID is generated by the service and does not need to be provided.");
        }
        if (recipe.getName() == null){
            throw new IllegalArgumentException("The recipe must have a name");
        }
        if (recipe.getInstructions() == null){
            throw new IllegalArgumentException("The recipe must have instructions");
        }
        if (recipe.getIngredientsList() == null){
            throw new IllegalArgumentException("The recipe must have instructions");
        }
        recipeRepository.save(recipe);
        return recipe;
    }

  
    public String deleteById(long id){
        Recipe proposedDeletion = recipeRepository.findRecipeById(id);
        if (proposedDeletion == null){
            throw new IllegalArgumentException("The specified recipe ID does not exist");
        }
        String name = proposedDeletion.getName();
        recipeRepository.deleteById(id);
        return "The recipe with ID " + id + " and title " + name + " has now been deleted.";
    }



    public Iterable<Recipe> findAll(){
        return this.recipeRepository.findAll();
    }

    public Page<Recipe> paginatedRecipes(Pageable pageable){
        return recipeRepository.findAll(pageable);
    }


    public Recipe getRecipeById(long recipeId) {
        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);
        return recipe.orElse(null);
    }

    public Optional<Recipe> getRecipeForImage(Long id){
        return recipeRepository.findById(id);
    }


    public Iterable<Recipe> findByNameContains(String keyword) {
        return recipeRepository.findAllByNameContainingIgnoreCase(keyword);
    }

    public Recipe updateRecipe(Recipe changesToRecipe){
        if(changesToRecipe ==null) throw new NullPointerException("No recipe entered");
        if(changesToRecipe.getId()==null) throw new IllegalArgumentException("You must specify a recipe ID to edit");
        Recipe oldRecipe= getRecipeById(changesToRecipe.getId());
        Recipe finalRecipe;
        if (oldRecipe == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        finalRecipe = makeChangesToRecipe(changesToRecipe, oldRecipe);
        return recipeRepository.save(finalRecipe);}

//    public Recipe rateRecipe(Rating rating){
//        if(rating.getRecipeId() ==null) throw new NullPointerException("No recipe entered");
//        if(rating.getMyRating() < 1 | rating.getMyRating() > 5 ) throw new IllegalArgumentException("Rating must be an integer between 1 and 5");
//        Recipe oldRecipe= getRecipeById(rating.getRecipeId());
//        Recipe finalRecipe;
//        if (oldRecipe == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
//        finalRecipe = updateRating(rating, oldRecipe);
//        return recipeRepository.save(finalRecipe);}

//    private Recipe updateRating(Rating rating, Recipe oldRecipe){
//        if (oldRecipe.getRatingCount() == 0){
//            oldRecipe.setRating(rating.getMyRating());
//            oldRecipe.setRatingCount();
//        }
//        else {oldRecipe.setRating(((oldRecipe.getRating()* oldRecipe.getRatingCount()) + rating.getMyRating())/(oldRecipe.getRatingCount() + 1));
//        oldRecipe.setRatingCount();}
//        return oldRecipe;
//    }


    private Recipe makeChangesToRecipe(Recipe changesToRecipe, Recipe oldRecipe) {
        if (changesToRecipe.getName() != null) oldRecipe.setName(changesToRecipe.getName());
        if (changesToRecipe.getIngredientsList() != null) oldRecipe.setIngredientsList(changesToRecipe.getIngredientsList());
        if (changesToRecipe.getInstructions() != null) oldRecipe.setInstructions(changesToRecipe.getInstructions());
        if (changesToRecipe.getServingNo() !=0) oldRecipe.setServingNo(changesToRecipe.getServingNo());
        if (changesToRecipe.getCuisineType() != null) oldRecipe.setCuisineType(changesToRecipe.getCuisineType());
        if (changesToRecipe.getTimeToCook() != null) oldRecipe.setTimeToCook(changesToRecipe.getTimeToCook());
        if (changesToRecipe.getCostType() != null) oldRecipe.setCostType(changesToRecipe.getCostType());
        if (changesToRecipe.getDifficultyLevel() != null) oldRecipe.setDifficultyLevel(changesToRecipe.getDifficultyLevel());
        if (changesToRecipe.getMealType() != null)oldRecipe.setMealType(changesToRecipe.getMealType());
        if (changesToRecipe.getSpiceType() != null) oldRecipe.setSpiceType(changesToRecipe.getSpiceType());
        return oldRecipe;
    }

    public Iterable<Recipe> findByIngredientsContain(String ingredient) {
        return recipeRepository.findAllByIngredientSearch(ingredient);
    }


    public Iterable<Recipe> getRecipeByServingNumber(int servingNo) {
        return recipeRepository.findAllByServingNo(servingNo);

    }

    public Iterable<Recipe> getRecipeByCookingTime(String timeToCook) {
        return recipeRepository.findAllByTimeToCook(timeToCook);
    }

    public Iterable<Recipe> getRecipeByCuisineType(Cuisine cuisineType) {
        return recipeRepository.findAllByCuisineType(cuisineType);
    }

    public Iterable<Recipe> getRecipeByDifficultyLevel(Difficulty difficultyLevel) {
        return recipeRepository.findAllByDifficultyLevel(difficultyLevel);

    }

    public Iterable<Recipe> getRecipeByMealType(MealTime mealType) {
        return recipeRepository.findAllByMealType(mealType);
    }

    public Iterable<Recipe> getRecipeByCostType(Cost costType) {
        return recipeRepository.findAllByCostType(costType);
    }

    public Iterable<Recipe> getRecipeBySpiceType(SpiceLevel spiceType) {
        return recipeRepository.findAllBySpiceType(spiceType);

    }

    public Iterable<Recipe> getRecipeByCookingTimeLessThanOrEqual(Double minutes) {
        return recipeRepository.findRecipeByCookingTimeLessThanOrEqualTo(minutes);
    }
//    public Iterable<Recipe> getRecipeByRatingGreaterThanOrEqual(Double rating) {
//        return recipeRepository.findRecipeByRatingGreaterThanOrEqualTo(rating);
//    }
    public Page<Recipe> findRecipeByCustomQuery(String query, Pageable pageable){

        RecipePredicatesBuilder builder = new RecipePredicatesBuilder();

        if (query != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(=|<=|>=)(\\w+?)&");
            Matcher matcher = pattern.matcher(query + "&");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        else {return recipeRepository.findAll(pageable);}
        BooleanExpression exp = builder.build();
        return recipeRepository.findAll(exp, pageable);
    }

    public Iterable<Recipe> findRecipeByMultipleIngredients(String query){
        ArrayList<String> requiredIngredients = new ArrayList<>();
        if (query!= null){
            Pattern pattern = Pattern.compile("(\\w+?)&");
            Matcher matcher = pattern.matcher(query + "&");
            while(matcher.find()){
                requiredIngredients.add(matcher.group(1));
            }
            int numIngredients = requiredIngredients.size();
            ArrayList<ArrayList<Long>> filteredResultsList = new ArrayList<>();
            for (int i = 0; i < numIngredients; i++) {
                filteredResultsList.add(recipeRepository.findRecipeIdByIngredientSearch(requiredIngredients.get(i)));
            }
            ArrayList<Long> commonLongs = findCommonLongs(filteredResultsList);
            return recipeRepository.findAllByIdIn(commonLongs);
        }
        else return null;
    }
        public static ArrayList<Long> findCommonLongs(ArrayList<ArrayList<Long>> listOfLists) {
            ArrayList<Long> commonLongs = new ArrayList<>(listOfLists.get(0));

            for (int i = 1; i < listOfLists.size(); i++) {
                commonLongs.retainAll(listOfLists.get(i));
            }

            return commonLongs;}
    public Recipe addRecipeImage(Recipe recipe, MultipartFile file) throws IOException {
        recipe.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        return recipeRepository.save(recipe);
    }
}
