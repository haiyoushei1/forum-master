package com.studycloud1.forummaster.controller;

import com.studycloud1.forummaster.dto.CommentCreateDTO;
import com.studycloud1.forummaster.dto.CommentDTO;
import com.studycloud1.forummaster.dto.ResultDTO;
import com.studycloud1.forummaster.enums.CommentTypeEnum;
import com.studycloud1.forummaster.exception.CustomizeErrorCode;
import com.studycloud1.forummaster.model.Comment;
import com.studycloud1.forummaster.model.User;
import com.studycloud1.forummaster.service.CommentService;
import com.studycloud1.forummaster.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object comment(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment Comment = new Comment();
        Comment.setCommentor(user.getId());
        Comment.setContent(commentCreateDTO.getContent());
        Comment.setGmtCreate(System.currentTimeMillis());
        Comment.setGmtModified(Comment.getGmtCreate());
        Comment.setType(commentCreateDTO.getType());
        Comment.setParentId(commentCreateDTO.getParentId());
        Comment.setLikeCount(0);

        commentService.insertComment(Comment, user);

        return ResultDTO.okOf();
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<List<CommentDTO>> comment(@PathVariable(name = "id") Integer id){

        List<CommentDTO> commentDTOS = commentService.selectCommentById(id, CommentTypeEnum.COMMENT);

        return ResultDTO.okOf(commentDTOS);
    }
}
