package com.api.retoBCP.service;

import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.repository.NotificationRepository;
import com.api.retoBCP.repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;
    public List<Notification> getAllNotifications(){
        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findAll().forEach( notifications::add);
        return notifications;
    }
    public Notification getNotificationById(Integer id){
        Notification notif =  notificationRepository.findById(id).get();
        return notif;
    }
    public void addNotification(Notification notif){
        LocalDateTime createdAt = LocalDateTime.now();

        Optional<NotificationType> temp = notificationTypeRepository.findById(notif.getNotificationType().getId());
        notif.setNotificationType(temp.get());
        notif.setCreatedAt(createdAt);
        notificationRepository.save(notif);
    }


}
