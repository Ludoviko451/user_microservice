package com.user.user.adapters.driven.jpa.mysql.adapter;
import com.user.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.user.user.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.user.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.user.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.user.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAdapterTest {

    @InjectMocks
    private UserAdapter userAdapter;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {

        User user = new User(1L, "test", "test", "1123456234", "3102000000", "test@example.com", "test", Collections.emptyList());
        Long idRole = 1L;

        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity = new RoleEntity();

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(roleRepository.findById(idRole)).thenReturn(Optional.of(roleEntity));

        userAdapter.saveUser(user, idRole);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testSaveUser_RoleNotFound() {

        User user = new User(1L, "test", "test", "1123456234", "3102000000", "test@example.com", "test", Collections.emptyList());
        Long idRole = 1L;

        when(roleRepository.findById(idRole)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> userAdapter.saveUser(user, idRole));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testFindByEmail() {

        String email = "test@example.com";
        UserEntity userEntity = new UserEntity(1L, "test", "test", "1123456234", "3102000000", "test@example.com", "test", Collections.emptyList());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toModel(userEntity)).thenReturn(new User(1L, "test", "test", "1123456234", "3102000000", "test@example.com", "test", Collections.emptyList()));

        Optional<User> result = userAdapter.findByEmail(email);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByEmail_NotFound() {

        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());


        Optional<User> result = userAdapter.findByEmail(email);


        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByDni() {

        String dni = "12345678A";
        UserEntity userEntity = new UserEntity();

        when(userRepository.findByDni(dni)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toModel(userEntity)).thenReturn(new User(1L, "test", "test", "1123456234", "3102000000", "test@example.com", "test", Collections.emptyList()));


        Optional<User> result = userAdapter.findByDni(dni);


        assertTrue(result.isPresent());
    }

    @Test
    void testFindByDni_NotFound() {

        String dni = "12345678A";

        when(userRepository.findByDni(dni)).thenReturn(Optional.empty());

        Optional<User> result = userAdapter.findByDni(dni);

        assertTrue(result.isEmpty());
    }

    @Test
    void testMatchesPassword_PasswordMatches() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = userAdapter.matchesPassword(rawPassword, encodedPassword);

        assertTrue(result);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }

    @Test
    void testMatchesPassword_PasswordDoesNotMatch() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = userAdapter.matchesPassword(rawPassword, encodedPassword);

        assertFalse(result);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }

    @Test
    void testEncryptPassword() {
        // Given
        String rawPassword = "password";
        String encryptedPassword = "encryptedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encryptedPassword);

        // When
        String result = userAdapter.encryptPassword(rawPassword);

        // Then
        assertEquals(encryptedPassword, result);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }
}

