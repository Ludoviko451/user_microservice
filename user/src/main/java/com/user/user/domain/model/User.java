package com.user.user.domain.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final Long id;
    private final String name;

    private final String lastName;

    private final String dni;

    private final String phoneNumber;

    private final String email;


    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    private List<Role> role;

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public User(Long id, String name, String lastName, String dni, String phoneNumber, String email, String password, List<Role> role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDni() {
        return dni;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

}
