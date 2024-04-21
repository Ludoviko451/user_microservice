package com.user.user.adapters.driving.http.controller;

import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.adapters.driving.http.dto.request.UserRequest;
import com.user.user.adapters.driving.http.dto.response.AuthResponse;
import com.user.user.adapters.driving.http.handlers.IAuthHandler;
import com.user.user.adapters.driving.http.mapper.IUserRequestMapper;
import com.user.user.domain.api.IAuthServicePort;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    public AuthRestController(IAuthServicePort authServicePort, IUserRequestMapper userRequestMapper) {
        this.authServicePort = authServicePort;
        this.userRequestMapper = userRequestMapper;
    }

    private final IAuthServicePort authServicePort;
   private final IUserRequestMapper userRequestMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        return ResponseEntity.ok(authServicePort.login(loginDTO));
    }


    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody @Valid UserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authServicePort.registerAdmin(userRequestMapper.addRequestToUser(userRequest)));
    }

    @PostMapping("/registerTeacher")
    public ResponseEntity<String> registerTeacher(@RequestBody @Valid UserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authServicePort.registerTeacher(userRequestMapper.addRequestToUser(userRequest)));
    }

    @PostMapping("/registerStudent")
    public ResponseEntity<String> registerStudent(@RequestBody @Valid UserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authServicePort.registerStudent(userRequestMapper.addRequestToUser(userRequest)));
    }


}
