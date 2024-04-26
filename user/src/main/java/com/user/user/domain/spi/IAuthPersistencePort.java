package com.user.user.domain.spi;

import com.user.user.domain.model.User;

public interface IAuthPersistencePort {

    String registerAdmin(User user);

    String login(String email, String password);

    String registerTeacher(User user);

    String registerStudent(User user);


}
