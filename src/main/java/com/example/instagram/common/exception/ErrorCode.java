package com.example.instagram.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_EMAIL(org.springframework.http.HttpStatus.CONFLICT, "이메일 주소가 유효")
    INVALID_PASSWORD(HttpStatus.BAD_REQ)

        pirvate HttpStatus
                private String message

                        ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
                        }
}
