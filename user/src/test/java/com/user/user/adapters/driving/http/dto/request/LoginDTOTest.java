package com.user.user.adapters.driving.http.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("Should accept valid login DTO")
    void shouldAcceptValidLoginDTO() {
        String email = "username@test.com";
        String password = "password";

        LoginDTO loginDTO = new LoginDTO(email, password);
        var result = validator.validate(loginDTO);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should reject login DTO with empty username")
    void shouldRejectLoginDTOWithEmptyUsername() {
        LoginDTO loginDTO = new LoginDTO("", "password");

        var result = validator.validate(loginDTO);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should reject login DTO with empty password")
    void shouldRejectLoginDTOWithEmptyPassword() {
        LoginDTO loginDTO = new LoginDTO("username@test.com", "");

        var result = validator.validate(loginDTO);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should reject login DTO with invalid email")
    void shouldRejectLoginDTOWithInvalidEmail() {
        LoginDTO loginDTO = new LoginDTO("username", "password");

        var result = validator.validate(loginDTO);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should Getters and Setters")
    void shouldGettersAndSetters() {
        LoginDTO loginDTO = new LoginDTO("username@test.com", "password");
        loginDTO.setEmail("username@test.com");
        loginDTO.setPassword("password");
        assertEquals("username@test.com", loginDTO.getEmail());
        assertEquals("password", loginDTO.getPassword());
    }
}
