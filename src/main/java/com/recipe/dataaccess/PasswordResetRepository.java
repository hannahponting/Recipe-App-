package com.recipe.dataaccess;
import com.recipe.entities.PasswordReset;
import org.springframework.data.repository.ListCrudRepository;

public interface PasswordResetRepository extends ListCrudRepository<PasswordReset, Long> {
    PasswordReset findPasswordResetByPersonId(Long personId);
}