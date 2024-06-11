package com.user.user.domain.api.models;

import com.user.user.domain.model.Role;
import com.user.user.domain.model.User;
import com.user.user.testData.DomainData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

     @Test
     @DisplayName("Should create a new user")
     void shouldCreateANewUser() {

         Role role = DomainData.getRole();
         User user = new User(1L, "testFirstName", "testLastName", "1123445234", "3102156154", "testEmail@gmail.com", "testPassword", List.of(role));
         user.setRole(List.of(role));
         assertEquals(1L, user.getId());
         assertEquals("testFirstName", user.getName());
         assertEquals("testLastName", user.getLastName());
         assertEquals("1123445234", user.getDni());
         assertEquals("3102156154", user.getPhoneNumber());
         assertEquals("testEmail@gmail.com", user.getEmail());
         assertEquals("testPassword", user.getPassword());
         assertEquals(List.of(role), user.getRole());
     }

}
