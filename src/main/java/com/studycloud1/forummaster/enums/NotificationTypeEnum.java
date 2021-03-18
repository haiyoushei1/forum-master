package com.studycloud1.forummaster.enums;

public enum NotificationTypeEnum {
    RELAY_QUESTION(1, "回复了问题"),
    RELAY_COMMENT(2, "回复了评论");

    private Integer code;
    private String name;

    NotificationTypeEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String nameOfType(int code) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getCode() == code) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
