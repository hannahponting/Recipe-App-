package com.recipe.entities;

import com.fasterxml.jackson.annotation.JsonView;

public class Rating {
    private Recipe recipe;

    @JsonView(Recipe.Rate.class)
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getMyRating() {
        return myRating;
    }

    public void setMyRating(int myRating) {
        this.myRating = myRating;
    }
    @JsonView(Recipe.Rate.class)
    private int myRating;
}
