package com.user.user.adapters.driven.jpa.mysql.exception;

public class UserAlreadyExists extends RuntimeException{


    public UserAlreadyExists(String message) {
        super(message);
    }
}


