package com.taskmanagement.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
