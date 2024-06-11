package com.user.user.adapters.driving.http.controller;

import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.adapters.driving.http.dto.request.UserRequest;
import com.user.user.adapters.driving.http.mapper.IUserRequestMapper;
import com.user.user.domain.api.IAuthServicePort;
import com.user.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthRestControllerTest {

    private final IAuthServicePort authServicePort = mock(IAuthServicePort.class);
    private final AuthRestController authRestController = new AuthRestController(authServicePort);

    private UserRequest createUser() {

        return new UserRequest("Usuario", "Prueba", "1127049576", "3102156154", "test@example.com", "a2321242");
    }


    @Test
    void testLogin() {
        LoginDTO loginDTO = new LoginDTO("test@gmail.com", "a34a43");
        when(authServicePort.login(loginDTO)).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        ResponseEntity<String> response = authRestController.login(loginDTO);

        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", response.getBody());
    }

}