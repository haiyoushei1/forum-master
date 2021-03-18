package com.studycloud1.forummaster.controller;


import com.studycloud1.forummaster.dto.NotificationDTO;
import com.studycloud1.forummaster.dto.PaginationDTO;
import com.studycloud1.forummaster.dto.QuestionDTO;
import com.studycloud1.forummaster.model.Notification;
import com.studycloud1.forummaster.model.Question;
import com.studycloud1.forummaster.model.User;
import com.studycloud1.forummaster.service.NotificationService;
import com.studycloud1.forummaster.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest httpServletRequest,
                          @RequestParam(name = "size", defaultValue = "3") Integer size,
                          @RequestParam(name = "page", defaultValue = "1") Integer page){
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if("questions".equals(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO<QuestionDTO> paginationDTO= questionService.list(page, size, user);
            model.addAttribute("pagination", paginationDTO);
        }else if("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            PaginationDTO<NotificationDTO> paginationDTO= notificationService.list(page, size, user);
            model.addAttribute("pagination", paginationDTO);
        }
        return "profile";
    }
}
