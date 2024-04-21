package com.user.user.domain.api.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.user.user.adapters.driven.jpa.mysql.exception.PhoneNumberNotValidException;
import com.user.user.adapters.driven.jpa.mysql.exception.UserNotExistException;
import com.user.user.domain.model.User;
import com.user.user.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User createUser() {

        return new User(1L, "Usuario", "Prueba", "1127049576", "3102156154", "test@example.com", "a2321242", Collections.emptyList());
    }

    @Test
    public void testSaveUser_Success() {
        User user = createUser();
        Long roleId = 1L;

        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(user.getDni())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userUseCase.saveUser(user, roleId));
    }

    @Test
    public void testSaveUser_UserAlreadyExistsByEmail() {
        User existingUser = createUser();
        Long roleId = 1L;

        when(userPersistencePort.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(UserNotExistException.class, () -> userUseCase.saveUser(existingUser, roleId));
    }

    @Test
    public void testSaveUser_UserAlreadyExistsByDni() {
        User existingUser = createUser();
        Long roleId = 1L;

        when(userPersistencePort.findByEmail(existingUser.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(existingUser.getDni())).thenReturn(Optional.of(existingUser));

        assertThrows(UserNotExistException.class, () -> userUseCase.saveUser(existingUser, roleId));
    }
    public void testFindByEmail_ValidEmailExists() {
        User user = createUser();

        when(userPersistencePort.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User foundUser = userUseCase.findByEmail("test@example.com");

        assertEquals(user, foundUser);
    }

    @Test
    public void testFindByEmail_InvalidEmailDoesNotExist() {
        User user = createUser();

        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotExistException.class, () -> userUseCase.findByEmail(user.getEmail()));
    }
    @Test
    public void testSaveUser_InvalidPhoneNumber() {
        User user = new User(1L, "Usuario", "Prueba", "1127049576", "12", "test@example.com", "a2321242", Collections.emptyList());
        Long roleId = 1L;

        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDni(user.getDni())).thenReturn(Optional.empty());

        assertThrows(PhoneNumberNotValidException.class, () -> userUseCase.saveUser(user, roleId));
    }
}