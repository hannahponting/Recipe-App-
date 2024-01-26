package com.recipe.dataaccess;

import com.recipe.entities.Rating;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends ListCrudRepository<Rating,Long> {
    Optional<Rating> findByRecipeIdAndPersonId(Long id, Long id1);
}
