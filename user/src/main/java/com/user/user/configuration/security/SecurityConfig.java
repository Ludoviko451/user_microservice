package com.user.user.configuration.security;


import com.user.user.adapters.driven.jpa.mysql.adapter.UserDetailsServiceImpl;
import com.user.user.configuration.security.jwt.JwtAuthenticationEntryPoint;
import com.user.user.configuration.security.jwt.JwtAuthentitacionFilter;
import com.user.user.configuration.security.jwt.JwtTokenProvider;
import com.user.user.configuration.security.jwt.SecurityContants;
import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration // Le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la app
@EnableWebSecurity // Indicamos que se activa la seguridad web en nuestra aplicacion y ademas esta sera una clase la cual contendra toda la configuracion referente a la seguridad
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

    //Este bean se va a encargar de verificar la informacion de los usuarios que se logean
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    //Con este bean nos encargaremos de encriptar todas nuestras claves

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    //Con este bean incorporara el filtro de seguridad de jwt

    @Bean
    public JwtAuthentitacionFilter jwtAuthentitacionFilter() {
        return new JwtAuthentitacionFilter(userDetailsService, tokenProvider);

    }

    //Bean que se encarga de establecer una cadena de filtros de seguridad en nuestra app,
    //Aqui se determina los permisos sobre los roles en la app

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling .authenticationEntryPoint(jwtAuthenticationEntryPoint))      //Nos establece un punto de entrada personalizado de autenticacion para  manejo de autentitaciones no autorizadas


                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Nos permite crear una sesion

                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("auth/login").permitAll()
                        .requestMatchers("auth/registerAdmin").hasAuthority(SecurityContants.ADMIN)
                        .requestMatchers("auth/registerTeacher").hasAuthority(SecurityContants.ADMIN)
                        .requestMatchers("auth/registerStudent").hasAnyAuthority(SecurityContants.ADMIN, SecurityContants.TEACHER)
                        .requestMatchers("user/userByEmail").hasAuthority(SecurityContants.ADMIN)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated() // Requiere autenticación para todas las demás solicitudes
                )

                .addFilterBefore(jwtAuthentitacionFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
