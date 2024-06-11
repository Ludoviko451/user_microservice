package com.user.user.adapters.driving.http.dto.request;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("Should create a valid UserRequest")
    void shouldCreateAValidUserRequest() {
        String name = "John";
        String lastName = "Doe";
        String email = "test@gmail.com";
        String dni = "1123456231";
        String password = "12334125";
        String phone = "3102156154";

        UserRequest userRequest = new UserRequest(name, password, lastName, dni, phone,  email);

        var result = validator.validate(userRequest);
        assertTrue(result.isEmpty());
}
    @Test
    @DisplayName("Should Getters and set password work")
    void shouldGettersWork() {
        String name = "John";
        String lastName = "Doe";
        String email = "a@a.com";
        String dni = "12345678";
        String password = "1234";
        String phone = "123456789";

        UserRequest userRequest = new UserRequest(name, password, lastName, dni, phone,  email);
        String encryptPassword = "encryptPassword";
        userRequest.setPassword(encryptPassword);


        assertEquals(name, userRequest.getName());
        assertEquals(lastName, userRequest.getLastName());
        assertEquals(email, userRequest.getEmail());
        assertEquals(dni, userRequest.getDni());
        assertEquals(encryptPassword, userRequest.getPassword());
        assertEquals(phone, userRequest.getPhoneNumber());
    }

    @Test
    @DisplayName("Should create an invalid UserRequest")
    void shouldCreateAnInvalidUserRequest() {
        String name = "";
        String lastName = "";
        String email = "";
        String dni = "";
        String password = "";
        String phone = "";

        UserRequest userRequest = new UserRequest(name, password, lastName, dni, phone,  email);
        var result = validator.validate(userRequest);

        assertFalse(result.isEmpty());
    }
}
