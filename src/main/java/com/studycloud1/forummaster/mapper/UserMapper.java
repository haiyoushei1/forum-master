package com.studycloud1.forummaster.mapper;

import com.studycloud1.forummaster.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (id, account_id, name, token, gmt_create, gmt_modified) values (#{id}, #{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User selectUserBytoken(@Param("token") String token);

    @Update("update user set token = #{token}, name = #{name} where id = #{id}")
    void updateUser(User user);

    @Select("select * from user where id = #{id}")
    User selectUserById(Long id);
}
