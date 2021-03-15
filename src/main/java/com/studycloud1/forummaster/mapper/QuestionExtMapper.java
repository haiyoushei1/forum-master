package com.studycloud1.forummaster.mapper;

import com.studycloud1.forummaster.model.Question;
import com.studycloud1.forummaster.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    void incView(Question question);
    void incComment(Question question);
}