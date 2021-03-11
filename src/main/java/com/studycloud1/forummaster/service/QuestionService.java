package com.studycloud1.forummaster.service;


import com.studycloud1.forummaster.dto.PaginationDTO;
import com.studycloud1.forummaster.dto.QuestionDTO;
import com.studycloud1.forummaster.mapper.QuestionMapper;
import com.studycloud1.forummaster.mapper.UserMapper;
import com.studycloud1.forummaster.model.Question;
import com.studycloud1.forummaster.model.QuestionExample;
import com.studycloud1.forummaster.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

        if(questionMapper.selectByPrimaryKey(question.getId()) == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else {
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());

            questionMapper.updateByExampleSelective(updateQuestion, questionExample);
        }
    }

    public PaginationDTO list(Integer page, Integer size){
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        Integer totalPage;

        if((totalCount % size) != 0){
            totalPage = totalCount / size + 1;
        }else{
            totalPage = totalCount / size;
        }
        if(page < 1)
            page = 1;
        if(page > totalPage)
            page = totalPage;

        Integer limitCount = size * (page - 1);

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(limitCount, size));

        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

        paginationDTO.setPaginationDTO(questionDTOS, page, totalPage);

        return paginationDTO;
    }

    public PaginationDTO list(Integer page, Integer size, User user){
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(user.getId());
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        Integer totalPage;

        if((totalCount % size) != 0){
            totalPage = totalCount / size + 1;
        }else{
            totalPage = totalCount / size;
        }
        if(page < 1)
            page = 1;
        if(page > totalPage)
            page = totalPage;

        Integer limitCount = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(limitCount, size));
        for(Question question : questions){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setPaginationDTO(questionDTOS, page, totalPage);
        return paginationDTO;
    }


    public QuestionDTO getQuestionById(Integer id) {

        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        User user = userMapper.selectByPrimaryKey(question.getCreator());

        if(user == null){
            user = new User();
            user.setName("用户已注销");
            user.setId(000);
        }

        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);

        return questionDTO;

    }
}
