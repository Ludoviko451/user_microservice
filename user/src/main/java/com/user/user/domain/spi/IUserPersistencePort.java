package com.user.user.domain.spi;

import com.user.user.domain.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface IUserPersistencePort {

    void saveUser(User user, Long idRole);
    List<User> getAllUsers();

    Optional<User> findByEmail(String email);

    Optional<User> findByDni(String dni);

    Authentication login(String email, String password);

    String encryptPassword(String password);

}
