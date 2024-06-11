package com.user.user.domain.api;
import com.user.user.adapters.driving.http.dto.request.LoginDTO;


public interface IAuthServicePort {
    String login(LoginDTO loginDTO);
}
