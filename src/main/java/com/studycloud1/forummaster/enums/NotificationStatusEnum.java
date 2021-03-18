package com.studycloud1.forummaster.enums;

public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private Integer status;

    NotificationStatusEnum(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
