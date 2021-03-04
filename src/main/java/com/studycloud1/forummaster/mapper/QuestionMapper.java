package com.studycloud1.forummaster.mapper;

import com.studycloud1.forummaster.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("select * from question where id = #{id}")
    Question selectQuestionById(Question question);

    @Insert("insert into question (id, title, description, gmt_create, gmt_modified, creator, view_count, comment_count, like_count, tag) " +
            "values (#{id}, #{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{viewCount}, #{commentCount}, #{likeCount}, #{tag})")
    void insertQuestion(Question question);


    @Select("select * from question limit #{page},#{size}")
    List<Question> selectQuestion(Integer page, Integer size);
}
