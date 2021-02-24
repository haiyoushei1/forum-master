package com.studycloud1.forummaster.mapper;

import com.studycloud1.forummaster.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (id, account_id, name, token, gmt_create, gmt_modified) values (#{id}, #{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insertUser(User user);

//    @Select("select ")
//    User selectUser(String token);

    @Select("select * from user where token = #{token}")
    User selectUser(@Param("token") String token);

    @Update("update user set name = #{token}")
    void updateUser(@Param("token") String token);

}
