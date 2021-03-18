package com.studycloud1.forummaster.controller;

import com.studycloud1.forummaster.dto.NotificationDTO;
import com.studycloud1.forummaster.dto.ResultDTO;
import com.studycloud1.forummaster.enums.NotificationTypeEnum;
import com.studycloud1.forummaster.exception.CustomizeErrorCode;
import com.studycloud1.forummaster.exception.CustomizeException;
import com.studycloud1.forummaster.mapper.NotificationMapper;
import com.studycloud1.forummaster.model.User;
import com.studycloud1.forummaster.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Integer id, HttpServletRequest httpServletRequest){
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if(user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.RELAY_COMMENT.getCode() == notificationDTO.getType()
                || NotificationTypeEnum.RELAY_QUESTION.getCode() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
