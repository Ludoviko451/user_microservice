package com.user.user.domain.spi;

import com.user.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserPersistencePort {

    void saveUser(User user, Long idRole);
    List<User> getAllUsers();

    Optional<User> findByEmail(String email);

    Optional<User> findByDni(String dni);
}
