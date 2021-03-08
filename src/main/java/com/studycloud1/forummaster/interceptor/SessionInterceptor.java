package com.studycloud1.forummaster.interceptor;

import com.studycloud1.forummaster.mapper.UserMapper;
import com.studycloud1.forummaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {


    @Autowired
    UserMapper userMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie cookies[] = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.selectUserBytoken(token);
                if(user != null){
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
