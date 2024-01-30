package com.recipe.dataaccess;

import com.recipe.entities.Credential;
import org.springframework.data.repository.ListCrudRepository;

public interface CredentialRepository extends ListCrudRepository<Credential, Long> {
    Credential findCredentialByPersonId(Long personId);
}
