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

    private IAuthServicePort authServicePort = mock(IAuthServicePort.class);
    private IUserRequestMapper userRequestMapper = mock(IUserRequestMapper.class);
    private AuthRestController authRestController = new AuthRestController(authServicePort, userRequestMapper);

    private UserRequest createUser() {

        return new UserRequest("Usuario", "Prueba", "1127049576", "3102156154", "test@example.com", "a2321242");
    }


    @Test
    public void testLogin() {
        LoginDTO loginDTO = new LoginDTO("test@gmail.com", "a34a43");
        when(authServicePort.login(loginDTO)).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        ResponseEntity<String> response = authRestController.login(loginDTO);

        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", response.getBody());
    }

    @Test
    public void testRegisterAdmin() {
        UserRequest userRequest = createUser();

        User user = userRequestMapper.addRequestToUser(userRequest);
        when(authServicePort.registerAdmin(user)).thenReturn("Usuario Creado Satisfactoriamente");

        ResponseEntity<String> response = authRestController.registerAdmin(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario Creado Satisfactoriamente", response.getBody());
    }

    @Test
    public void testRegisterTeacher() {
        UserRequest userRequest = createUser();

        User user = userRequestMapper.addRequestToUser(userRequest);

        when(authServicePort.registerTeacher(user)).thenReturn("Usuario Creado Satisfactoriamente");

        ResponseEntity<String> response = authRestController.registerTeacher(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario Creado Satisfactoriamente", response.getBody());
    }

    @Test
    public void testRegisterStudent() {

        UserRequest userRequest = createUser();

        User user = userRequestMapper.addRequestToUser(userRequest);

        when(authServicePort.registerStudent(user)).thenReturn("Usuario Creado Satisfactoriamente");

        ResponseEntity<String> response = authRestController.registerStudent(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario Creado Satisfactoriamente", response.getBody());
    }
}