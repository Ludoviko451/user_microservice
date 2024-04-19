package com.user.user.adapters.driving.http.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
