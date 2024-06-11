package com.user.user.domain.api;

import com.user.user.domain.model.User;

public interface IUserServicePort {

    void saveUser(User user, Long roleId);

    User findUserByEmail(String email);

    String registerTeacher(User user);

    String registerStudent(User user);

    String registerAdmin(User user);

}
