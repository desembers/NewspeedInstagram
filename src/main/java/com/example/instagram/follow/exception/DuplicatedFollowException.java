package com.example.instagram.follow.exception;

import com.example.instagram.common.exception.NesFeedException;
import org.springframework.http.HttpStatus;

public class DuplicatedFollowException extends NesFeedException {
    public DuplicatedFollowException(String message, HttpStatus status) {
        super(message, status);
    }
}
