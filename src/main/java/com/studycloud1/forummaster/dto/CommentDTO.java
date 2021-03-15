package com.studycloud1.forummaster.dto;

import com.studycloud1.forummaster.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;

    private Integer parentId;

    private Integer type;

    private Integer commentor;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer likeCount;

    private String content;

    private User user;

}
