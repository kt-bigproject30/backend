package com.kt.aivle.aivleproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class AbstractExceptionHandler {

    protected final ResponseEntity<String> handleNotFound(Exception e) {
        log.error("Not Found Exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    protected final ResponseEntity<String> handleBadRequest(Exception e) {
        log.error("Bad Request Exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    protected final ResponseEntity<String> handleForbidden(Exception e) {
        log.error("Forbidden Exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    protected final ResponseEntity<String> handleInternalServerError(Exception e) {
        log.error("Internal Server Error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
