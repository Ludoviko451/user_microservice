package com.user.user.domain.api.usecases;

import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthUseCaseTest {


    private IUserServicePort userServicePort = mock(IUserServicePort.class);
    private JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
    private IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
    private AuthUseCase authUseCase = new AuthUseCase(userServicePort, jwtTokenProvider, userPersistencePort);

    private User createUser() {

        return new User(1L, "Usuario", "Prueba", "1127049576", "3102156154", "test@example.com", "a2321242", Collections.emptyList());
    }

    @Test
    public void testRegisterAdmin() {
        User user = createUser();
        when(userPersistencePort.encryptPassword(anyString())).thenReturn("encryptedPassword");
        doNothing().when(userServicePort).saveUser(user, 1L);

        String result = authUseCase.registerAdmin(user);

        assertEquals("Usuario Creado Satisfactoriamente", result);
    }

    @Test
    public void testLogin() {
        LoginDTO loginDTO = new LoginDTO("test@gmail.com", "password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        when(userPersistencePort.login("test@gmail.com", "password")).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn("token123");

        String token = authUseCase.login(loginDTO);

        assertEquals("token123", token);
    }

    @Test
    public void testRegisterTeacher() {
        User user = createUser();
        when(userPersistencePort.encryptPassword(anyString())).thenReturn("encryptedPassword");
        doNothing().when(userServicePort).saveUser(user, 2L);

        String result = authUseCase.registerTeacher(user);

        assertEquals("Usuario Creado Satisfactoriamente", result);
    }

    @Test
    public void testRegisterStudent() {
        User user = createUser();
        when(userPersistencePort.encryptPassword(anyString())).thenReturn("encryptedPassword");
        doNothing().when(userServicePort).saveUser(user, 3L);

        String result = authUseCase.registerStudent(user);

        assertEquals("Usuario Creado Satisfactoriamente", result);

    }
}