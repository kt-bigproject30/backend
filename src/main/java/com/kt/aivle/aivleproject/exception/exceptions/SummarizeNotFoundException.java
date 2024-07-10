package com.kt.aivle.aivleproject.exception.exceptions;

public class SummarizeNotFoundException extends RuntimeException{
    public SummarizeNotFoundException() {
        super("해당 글을 찾을 수 없습니다.");
    }
}
