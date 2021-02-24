package com.studycloud1.forummaster.controller;

import com.studycloud1.forummaster.mapper.UserMapper;
import com.studycloud1.forummaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest){
        Cookie cookies[] = httpServletRequest.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.selectUser(token);
                if(user != null){
                    httpServletRequest.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        return "index";
    }
}
