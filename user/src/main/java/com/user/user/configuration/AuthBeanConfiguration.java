package com.user.user.configuration;

import com.user.user.configuration.security.SecurityConfig;
import com.user.user.domain.api.IAuthServicePort;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.api.usecases.AuthUseCase;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.configuration.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AuthBeanConfiguration {

    private final IUserServicePort userServicePort;
    private final IUserPersistencePort userPersistencePort;
    private final SecurityConfig securityConfig;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public IAuthServicePort authServicePort(){

        return new AuthUseCase(userServicePort, jwtTokenProvider, userPersistencePort);
    }
}
