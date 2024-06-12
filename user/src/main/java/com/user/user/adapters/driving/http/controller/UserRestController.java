package com.user.user.adapters.driving.http.controller;

import com.user.user.adapters.driving.http.dto.request.UserRequest;
import com.user.user.adapters.driving.http.dto.response.UserResponse;
import com.user.user.adapters.driving.http.mapper.IUserRequestMapper;
import com.user.user.adapters.driving.http.mapper.IUserResponseMapper;
import com.user.user.domain.api.IUserServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {


    private final IUserResponseMapper userResponseMapper;
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody @Valid UserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userServicePort.registerAdmin(userRequestMapper.addRequestToUser(userRequest)));
    }

    @PostMapping("/registerTeacher")
    public ResponseEntity<String> registerTeacher(@RequestBody @Valid UserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userServicePort.registerTeacher(userRequestMapper.addRequestToUser(userRequest)));
    }

    @PostMapping("/registerStudent")
    public ResponseEntity<String> registerStudent(@RequestBody @Valid UserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userServicePort.registerStudent(userRequestMapper.addRequestToUser(userRequest)));
    }
}
