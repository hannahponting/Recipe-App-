package com.recipe.dataaccess;

import com.recipe.entities.Recipe;
import com.recipe.utilities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RecipeRepository extends ListCrudRepository<Recipe,Long>{

    List<Recipe> findRecipeById(Long recipeId);

    Collection<Recipe> findAllByNameContainingIgnoreCase(String ingredient);

    Collection<Recipe> findAllByServingNo(int servingNo);

    Collection<Recipe> findAllByTimeToCook(String timeToCook);

    Collection<Recipe> findAllByCuisineType(Cuisine cuisineTyp);

    Collection<Recipe> findAllByDifficultyLevel(Difficulty difficultyLevel);

    Iterable<Recipe> findAllByMealType(MealTime mealType);

    Iterable<Recipe> findAllByCostType(Cost costType);

    Iterable<Recipe> findAllBySpiceType(SpiceLevel spiceType);

    @Query(value = "SELECT recipe FROM Recipe recipe INNER JOIN ingredientsList ON ID=ID WHERE element(ingredientsList) ILIKE %:ingredient%")
    Collection<Recipe> findAllByIngredientSearch(@Param("ingredient") String ingredient);

}
