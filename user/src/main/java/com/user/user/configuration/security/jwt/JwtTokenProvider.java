package com.user.user.configuration.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    public String generateToken(UserDetails authentication) {

        String username = authentication.getUsername();



        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        Date timeNow = new Date();


        Date expiration = new Date(timeNow.getTime() + SecurityContants.JWT_EXPIRATION_TIME);


        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setIssuedAt(timeNow)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS384, SecurityContants.JWT_SECRET)
                .compact();
    }



    public String findUsername(String token) {

        return Jwts.parser()
                .setSigningKey(SecurityContants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityContants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {

            throw new AuthenticationCredentialsNotFoundException("Jwt ha expirado o es inválido");
        }
    }
}
