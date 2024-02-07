package com.recipe.entities;

public class CredentialPair {
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private String email;
    private String password;

    public String getResetCode() {
        return resetCode;
    }

    private String resetCode;
}
