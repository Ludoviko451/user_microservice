package com.user.user.adapters.driving.http.handlers.impl;

import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.user.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.user.user.adapters.driving.http.dto.request.LoginDTO;
import com.user.user.adapters.driving.http.dto.response.AuthResponse;
import com.user.user.adapters.driving.http.handlers.IAuthHandler;
import com.user.user.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthHandlerImpl implements IAuthHandler {

    private final IUserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthHandlerImpl(IUserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginDTO loginDTO) {
        // Buscar el usuario por su correo electrónico
        UserEntity userEntity = userRepository.findByEmail(loginDTO.getEmail()).orElse(null);
        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Verificar la contraseña
        if (!verifyPassword(loginDTO.getPassword(), userEntity.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Autenticar al usuario
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        String token = jwtTokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    private boolean verifyPassword(String password, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, storedPassword);
    }
}
