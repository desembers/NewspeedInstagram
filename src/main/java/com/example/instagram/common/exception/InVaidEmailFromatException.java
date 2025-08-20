package com.example.instagram.common.exception;

import org.springframework.http.HttpStatus;

public class InVaidEmailFromatException extends NesFeedException {
    public InVaidEmailFromatException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
