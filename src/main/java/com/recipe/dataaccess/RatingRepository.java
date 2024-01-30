package com.recipe.dataaccess;

import com.recipe.entities.Rating;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface RatingRepository extends ListCrudRepository<Rating,Long> {
    Optional<Rating> findByRecipeIdAndPersonId(Long id, Long id1);

    @Query("SELECT AVG(r.myRating) FROM Rating r WHERE r.recipeId = :recipeId")
    Double findAverageMyRatingByRecipeId(@Param("recipeId") Long recipeId);

    @Query("SELECT r.recipeId FROM Rating r GROUP BY r.recipeId HAVING AVG(r.myRating) >= :rating")
    ArrayList<Long> findRecipesRatedAtLeast(@Param("rating") Double rating);

    @Query("SELECT r.recipeId from Rating r WHERE r.personId = :person AND r.favourite=true")
    ArrayList<Long> findFavouriteRecipesByUser(@Param("person") Long person);
}
