package com.user.user.configuration.security.jwt;

public class SecurityContants {
    private SecurityContants() {
        throw new IllegalStateException("Utility class");
    }
    public static final long JWT_EXPIRATION_TIME = 300000;
    public static final String JWT_SECRET = "secret";


    public static final String ADMIN = "ADMIN";
    public static final String TEACHER = "TEACHER";

    public static final String STUDENT = "STUDENT";
}
