package com.kt.aivle.aivleproject.exception.exceptions;

public class PostNotUploadException extends RuntimeException{
    public PostNotUploadException(){
        super("개시물 업로드가 불가능합니다.");
    }
}
