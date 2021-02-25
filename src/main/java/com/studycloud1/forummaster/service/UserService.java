package com.studycloud1.forummaster.service;

import com.studycloud1.forummaster.mapper.UserMapper;
import com.studycloud1.forummaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user){
        if(userMapper.selectUserById(user.getId()) != null){
            userMapper.updateUser(user);
        }else{
            userMapper.insertUser(user);
        }
    }
}
