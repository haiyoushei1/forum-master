package com.studycloud1.forummaster.controller;

import com.studycloud1.forummaster.dto.PaginationDTO;
import com.studycloud1.forummaster.dto.QuestionDTO;
import com.studycloud1.forummaster.mapper.UserMapper;
import com.studycloud1.forummaster.model.User;
import com.studycloud1.forummaster.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest, Model model,
                        @RequestParam(name = "size", defaultValue = "3") Integer size,
                        @RequestParam(name = "page", defaultValue = "1") Integer page){
        Cookie cookies[] = httpServletRequest.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.selectUserBytoken(token);
                if(user != null){
                    httpServletRequest.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        PaginationDTO paginationDTO = questionService.list(page, size);
        model.addAttribute("pagination", paginationDTO);

        return "index";
    }

}
