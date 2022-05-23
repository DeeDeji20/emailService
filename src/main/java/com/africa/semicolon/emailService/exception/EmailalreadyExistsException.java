package com.africa.semicolon.emailService.exception;

public class EmailalreadyExistsException extends RuntimeException {
    public EmailalreadyExistsException(String message) {
        super(message);
    }
}
