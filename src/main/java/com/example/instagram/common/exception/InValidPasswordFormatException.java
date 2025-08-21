package com.example.instagram.common.exception;

import org.springframework.http.HttpStatus;

public class InValidPasswordFormatException extends NesFeedException {
    public InValidPasswordFormatException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
