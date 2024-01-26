package com.recipe.dataaccess;

import com.recipe.entities.Rating;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface RatingRepository extends ListCrudRepository<Rating,Long> {
    Optional<Rating> findByRecipeIdAndPersonId(Long id, Long id1);

    @Query("SELECT AVG(r.myRating) FROM Rating r WHERE r.recipeId = :recipeId")
    Double findAverageMyRatingByRecipeId(@Param("recipeId") Long recipeId);
}
