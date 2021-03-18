package com.studycloud1.forummaster.service;

import com.studycloud1.forummaster.dto.CommentDTO;
import com.studycloud1.forummaster.enums.CommentTypeEnum;
import com.studycloud1.forummaster.enums.NotificationStatusEnum;
import com.studycloud1.forummaster.enums.NotificationTypeEnum;
import com.studycloud1.forummaster.exception.CustomizeErrorCode;
import com.studycloud1.forummaster.exception.CustomizeException;
import com.studycloud1.forummaster.mapper.*;
import com.studycloud1.forummaster.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void insertComment(Comment comment, User commentor) {

        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_EMPTY);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
        }
        //回复评论
        if(CommentTypeEnum.COMMENT.getType() == comment.getType()){
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_EMPTY);
            }

            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());

            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_EMPTY);
            }

            commentMapper.insert(comment);
            createNotify(comment, dbComment.getCommentor(), NotificationTypeEnum.RELAY_COMMENT, commentor.getName(), question);
        //回复问题
        }else{
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_EMPTY);
            }
            commentMapper.insert(comment);
            createNotify(comment, question.getCreator(), NotificationTypeEnum.RELAY_QUESTION, commentor.getName(), question);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
        }

    }

    public List<CommentDTO> selectCommentById(Integer id, CommentTypeEnum commentTypeEnum){
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if(comments.size() == 0)
            return new ArrayList<>();

        Set<Integer> commentors = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        UserExample userExample = new UserExample();
        List<Integer> userId = new ArrayList<>();
        userId.addAll(commentors);
        userExample.createCriteria().andIdIn(userId);
        List<User> users = userMapper.selectByExample(userExample);

        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    private void createNotify(Comment comment, Integer receiverId, NotificationTypeEnum notificationTypeEnum, String name, Question question){
        if(comment.getCommentor().equals(receiverId)){
            return ;
        }
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentor());
        notification.setReceiver(receiverId);
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setOuterid(question.getId());
        notification.setType(notificationTypeEnum.getCode());
        notification.setNotifierName(name);
        notification.setNotifierTitle(question.getTitle());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());

        notificationMapper.insert(notification);
    }
}
