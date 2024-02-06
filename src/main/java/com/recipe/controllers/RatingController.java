package com.recipe.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.recipe.entities.Rating;
import com.recipe.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService){this.ratingService = ratingService;}

    @PostMapping("")
    @Operation(summary = "submit a new rating")
    @JsonView(Rating.RatingView.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Rating addRating(@RequestBody Rating rating) {
        Rating newRating;
        try {
            newRating = ratingService.addRating(rating);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return newRating;
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "get the average rating for a recipe")
    public Double getRecipeRating(@PathVariable Long recipeId){
        return ratingService.getRecipeRating(recipeId);
    }

    @GetMapping("favourite/{personId}/{recipeId}")
    @Operation(summary = "check weather the user like a recipe or not")
    public Boolean IsRecipeFavourite(@PathVariable Long personId, @PathVariable Long recipeId  ){
        return ratingService.IsRecipeFavouriteByUserId(personId,recipeId);
    }




}
