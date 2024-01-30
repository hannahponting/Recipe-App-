package com.recipe.controllers;

import com.recipe.entities.CredentialPair;
import com.recipe.services.CredentialService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class CredentialController {
    CredentialService credentialService;
    @Autowired
    public CredentialController(CredentialService credentialService){this.credentialService = credentialService;}

    @PostMapping("/setPassword")
    @Operation(summary = "save a new password")
    @ResponseStatus(HttpStatus.CREATED)
    public String updatePassword(@RequestBody CredentialPair credentialPair) {
    return credentialService.generateCredential(credentialPair.getEmail(), credentialPair.getPassword());
    }

    @PostMapping("/login")
    @Operation(summary = "log in to an account")
    public boolean login(@RequestBody CredentialPair credentialPair) {
        return credentialService.validLogin(credentialPair.getEmail(),credentialPair.getPassword());
    }
}
