package com.studycloud1.forummaster.mapper;

import com.studycloud1.forummaster.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("select * from question where id = #{id}")
    Question selectQuestionById(Integer id);

    @Insert("insert into question (id, title, description, gmt_create, gmt_modified, creator, view_count, comment_count, like_count, tag) " +
            "values (#{id}, #{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{viewCount}, #{commentCount}, #{likeCount}, #{tag})")
    void insertQuestion(Question question);


    @Select("select * from question limit #{limitCount},#{size}")
    List<Question> selectQuestion(Integer limitCount, Integer size);

    @Select("select count(1) from question")
    Integer selectAllQuestionCount();

    @Select("select count(1) from question where creator = #{userId}")
    Integer selectUserQuestionCount(Integer userId);

    @Select("select * from question where creator = #{id} limit #{limitCount},#{size}")
    List<Question> selectUserQuestion(Integer limitCount, Integer size, Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void updateQuestion(Question question);
}
