package com.recipe.entities;

import jakarta.persistence.*;

@Entity
public class Credential {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private String salt;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    private String passwordHash;
}
