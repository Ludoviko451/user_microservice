package com.user.user.configuration.security;


import com.user.user.adapters.driven.jpa.mysql.adapter.UserDetailsServiceImpl;
import com.user.user.configuration.security.jwt.JwtAuthenticationEntryPoint;
import com.user.user.configuration.security.jwt.JwtAuthentitacionFilter;
import com.user.user.configuration.security.jwt.JwtTokenProvider;
import com.user.user.configuration.security.jwt.SecurityContants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    private final UserDetailsServiceImpl userDetailsService;


    private final JwtTokenProvider tokenProvider;




    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsServiceImpl userDetailsService, JwtTokenProvider tokenProvider) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }



    @Bean
    public JwtAuthentitacionFilter jwtAuthentitacionFilter() {
        return new JwtAuthentitacionFilter(userDetailsService, tokenProvider);

    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling .authenticationEntryPoint(jwtAuthenticationEntryPoint))

                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("auth/login").permitAll()
                        .requestMatchers("auth/registerAdmin").hasAuthority(SecurityContants.ADMIN)
                        .requestMatchers("auth/registerTeacher").hasAuthority(SecurityContants.ADMIN)
                        .requestMatchers("auth/registerStudent").hasAuthority(SecurityContants.ADMIN)
                        .requestMatchers("user/userByEmail").hasAuthority(SecurityContants.ADMIN)
                        .requestMatchers("user/roleByEmail").authenticated()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().permitAll()
                )

                .addFilterBefore(jwtAuthentitacionFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
