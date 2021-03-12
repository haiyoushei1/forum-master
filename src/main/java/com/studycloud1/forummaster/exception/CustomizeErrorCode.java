package com.studycloud1.forummaster.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(1001, "资源不见了");

    private String message;
    private Integer code;
    public String getMessage(){
        return message;
    }

    public Integer getCode(){
        return code;
    }

    CustomizeErrorCode(Integer code, String message){
        this.message = message;
        this.code = code;
    }
}
