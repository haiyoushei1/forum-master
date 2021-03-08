package com.studycloud1.forummaster.controller;

import com.studycloud1.forummaster.dto.QuestionDTO;
import com.studycloud1.forummaster.model.Question;
import com.studycloud1.forummaster.model.User;
import com.studycloud1.forummaster.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title" ,required = false) String title,
                            @RequestParam(value = "description" ,required = false) String description,
                            @RequestParam(value = "tag" ,required = false) String tag,
                            @RequestParam(value = "id" ,required = false)  Integer id,
                            Model model,
                            HttpServletRequest httpServletRequest){

        if(StringUtils.isBlank(title)){
            model.addAttribute("error", "title can`t be null");
            return "publish";
        }

        if(StringUtils.isBlank(description)){
            model.addAttribute("error", "description can`t be null");
            return "publish";
        }

        if(StringUtils.isBlank(tag)){
            model.addAttribute("error", "tag can`t be null");
            return "publish";
        }

        User user = (User) httpServletRequest.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error", "please log in");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.createOrUpdate(question);

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);

        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());

        return "publish";
    }
}
