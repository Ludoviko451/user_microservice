package com.user.user.adapters.driven.jpa.mysql.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleEntityTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("Should create a valid RoleEntity")
    void shouldCreateValidRoleEntity() {
        Long id = 1L;
        String name = "admin";

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(id);
        roleEntity.setName(name);
        var result = validator.validate(roleEntity);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should create a invalid RoleEntity")
    void shouldCreateInvalidRoleEntity() {
        Long id = 0L;
        String name = "";

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(id);
        roleEntity.setName(name);
        var result = validator.validate(roleEntity);

        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should Getters and Setters work")
    void shouldGettersAndSettersWork() {
        Long id = 1L;
        String name = "admin";
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(id);
        roleEntity.setName(name);
        assertEquals(id, roleEntity.getIdRole());
        assertEquals(name, roleEntity.getName());
    }
}
