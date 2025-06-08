package com.example.demo.notification;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

      EXCEPTION_BOOK_NOT_FOUND("EXCEPTION_BOOK_NOT_FOUND");

      private final String exceptionMessage;

      ExceptionMessage(String message) {
          this.exceptionMessage = message;
      }
}
