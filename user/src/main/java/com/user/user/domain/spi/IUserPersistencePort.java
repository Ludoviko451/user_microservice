package com.user.user.domain.spi;

import com.user.user.domain.model.User;
import java.util.Optional;

public interface IUserPersistencePort {

    void saveUser(User user, Long idRole);

    Optional<User> findByEmail(String email);

    Optional<User> findByDni(String dni);

    String encryptPassword(String password);
    boolean matchesPassword(String password, String encodedPassword);
}
