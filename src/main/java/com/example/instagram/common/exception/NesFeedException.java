package com.example.instagram.common.exception;

import org.springframework.http.HttpStatus;

public class NesFeedException extends RuntimeException {

    //서비스 로직 예외 처리 코드
    HttpStatus status;

    //상태 코드 전달하기
    public NesFeedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
