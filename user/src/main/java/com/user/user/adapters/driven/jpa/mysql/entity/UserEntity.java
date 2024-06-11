package com.user.user.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Column(unique = false, nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @Column(unique = false, nullable = false)
    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "El dni no puede estar vacío")
    private String dni;

    @Column(unique = false, nullable = false)
    @NotBlank(message = "El teléfono no puede estar vacío")
    private String phoneNumber;

    @Column(unique = false, nullable = false)
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles = new ArrayList<>();


}
