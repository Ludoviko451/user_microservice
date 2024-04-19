package com.user.user.domain.api;

import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.domain.model.User;

public interface IAuthServicePort {

    String registerAdmin(User user);

    String login(LoginDTO loginDTO);

    String registerTeacher(User user);

    String registerStudent(User user);
}
