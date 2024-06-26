package com.user.user.configuration.exceptionhandler;

import com.user.user.adapters.driven.jpa.mysql.exception.*;
import com.user.user.configuration.Constants;
import com.user.user.configuration.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@ControllerAdvice
public class UserControllerAdvisor {

    @ExceptionHandler(PhoneNumberNotValidException.class)
    public ResponseEntity<ExceptionResponse> handlePhoneNumberNotValidException(PhoneNumberNotValidException e) {
        ExceptionResponse response = new ExceptionResponse(String.format(Constants.PHONE_NUMBER_NOT_VALID_MESSAGE, e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ExceptionResponse> handlePasswordMismatchException(PasswordMismatchException e) {
        ExceptionResponse response = new ExceptionResponse(String.format(Constants.PASSWORD_MISMATCH_EXCEPTION, e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotExistException(UserNotExistException e) {
        ExceptionResponse response = new ExceptionResponse(String.format(Constants.USER_NOT_EXIST_MESSAGE, e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExists(UserAlreadyExists e) {
        ExceptionResponse response = new ExceptionResponse(String.format(Constants.USER_ALREADY_EXISTS, e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }


}
