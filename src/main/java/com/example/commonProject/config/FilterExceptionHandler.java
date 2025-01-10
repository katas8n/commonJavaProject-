package com.example.commonProject.config;

import com.example.commonProject.exception.CartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FilterExceptionHandler {
    class Message {
        private String message;

        public Message(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<Message> handleCartException(CartException e) {
        return ResponseEntity.badRequest().body(new Message(e.getMessage()));
    }
}
