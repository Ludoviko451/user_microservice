package com.user.user.configuration;

import com.user.user.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.user.user.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.user.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.user.user.domain.api.IAuthServicePort;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.api.usecases.AuthUseCase;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AuthBeanConfiguration {

    private final IUserServicePort userServicePort;
    private final SecurityConfig securityConfig;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public IAuthServicePort authServicePort(){

        return new AuthUseCase(userServicePort, securityConfig.passwordEncoder(), jwtTokenProvider);
    }
}
