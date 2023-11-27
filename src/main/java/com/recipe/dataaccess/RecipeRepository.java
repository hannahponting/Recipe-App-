package com.recipe.dataaccess;

import com.recipe.entities.Recipe;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.ListCrudRepository;

@Repository
public interface RecipeRepository extends ListCrudRepository<Recipe,Long>{
}
