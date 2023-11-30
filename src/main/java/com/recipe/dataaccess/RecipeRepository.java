package com.recipe.dataaccess;

import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recipe.entities.QRecipe;
import com.recipe.entities.Recipe;
import com.recipe.utilities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface RecipeRepository extends ListCrudRepository<Recipe,Long> , QuerydslPredicateExecutor {


    Recipe findRecipeById(Long recipeId);

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
    @Query(value = "SELECT recipe FROM Recipe recipe WHERE cookingMinutes <= :minutes AND cookingMinutes != -1")
    Iterable<Recipe> findRecipeByCookingTimeLessThanOrEqualTo(@Param("minutes") Double minutes);
}
