package com.user.user.domain.api.models;

import com.user.user.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @Test
    @DisplayName("Should create a new role")
    void shouldCreateANewRole() {
        Role role = new Role(1L, "testName");
        assertEquals(1L, role.getIdRole());
        assertEquals("testName", role.getName());
    }
}
