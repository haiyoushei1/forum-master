package com.studycloud1.forummaster.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(CustomizeErrorCode customErrorCode){
        this.message = customErrorCode.getMessage();
        this.code = customErrorCode.getCode();
    }
}
