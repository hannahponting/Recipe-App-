package com.recipe.controllers;

import com.recipe.entities.CredentialPair;
import com.recipe.entities.PasswordResetPair;
import com.recipe.services.CredentialService;
import com.recipe.services.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @Autowired
    PasswordResetService passwordResetService;
    @Autowired
    public CredentialController(CredentialService credentialService, PasswordResetService passwordResetService){
        this.credentialService = credentialService;
    this.passwordResetService = passwordResetService;}

    @PostMapping("/setPassword")
    @Operation(summary = "save a new password")
    @ResponseStatus(HttpStatus.CREATED)
    public String updatePassword(@RequestBody CredentialPair credentialPair) {
    return credentialService.generateCredential(credentialPair.getEmail(), credentialPair.getPassword(), credentialPair.getResetCode());
    }

    @PostMapping("/login")
    @Operation(summary = "log in to an account")
    public boolean login(@RequestBody CredentialPair credentialPair) {
        return credentialService.validLogin(credentialPair.getEmail(),credentialPair.getPassword());
    }
    @GetMapping("/reset/{email}")
    @Operation(summary = "trigger password reset")
    public String requestReset(@PathVariable String email) {
        return passwordResetService.generateResetCode(email);
    }
}
