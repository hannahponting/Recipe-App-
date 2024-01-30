package com.recipe.entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Rating {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    @JsonView(ReadUpdateDelete.class)
    Long id;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    private Long recipeId;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    private Long personId;

    public Integer getMyRating() {
        return myRating;
    }

    public void setMyRating(Integer myRating) {
        this.myRating = myRating;
    }

    private Integer myRating;

    public Boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    private Boolean favourite;

    public interface RatingView {}
    public interface ReadUpdateDelete{}
}
