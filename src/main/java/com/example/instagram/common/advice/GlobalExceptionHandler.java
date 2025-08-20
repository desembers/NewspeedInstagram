package com.example.instagram.common.advice;

import com.example.instagram.common.exception.InVaidEmailFromatException;
import com.example.instagram.common.exception.InValidPasswordException;
import com.example.instagram.common.exception.InValidPasswordFormatException;
import com.example.instagram.common.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(InVaidEmailFromatException.class)
    public ResponseEntity<String> handleInValidEmailException(InVaidEmailFromatException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InValidPasswordException.class)
    public ResponseEntity<String> handleInValidPasswordException(InValidPasswordException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InValidPasswordFormatException.class)
    public ResponseEntity<String> handleInValidPasswordFormatException(InValidPasswordFormatException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}
