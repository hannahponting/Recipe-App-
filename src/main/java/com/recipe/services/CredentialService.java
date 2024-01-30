package com.recipe.services;

import com.recipe.dataaccess.CredentialRepository;
import com.recipe.dataaccess.PersonRepository;
import com.recipe.entities.Credential;
import com.recipe.entities.Person;
import com.recipe.entities.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class CredentialService {
    CredentialRepository credentialRepository;
    PersonRepository personRepository;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository, PersonRepository personRepository) {
        this.credentialRepository = credentialRepository;
        this.personRepository = personRepository;
    }

    public static boolean isValidPassword(String password) {
        // Minimum 8 characters, at least one uppercase letter, and at least one special character
        String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()\\-+=']).{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
    public static String generateSalt() {
        // Create a byte array to hold the random salt
        byte[] salt = new byte[32];

        // Generate random bytes using SecureRandom
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        // Convert the byte array to a Base64-encoded string
        return Base64.getEncoder().encodeToString(salt);
    }
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        // Combine the password and salt, then hash using SHA-256
        String input = password + salt;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());

        // Convert the byte array to a Base64-encoded string
        return Base64.getEncoder().encodeToString(hash);
    }


    public String generateCredential(String email, String password) {
        Credential newCredential = new Credential();
        Person person = personRepository.findPersonByEmail(email);
        if (person == null) {
            throw new IllegalArgumentException("This email address is not associated with an account.");
        }
        Credential existingCredential = credentialRepository.findCredentialByPersonId(person.getId());
        if (existingCredential != null) {
            newCredential = existingCredential;}
        else{newCredential.setPerson(person);}
        if(!isValidPassword(password)){
            throw new IllegalArgumentException("The password must be at least 8 characters and contain an uppercase and a special character.");
        }
        newCredential.setSalt(generateSalt());
        if(newCredential.getSalt() != null){
            try {
                newCredential.setPasswordHash(hashPassword(password, newCredential.getSalt()));
                    credentialRepository.save(newCredential);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        if(newCredential.getId() != null){
        return "password saved";}
        else throw new RuntimeException("password creation failed");
    }

    public boolean validLogin(String email, String password) {
        Person person = personRepository.findPersonByEmail(email);
        if (person == null) {
            throw new IllegalArgumentException("This email address is not associated with an account.");
        }
        Credential credential = credentialRepository.findCredentialByPersonId(person.getId());
        String salt = credential.getSalt();
        boolean passwordValid;
            try {
                passwordValid = hashPassword(password, salt).equals(credential.getPasswordHash());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            return  passwordValid;
        }
}
