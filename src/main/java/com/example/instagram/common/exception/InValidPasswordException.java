package com.example.instagram.common.exception;

import org.springframework.http.HttpStatus;

public class InValidPasswordException extends NesFeedException {
    public InValidPasswordException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
