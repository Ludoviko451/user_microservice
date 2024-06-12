package com.user.user.adapters.driving.http.controller;

import com.user.user.adapters.driven.jpa.mysql.exception.UserNotExistException;
import com.user.user.adapters.driving.http.dto.request.UserRequest;
import com.user.user.adapters.driving.http.dto.response.UserResponse;
import com.user.user.adapters.driving.http.mapper.IUserRequestMapper;
import com.user.user.adapters.driving.http.mapper.IUserResponseMapper;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    private final IUserResponseMapper userResponseMapper = mock(IUserResponseMapper.class);
    private final IUserServicePort userServicePort = mock(IUserServicePort.class);
    private final IUserRequestMapper userRequestMapper = mock(IUserRequestMapper.class);
    private final UserRestController userRestController = new UserRestController(userResponseMapper, userServicePort, userRequestMapper);

    private User createUser() {
        return new User(1L, "Usuario", "Prueba", "1127049576", "3102156154", "test@example.com", "a2321242", Collections.emptyList());
    }

    private UserRequest createUserRequest() {
        return new UserRequest("Usuario", "Prueba", "1127049576", "3102156154", "test@example.com", "a2321242");
    }

    @Test
    void testRegisterAdmin_Success() {
        UserRequest userRequest = createUserRequest();
        User user = createUser();
        when(userRequestMapper.addRequestToUser(any(UserRequest.class))).thenReturn(user);
        when(userServicePort.registerAdmin(any(User.class))).thenReturn("Admin created successfully");

        ResponseEntity<String> response = userRestController.registerAdmin(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Admin created successfully", response.getBody());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServicePort).registerAdmin(userCaptor.capture());
        assertEquals("test@example.com", userCaptor.getValue().getEmail());
    }

    @Test
    void testRegisterTeacher_Success() {
        UserRequest userRequest = createUserRequest();
        User user = createUser();
        when(userRequestMapper.addRequestToUser(any(UserRequest.class))).thenReturn(user);
        when(userServicePort.registerTeacher(any(User.class))).thenReturn("Teacher created successfully");

        ResponseEntity<String> response = userRestController.registerTeacher(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Teacher created successfully", response.getBody());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServicePort).registerTeacher(userCaptor.capture());
        assertEquals("test@example.com", userCaptor.getValue().getEmail());
    }

    @Test
    void testRegisterStudent_Success() {
        UserRequest userRequest = createUserRequest();
        User user = createUser();
        when(userRequestMapper.addRequestToUser(any(UserRequest.class))).thenReturn(user);
        when(userServicePort.registerStudent(any(User.class))).thenReturn("Student created successfully");

        ResponseEntity<String> response = userRestController.registerStudent(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Student created successfully", response.getBody());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServicePort).registerStudent(userCaptor.capture());
        assertEquals("test@example.com", userCaptor.getValue().getEmail());
    }

}
