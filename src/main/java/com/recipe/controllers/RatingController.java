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
}
