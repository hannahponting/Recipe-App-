package com.recipe.services;

import com.recipe.dataaccess.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class RecipeService {

    public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }
}
