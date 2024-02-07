package com.recipe.entities;

public class PasswordResetPair {
    public String getEmail() {
        return email;
    }

    public String getResetCode() {
        return resetCode;
    }

    private String email;
    private String resetCode;
}
