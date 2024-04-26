package com.user.user.adapters.driving.http.controller;

import com.user.user.adapters.driven.jpa.mysql.exception.UserNotExistException;
import com.user.user.adapters.driving.http.dto.response.UserResponse;
import com.user.user.adapters.driving.http.mapper.IUserResponseMapper;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRestControllerTest {


    private IUserResponseMapper userResponseMapper = mock(IUserResponseMapper.class);

    private IUserServicePort userServicePort = mock(IUserServicePort.class);

    private UserRestController userRestController = new UserRestController(userResponseMapper, userServicePort);

    private User createUser() {

        return new User(1L, "Usuario", "Prueba", "1127049576", "3102156154", "test@example.com", "a2321242", Collections.emptyList());
    }

    @Test
    void testGetUserByMail_ValidEmail_UserFound() {
        // Mocking user data
        String email = "test@example.com";
        User user = createUser();
        when(userServicePort.findUserByEmail(email)).thenReturn(user);

        // Mocking response mapper
        UserResponse expectedResponse = userResponseMapper.toResponse(user);
        when(userResponseMapper.toResponse(user)).thenReturn(expectedResponse);

        // Testing the endpoint
        ResponseEntity<UserResponse> response = userRestController.getUserByMail(email);

        // Asserting the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}