package com.example.instagram.common.exception;

import org.springframework.http.HttpStatus;

public class InValidException extends NesFeedException {

  //예외 방지 명시적인 코드
  public InValidException(String message) {

    super(message, HttpStatus.UNAUTHORIZED);
  }
}
