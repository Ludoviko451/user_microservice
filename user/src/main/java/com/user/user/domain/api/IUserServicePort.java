package com.user.user.domain.api;

import com.user.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserServicePort {

    void saveUser(User user, Long roleId);

    List<User> getAllUsers();

    User findByEmail(String email);
}
