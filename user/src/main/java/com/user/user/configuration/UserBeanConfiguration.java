package com.user.user.configuration;

import com.user.user.adapters.driven.jpa.mysql.adapter.UserAdapter;
import com.user.user.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.user.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.user.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.user.user.domain.api.IUserServicePort;
import com.user.user.domain.api.usecases.UserUseCase;
import com.user.user.domain.spi.IUserPersistencePort;
import com.user.user.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class UserBeanConfiguration {

    private final IUserEntityMapper userEntityMapper;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserAdapter(userRepository, userEntityMapper, roleRepository, jwtTokenProvider);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }

}
