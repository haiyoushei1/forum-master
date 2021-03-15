package com.studycloud1.forummaster.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String content;
    private Integer parentId;
    private Integer type;
}
