package com.recipe.dataaccess;

import com.recipe.entities.Recipe;
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

    @Query(value = "SELECT recipe FROM Recipe recipe INNER JOIN ingredientsList ON ID=ID WHERE element(ingredientsList) LIKE %:ingredient%")
    Collection<Recipe> findAllByIngredientSearch(@Param("ingredient") String ingredient);

}
