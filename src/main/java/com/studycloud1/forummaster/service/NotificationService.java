package com.studycloud1.forummaster.service;

import com.studycloud1.forummaster.dto.NotificationDTO;
import com.studycloud1.forummaster.dto.PaginationDTO;
import com.studycloud1.forummaster.dto.QuestionDTO;
import com.studycloud1.forummaster.enums.NotificationStatusEnum;
import com.studycloud1.forummaster.enums.NotificationTypeEnum;
import com.studycloud1.forummaster.exception.CustomizeErrorCode;
import com.studycloud1.forummaster.exception.CustomizeException;
import com.studycloud1.forummaster.mapper.NotificationMapper;
import com.studycloud1.forummaster.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    public PaginationDTO<NotificationDTO> list(Integer page, Integer size, User user) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(user.getId());
        notificationExample.setOrderByClause("gmt_create desc");
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        Integer totalPage;

        if((totalCount % size) != 0){
            totalPage = totalCount / size + 1;
        }else{
            totalPage = totalCount / size;
        }
        if(page < 1)
            page = 1;
        if(page > totalPage)
            page = totalPage;

        Integer limitCount = size * (page - 1);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(limitCount, size));
        for(Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setId(notification.getId());
            notificationDTO.setGmtCreate(notification.getGmtCreate());
            notificationDTO.setNotifierName(notification.getNotifierName());
            notificationDTO.setOuterTitle(notification.getNotifierTitle());
            notificationDTO.setOuterId(notification.getOuterid());
            notificationDTO.setStatus(notification.getStatus());
            notificationDTO.setType(notification.getType());
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setPaginationDTO(notificationDTOS, page, totalPage);
        return paginationDTO;
    }

    public Long getUnReadCount(User user) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(user.getId()).
                                        andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if((int)notification.getReceiver() != (int)user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setOuterId(notification.getOuterid());
        notificationDTO.setType(notification.getType());
        return notificationDTO;
    }
}
