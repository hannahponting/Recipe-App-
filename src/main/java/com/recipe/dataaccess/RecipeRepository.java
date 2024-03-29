package com.recipe.dataaccess;

import com.recipe.entities.Recipe;
import com.recipe.utilities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public interface RecipeRepository extends ListCrudRepository<Recipe,Long> , QuerydslPredicateExecutor{


    Recipe findRecipeById(Long recipeId);

    Page<Recipe> findAll(Pageable pageable);

    List<Recipe> findAllByIdIn(ArrayList<Long> ids);
    Page<Recipe> findAllByIdIn(ArrayList<Long> ids, Pageable pageable);

    Collection<Recipe> findAllByNameContainingIgnoreCase(String ingredient);

    Collection<Recipe> findAllByServingNo(int servingNo);

    Collection<Recipe> findAllByTimeToCook(String timeToCook);

    Collection<Recipe> findAllByCuisineType(Cuisine cuisineTyp);

    Collection<Recipe> findAllByDifficultyLevel(Difficulty difficultyLevel);

    Iterable<Recipe> findAllByMealType(MealTime mealType);

    Iterable<Recipe> findAllByCostType(Cost costType);

    Iterable<Recipe> findAllBySpiceType(SpiceLevel spiceType);

    @Query(value = "SELECT recipe FROM Recipe recipe INNER JOIN ingredientsList WHERE element(ingredientsList) ILIKE %:ingredient%")
    Collection<Recipe> findAllByIngredientSearch(@Param("ingredient") String ingredient);
    @Query(value = "SELECT recipe.id FROM Recipe recipe INNER JOIN ingredientsList WHERE element(ingredientsList) ILIKE %:ingredient%")
    ArrayList<Long> findRecipeIdByIngredientSearch(@Param("ingredient") String ingredient);

    @Query(value = "SELECT recipe FROM Recipe recipe WHERE cookingMinutes <= :minutes AND cookingMinutes != -1")
    Iterable<Recipe> findRecipeByCookingTimeLessThanOrEqualTo(@Param("minutes") Double minutes);

//    @Query(value = "SELECT recipe FROM Recipe recipe WHERE rating >= :rating")
//    Iterable<Recipe> findRecipeByRatingGreaterThanOrEqualTo(@Param("rating") Double rating);
}
