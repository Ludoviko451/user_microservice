package com.user.user.adapters.driving.http.controller;

import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.domain.api.IAuthServicePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    public AuthRestController(IAuthServicePort authServicePort) {
        this.authServicePort = authServicePort;
    }

    private final IAuthServicePort authServicePort;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        return ResponseEntity.ok(authServicePort.login(loginDTO));
    }
}
