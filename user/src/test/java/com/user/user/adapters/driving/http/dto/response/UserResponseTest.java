package com.user.user.adapters.driving.http.dto.response;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
 class UserResponseTest {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("User Response should be valid")
    void userResponseShouldBeValid() {
        UserResponse userResponse = new UserResponse(1L, "name", "password", "lastName", "dni", "phoneNumber", "email", Collections.emptyList());
        var violations = validator.validate(userResponse);
        assertTrue(violations.isEmpty());
    }
    @Test
    @DisplayName("Should Getters work")
    void shouldGettersWork() {
        UserResponse userResponse = new UserResponse(1L, "name", "password", "lastName", "dni", "phoneNumber", "email", Collections.emptyList());
        assertEquals(1L, (long) userResponse.getId());
        assertEquals("name", userResponse.getName());
        assertEquals("password", userResponse.getPassword());
        assertEquals("lastName", userResponse.getLastName());
        assertEquals("dni", userResponse.getDni());
        assertEquals("phoneNumber", userResponse.getPhoneNumber());
        assertEquals("email", userResponse.getEmail());
        assertEquals(Collections.emptyList(), userResponse.getRole());
    }

}
