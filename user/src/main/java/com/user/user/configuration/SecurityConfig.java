package com.user.user.configuration;


import com.user.user.security.jwt.JwtAuthenticationEntryPoint;
import com.user.user.security.jwt.JwtAuthentitacionFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la app
@EnableWebSecurity // Indicamos que se activa la seguridad web en nuestra aplicacion y ademas esta sera una clase la cual contendra toda la configuracion referente a la seguridad
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
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
    JwtAuthentitacionFilter jwtAuthentitacionFilter() {
        return new JwtAuthentitacionFilter();

    }

    //Bean que se encarga de establecer una cadena de filtros de seguridad en nuestra app,
    //Aqui se determina los permisos sobre los roles en la app

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf ->
                        csrf.disable()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling .authenticationEntryPoint(jwtAuthenticationEntryPoint))      //Nos establece un punto de entrada personalizado de autenticacion para  manejo de autentitaciones no autorizadas


                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Nos permite crear una sesion

                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("auth/login").permitAll()
                        .requestMatchers("auth/registerAdmin").hasAuthority("ADMIN")
                        .requestMatchers("auth/registerTeacher").hasAuthority("ADMIN")
                        .requestMatchers("auth/registerStudent").hasAnyAuthority("ADMIN", "TEACHER")
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
