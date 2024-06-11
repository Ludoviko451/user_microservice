package com.user.user.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "El nombre es requerido")
    private final String name;

    @NotBlank(message = "La clave es requerida")
    private String password;

    @NotBlank(message = "El apellido es requerido")
    private final String lastName;

    @Pattern(regexp = "\\d+", message = "El documento de identidad debe ser númerico")
    @Size(min = 5, message = "El documento de identidad debe tener al menos 5 numeros")
    private final String dni;

    @Pattern(regexp = "\\d+", message = "El numero de telefono debe ser númerico")
    @NotBlank
    @Size(max = 10)
    private final String phoneNumber;

    @NotBlank(message = "El correo electrónico es requerido")
    @Email(message = "El correo electrónico debe ser válido")
    private final String email;
}
