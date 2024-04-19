package com.user.user.adapters.driven.jpa.mysql.exception;

public class UserNotExistException extends RuntimeException{

    public UserNotExistException(String email) {
        super(email);
    }
}
