package com.EventManagementSystem.EventManagementSystem.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}