package com.studycloud1.forummaster.controller;


import com.studycloud1.forummaster.dto.QuestionDTO;
import com.studycloud1.forummaster.mapper.QuestionMapper;
import com.studycloud1.forummaster.model.Question;
import com.studycloud1.forummaster.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String checkQuestion(@PathVariable(name = "id") Integer Id,
                                Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(Id);

        model.addAttribute("question", questionDTO);

        return "question";
    }
}
