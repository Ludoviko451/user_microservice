package com.user.user.domain.api.usecases;

import com.user.user.adapters.driven.jpa.mysql.exception.PhoneNumberNotValidException;
import com.user.user.adapters.driven.jpa.mysql.exception.UserAlreadyExists;
import com.user.user.adapters.driven.jpa.mysql.exception.UserNotExistException;
import com.user.user.configuration.Constants;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @InjectMocks
    private UserUseCase userUseCase;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser_UserDoesNotExist() {
        User user = new User(1L, "John", "Doe", "12345678", "3102156154", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(any())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userUseCase.saveUser(user, 1L));

        verify(userPersistencePort, times(1)).saveUser(user, 1L);
    }

    @Test
    void testSaveUser_UserAlreadyExistsByEmail() {
        User user = new User(1L, "John", "Doe", "12345678", "3102156154", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExists.class, () -> userUseCase.saveUser(user, 1L));

        verify(userPersistencePort, never()).saveUser(any(), anyLong());
    }

    @Test
    void testSaveUser_UserAlreadyExistsByDni() {
        User user = new User(1L, "John", "Doe", "12345678", "3102156154", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(any())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExists.class, () -> userUseCase.saveUser(user, 1L));

        verify(userPersistencePort, never()).saveUser(any(), anyLong());
    }

    @Test
    void testSaveUser_InvalidPhoneNumber() {
        User user = new User(1L, "John", "Doe", "12345678", "33333333333333", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(any())).thenReturn(Optional.empty());

        assertThrows(PhoneNumberNotValidException.class, () -> userUseCase.saveUser(user, 1L));

        verify(userPersistencePort, never()).saveUser(any(), anyLong());
    }

    @Test
    void testFindUserByEmail_UserExists() {
        User user = new User(1L, "John", "Doe", "12345678", "3102156154", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.of(user));

        User foundUser = userUseCase.findUserByEmail("john.doe@example.com");

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    void testFindUserByEmail_UserDoesNotExist() {
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UserNotExistException.class, () -> userUseCase.findUserByEmail("john.doe@example.com"));
    }

    @Test
    void testRegisterAdmin() {
        User user = new User(1L, "John", "Doe", "12345678", "3102156154", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(any())).thenReturn(Optional.empty());

        String result = userUseCase.registerAdmin(user);

        assertEquals(Constants.ADMIN_CREATED, result);
        verify(userPersistencePort, times(1)).saveUser(user, Constants.ROLE_ADMIN);
    }

    @Test
    void testRegisterTeacher() {
        User user = new User(1L, "John", "Doe", "12345678", "3102156154", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(any())).thenReturn(Optional.empty());

        String result = userUseCase.registerTeacher(user);

        assertEquals(Constants.TEACHER_CREATED, result);
        verify(userPersistencePort, times(1)).saveUser(user, Constants.ROLE_TEACHER);
    }

    @Test
    void testRegisterStudent() {
        User user = new User(1L, "John", "Doe", "12345678", "3102156154", "john.doe@example.com", "password", null);
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(any())).thenReturn(Optional.empty());

        String result = userUseCase.registerStudent(user);

        assertEquals(Constants.STUDENT_CREATED, result);
        verify(userPersistencePort, times(1)).saveUser(user, Constants.ROLE_STUDENT);
    }
}
