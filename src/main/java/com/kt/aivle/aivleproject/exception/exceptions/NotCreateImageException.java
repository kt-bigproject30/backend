package com.kt.aivle.aivleproject.exception.exceptions;

public class NotCreateImageException extends RuntimeException {
    public NotCreateImageException(){
        super("해당 요약글을 찾을 수 없습니다.");
    }
}
