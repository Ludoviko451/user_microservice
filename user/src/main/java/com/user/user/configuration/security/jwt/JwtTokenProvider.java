package com.user.user.configuration.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // Método para generar un token JWT
    public String generateToken(Authentication authentication) {
        // Obtener el nombre de usuario desde la autenticación
        String username = authentication.getName();

        // Obtener la fecha y hora actual
        Date timeNow = new Date();

        // Calcular la fecha de expiración del token (5 minutos desde ahora)
        Date expiration = new Date(timeNow.getTime() + SecurityContants.JWT_EXPIRATION_TIME);

        // Generar el token utilizando la librería Jwts
        return Jwts.builder()
                .setSubject(username) // Establecer el nombre de usuario como sujeto del token
                .setIssuedAt(timeNow) // Establecer la fecha de emisión del token
                .setExpiration(expiration) // Establecer la fecha de expiración del token
                .signWith(SignatureAlgorithm.HS384, SecurityContants.JWT_SECRET) // Firmar el token con el algoritmo HS384 y la clave secreta
                .compact(); // Compactar el token en una cadena
    }

    // Método para extraer el nombre de usuario a partir del token JWT
    public String findUsername(String token) {
        // Parsear el token y extraer el nombre de usuario
        return Jwts.parser()
                .setSigningKey(SecurityContants.JWT_SECRET) // Establecer la clave secreta para verificar la firma del token
                .parseClaimsJws(token) // Parsear el token
                .getBody() // Obtener el cuerpo del token
                .getSubject(); // Obtener el nombre de usuario del cuerpo del token
    }

    // Método para validar la autenticidad y la vigencia del token JWT
    public Boolean validateToken(String token) {
        try {
            // Parsear y verificar la firma del token
            Jwts.parser().setSigningKey(SecurityContants.JWT_SECRET).parseClaimsJws(token);
            // Si no hay excepciones, el token es válido
            return true;
        } catch (Exception e) {
            // Si se produce una excepción, el token es inválido
            throw new AuthenticationCredentialsNotFoundException("Jwt ha expirado o es inválido");
        }
    }
}
