package com.studycloud1.forummaster.service;


import com.studycloud1.forummaster.dto.QuestionDTO;
import com.studycloud1.forummaster.mapper.QuestionMapper;
import com.studycloud1.forummaster.mapper.UserMapper;
import com.studycloud1.forummaster.model.Question;
import com.studycloud1.forummaster.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(Question question) {

        if(questionMapper.selectQuestionById(question) == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insertQuestion(question);
        }

    }

    public List<QuestionDTO> list(Integer page, Integer size){
        page = size * (page - 1);
        List<Question> questions = questionMapper.selectQuestion(page, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for(Question question : questions){
            User user = userMapper.selectUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            if(user == null){
                user = new User();
                user.setName("用户已注销");
                user.setId(000);
            }

            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
