package com.user.user.domain.api.usecases;

import com.user.user.adapters.driven.jpa.mysql.exception.PasswordMismatchException;
import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.configuration.Constants;
import com.user.user.domain.model.Role;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.configuration.security.jwt.JwtTokenProvider;
import com.user.user.domain.api.IUserServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthUseCaseTest {

    @InjectMocks
    private AuthUseCase authUseCase;

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "password");
        Role role = new Role(1L, "ROLE_USER");
        User user = new User(1L, "John", "Doe", "12345678", "123-456-7890", "john.doe@example.com", "encodedPassword", Collections.singletonList(role));

        when(userServicePort.findUserByEmail(anyString())).thenReturn(user);
        when(userPersistencePort.matchesPassword(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.generateToken(any())).thenReturn("generatedToken");

        String token = authUseCase.login(loginDTO);

        assertNotNull(token);
        assertEquals("generatedToken", token);
    }

    @Test
    void testLogin_PasswordMismatch() {
        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "password");
        Role role = new Role(1L, "ROLE_USER");
        User user = new User(1L, "John", "Doe", "12345678", "123-456-7890", "john.doe@example.com", "encodedPassword", Collections.singletonList(role));

        when(userServicePort.findUserByEmail(anyString())).thenReturn(user);
        when(userPersistencePort.matchesPassword(anyString(), anyString())).thenReturn(false);

        assertThrows(PasswordMismatchException.class, () -> authUseCase.login(loginDTO));
    }
}
