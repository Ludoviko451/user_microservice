package com.user.user.adapters.driving.http.handlers;

import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.adapters.driving.http.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface IAuthHandler {

    public ResponseEntity<AuthResponse> login(LoginDTO loginDTO);

}
