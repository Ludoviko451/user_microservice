package com.user.user.adapters.driven.jpa.mysql.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("Should create a valid UserEntity")
    void shouldCreateValidUserEntity() {
        Long id = 1L;
        String name = "John";
        String lastName = "Doe";
        String email = "a@a.com";
        String dni = "12345678";
        String password = "1234";
        String phone = "123456789";

        UserEntity userEntity = new UserEntity();
        userEntity.setDni(dni);
        userEntity.setEmail(email);
        userEntity.setId(id);
        userEntity.setName(name);
        userEntity.setLastName(lastName);
        userEntity.setPassword(password);
        userEntity.setPhoneNumber(phone);
        userEntity.setRoles(Collections.emptyList());

        var result = validator.validate(userEntity);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should create an invalid UserEntity")
    void shouldCreateInvalidUserEntity() {
        Long id = 0L;
        String name = "";
        String lastName = "";
        String email = "";
        String dni = "";
        String password = "";
        String phone = "";

        UserEntity userEntity = new UserEntity();
        userEntity.setDni(dni);
        userEntity.setEmail(email);
        userEntity.setId(id);
        userEntity.setName(name);
        userEntity.setLastName(lastName);
        userEntity.setPassword(password);
        userEntity.setPhoneNumber(phone);
        userEntity.setRoles(Collections.emptyList());
        var result = validator.validate(userEntity);

        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should Getters and Setters work")
    void shouldGettersAndSettersWork() {
        Long id = 1L;
        String name = "John";
        String lastName = "Doe";
        String email = "a@a.com";
        String dni = "12345678";
        String password = "1234";
        String phone = "123456789";

        UserEntity userEntity = new UserEntity();
        userEntity.setDni(dni);
        userEntity.setEmail(email);
        userEntity.setId(id);
        userEntity.setName(name);
        userEntity.setLastName(lastName);
        userEntity.setPassword(password);
        userEntity.setPhoneNumber(phone);
        userEntity.setRoles(Collections.emptyList());

        assertEquals(dni, userEntity.getDni());
        assertEquals(email, userEntity.getEmail());
        assertEquals(id, userEntity.getId());
        assertEquals(name, userEntity.getName());
        assertEquals(lastName, userEntity.getLastName());
        assertEquals(password, userEntity.getPassword());
        assertEquals(phone, userEntity.getPhoneNumber());
        assertEquals(Collections.emptyList(), userEntity.getRoles());
    }
}
