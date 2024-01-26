package com.recipe.dataaccess;

import com.recipe.entities.Person;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ListCrudRepository<Person,Long> {

    Person findPersonById(Long personId);
    Person findPersonByEmail(String email);
}
