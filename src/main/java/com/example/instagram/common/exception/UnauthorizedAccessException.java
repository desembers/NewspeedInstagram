package com.example.instagram.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends NesFeedException {
    public UnauthorizedAccessException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
