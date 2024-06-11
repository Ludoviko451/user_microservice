package com.user.user.adapters.driven.jpa.mysql.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class CustomUserDetailsTest {

    private UserEntity userEntity;
    private RoleEntity roleUser;
    private RoleEntity roleAdmin;

    @BeforeEach
    public void setUp() {
        roleUser = new RoleEntity(1L, "ROLE_USER");
        roleAdmin = new RoleEntity(2L, "ROLE_ADMIN");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPassword("password");
        userEntity.setRoles(Arrays.asList(roleUser, roleAdmin));
    }

    @Test
    public void testBuild() {
        CustomUserDetails userDetails = CustomUserDetails.build(userEntity, userEntity.getRoles());

        assertNotNull(userDetails);
        assertEquals(userEntity.getId(), userDetails.getId());
        assertEquals(userEntity.getName(), userDetails.getName());
        assertEquals(userEntity.getEmail(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    public void testGetAuthorities() {
        CustomUserDetails userDetails = CustomUserDetails.build(userEntity, userEntity.getRoles());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    public void testIsAccountNonExpired() {
        CustomUserDetails userDetails = new CustomUserDetails();
        assertFalse(userDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        CustomUserDetails userDetails = new CustomUserDetails();
        assertFalse(userDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        CustomUserDetails userDetails = new CustomUserDetails();
        assertFalse(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        CustomUserDetails userDetails = new CustomUserDetails();
        assertFalse(userDetails.isEnabled());
    }
}
