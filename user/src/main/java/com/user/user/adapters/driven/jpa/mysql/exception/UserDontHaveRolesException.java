package com.user.user.adapters.driven.jpa.mysql.exception;

public class UserDontHaveRolesException extends RuntimeException {

    public UserDontHaveRolesException(String message) {
        super(message);
    }
}
