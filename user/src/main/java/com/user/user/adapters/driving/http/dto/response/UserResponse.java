package com.user.user.adapters.driving.http.dto.response;

import com.user.user.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserResponse {

    private final Long id;

    private final String name;

    private final String password;

    private final String lastName;

    private final String dni;

    private final String phoneNumber;

    private final String email;

    private final List<Role> role;
}
