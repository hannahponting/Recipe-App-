package com.recipe.entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Rating {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    @JsonView(ReadUpdateDelete.class)
    Long id;
    @ManyToOne
    @JsonView(Recipe.IdOnly.class)
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToOne
    private Person person;

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

    public interface RatingView extends Recipe.IdOnly, Person.IdOnly{}
    public interface ReadUpdateDelete{}
}
