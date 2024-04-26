package com.user.user.adapters.driving.http.controller;

import com.user.user.adapters.driving.http.dto.response.UserResponse;
import com.user.user.adapters.driving.http.mapper.IUserResponseMapper;
import com.user.user.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {


    private final IUserResponseMapper userResponseMapper;
    private final IUserServicePort userServicePort;

    @GetMapping("/userByEmail")
    public ResponseEntity<UserResponse> getUserByMail(@RequestParam("email") String email) {
        // Tu lógica para obtener el rol del usuario por su ID aquí
        return ResponseEntity.ok(userResponseMapper.toResponse(userServicePort.findUserByEmail(email)));
    }
}
