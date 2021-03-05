package com.studycloud1.forummaster.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOS;
    private boolean hasPrevious;
    private boolean hasFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPaginationDTO(List<QuestionDTO> questionDTOS, Integer page, Integer totalPage){
        this.questionDTOS = questionDTOS;
        this.page = page;
        this.totalPage = totalPage;
        pages.add(page);

        for(int i = 1; i <= 3; i++){
            if(page - i > 0){
                pages.add(0, page - i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }


        if(page == 1){
            hasPrevious = false;
        }else{
            hasPrevious = true;
        }

        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }

        if(pages.contains(1)){
            hasFirstPage = false;
        }else{
            hasFirstPage = true;
        }

        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }


    }
}
