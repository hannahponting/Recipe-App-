package com.recipe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    public Long getId() {
        return id;
    }
    @JsonView(ReadUpdateDelete.class)
    @Id
    @GeneratedValue
    private Long id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @JsonView({CreateReadUpdateDelete.class, ReadUpdateDelete.class})
    @Column(unique = true)
    private String email;
    @JsonView({CreateReadUpdateDelete.class, ReadUpdateDelete.class})
    private String firstName;
    @JsonView({CreateReadUpdateDelete.class, ReadUpdateDelete.class})
    private String lastName;

    public interface CreateReadUpdateDelete{};
    public interface ReadUpdateDelete{};

}
