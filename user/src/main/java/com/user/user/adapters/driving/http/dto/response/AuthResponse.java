package com.user.user.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;


public class AuthResponse {

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;

    private String tokenType = "Bearer";

}
