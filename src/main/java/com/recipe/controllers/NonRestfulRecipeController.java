package com.recipe.controllers;


import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/recipes")
public class NonRestfulRecipeController {

    RecipeService recipeService;

    @Autowired
    public NonRestfulRecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/{recipeId}")
    @Operation(summary = "get recipes by id ")
    public String  getRecipeById(@PathVariable Long recipeId, Model model){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        model.addAttribute("recipe", recipe);
        return "old";
    }


}
