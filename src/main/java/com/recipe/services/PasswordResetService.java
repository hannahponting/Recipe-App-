package com.recipe.services;

import com.recipe.dataaccess.CredentialRepository;
import com.recipe.dataaccess.PasswordResetRepository;
import com.recipe.dataaccess.PersonRepository;
import com.recipe.entities.Credential;
import com.recipe.entities.PasswordReset;
import com.recipe.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Random;

import static com.recipe.services.CredentialService.generateSalt;
import static com.recipe.services.CredentialService.hashPassword;

@Service
public class PasswordResetService {

    @Autowired
    public PasswordResetService(PasswordResetRepository passwordResetRepository, PersonRepository personRepository, CredentialRepository credentialRepository) {
        this.passwordResetRepository = passwordResetRepository;
        this.personRepository = personRepository;
        this.credentialRepository = credentialRepository;
    }
    PasswordResetRepository passwordResetRepository;
    PersonRepository personRepository;
    CredentialRepository credentialRepository;

    private static int generateResetCode () {
        int min = 100000; // Minimum value (inclusive)
        int max = 999999; // Maximum value (exclusive)
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        System.out.println("Reset code: " + randomNumber);
        return randomNumber;
    }
    public String generateResetCode(String email) {
        PasswordReset passwordReset = new PasswordReset();
        Person person = personRepository.findPersonByEmail(email);
        if (person == null) {
            throw new IllegalArgumentException("This email address is not associated with an account.");
        }
        PasswordReset existingReset = passwordResetRepository.findPasswordResetByPersonId(person.getId());
        if (existingReset != null) {
            passwordReset = existingReset;}
        else{passwordReset.setPerson(person);}
        passwordReset.setSalt(generateSalt());
        String resetCode = String.valueOf(generateResetCode());
        if(passwordReset.getSalt() != null){
            try {
                passwordReset.setResetCodeHash(hashPassword(resetCode, passwordReset.getSalt()));
                passwordReset.setExpiry(LocalDateTime.now().plusMinutes(30));
                passwordResetRepository.save(passwordReset);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        if(passwordReset.getId() != null){
            return "Code generated";}
        else throw new RuntimeException("code creation failed");
    }

    public boolean validReset(String email, String resetCode) {
        try{
            Person person = personRepository.findPersonByEmail(email);
            if (person == null) {
                throw new IllegalArgumentException("This email address is not associated with an account.");
            }
            PasswordReset passwordReset = passwordResetRepository.findPasswordResetByPersonId(person.getId());
            if (passwordReset == null) {
                throw new IllegalArgumentException("Invalid password reset");
            }
            String salt = passwordReset.getSalt();
            boolean passwordResetValid = false;
            try {
                if(passwordReset.getExpiry().isAfter(LocalDateTime.now())){
                passwordResetValid = hashPassword(resetCode, salt).equals(passwordReset.getResetCodeHash());
                if(passwordResetValid){
                    passwordReset.setExpiry(LocalDateTime.now());
                    passwordResetRepository.save(passwordReset);
                }}
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            return  passwordResetValid;
        }
        catch(IllegalArgumentException e){return false;}
    }
}
