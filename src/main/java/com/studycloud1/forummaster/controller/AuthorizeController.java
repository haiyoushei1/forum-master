package com.studycloud1.forummaster.controller;

import com.studycloud1.forummaster.dto.AccessTokenDTO;
import com.studycloud1.forummaster.dto.GithubUser;
import com.studycloud1.forummaster.mapper.UserMapper;
import com.studycloud1.forummaster.model.User;
import com.studycloud1.forummaster.provider.GithubProvider;
import com.studycloud1.forummaster.service.UserService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirect_uri);

        String accesstoken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accesstoken);

        if(githubUser != null){

            User user = new User();
            String token = UUID.randomUUID().toString();

            user.setId(githubUser.getId());
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());

            userService.createOrUpdate(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60*5);
            httpServletResponse.addCookie(cookie);

            return "redirect:/";
        }
        return "index";
    }
}
