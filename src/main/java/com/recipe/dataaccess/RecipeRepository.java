package com.recipe.dataaccess;

import com.recipe.entities.Recipe;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RecipeRepository extends ListCrudRepository<Recipe,Long>{

    List<Recipe> findRecipeById(Long recipeId);

    Collection<Recipe> findAllByNameContainingIgnoreCase(String ingredient);
}
