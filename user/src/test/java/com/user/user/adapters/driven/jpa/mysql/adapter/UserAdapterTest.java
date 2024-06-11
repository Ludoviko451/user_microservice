package com.user.user.adapters.driven.jpa.mysql.adapter;
import com.user.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAdapterTest {

    @InjectMocks
    private UserAdapter userAdapter;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
}
