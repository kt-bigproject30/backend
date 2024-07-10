package com.kt.aivle.aivleproject.exception;

import com.kt.aivle.aivleproject.exception.exceptions.SummarizeNotFoundException;
import com.kt.aivle.aivleproject.global.exception.AbstractExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class SummarizeExceptionHandler extends AbstractExceptionHandler {
    @ExceptionHandler(SummarizeNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(SummarizeNotFoundException e) {
        return handleNotFound(e);
    }
}
