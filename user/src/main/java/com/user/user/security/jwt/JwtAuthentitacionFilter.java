package com.user.user.security.jwt;

import com.user.user.adapters.driven.jpa.mysql.adapter.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Este filtro se encarga de validar la información del token JWT en cada solicitud entrante
public class JwtAuthentitacionFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Método para extraer el token del encabezado de autorización de la solicitud
    private String obtainToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Obtener el token del encabezado de la solicitud
        String token = obtainToken(request);

        // Validar el token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // Si el token es válido, extraer el nombre de usuario del token y cargar los detalles del usuario
            String username = tokenProvider.findUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Obtener los roles del usuario
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            // Verificar si el usuario tiene uno de los roles permitidos
            if (roles.contains("ADMIN") || roles.contains("TEACHER") || roles.contains("STUDENT")) {
                // Crear una instancia de UsernamePasswordAuthenticationToken y establecerla en el contexto de seguridad
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
