package com.api.retoBCP.service;

import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.repository.NotificationRepository;
import com.api.retoBCP.repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationTypeService {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    public List<NotificationType> getAllNotificationTypes(){
        List<NotificationType> notificationTypes = new ArrayList<>();
        notificationTypeRepository.findAll().forEach( notificationTypes::add);
        return notificationTypes;
    }
    public NotificationType getNotificationTypeById(Integer id){
        NotificationType notif =  notificationTypeRepository.findById(id).get();
        return notif;
    }
    public void addNotificationType(NotificationType notifType){
        notificationTypeRepository.save(notifType);
    }

}
