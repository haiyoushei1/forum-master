package com.studycloud1.forummaster.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(1001, "资源不见了"),
    TARGET_PARAM_NOT_FOUND(1002, "未选中问题"),
    NO_LOGIN(1003, "用户未登录"),
    CONTENT_IS_EMPTY(1004, "回复内容不能为空"),
    SYS_ERROR(1005, "服务器正在维护"),
    TARGET_PARAM_EMPTY(1006, "评论或问题已被删除"),
    TYPE_PARAM_NOT_FOUND(1007, "回复类型错误，请重试"),
    READ_FAIL(1008, "读错了，请重试");;


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
