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
    public List<Notification> findById(Integer id){
        List<Notification> notifs = notificationRepository.findAll();
        List<Notification> userNotifs = new ArrayList<>();
        for(Notification notif : notifs){
            if(notif.getUser_id().equals(id)){
                userNotifs.add(notif);
            }
        }

        if(userNotifs.size() > 0){
            return userNotifs;
        }else{
            return null;
        }

    }
    public void addNotification(Notification notif){
        LocalDateTime createdAt = LocalDateTime.now();

        Optional<NotificationType> temp = notificationTypeRepository.findById(notif.getNotificationType().getId());
        notif.setNotificationType(temp.get());
        notif.setCreatedAt(createdAt);
        System.out.println(notif.getNotificationType().getType().toString());

        if(notif.getNotificationType().getType().toString().toLowerCase().equals("LoggedIn")){
            notif.setMessage("You accessed your account at: "+ createdAt.getHour()+":"+createdAt.getMinute()+" on: "
            + createdAt.getMonth()+"/"+createdAt.getDayOfMonth()+"/"+createdAt.getYear());
        }else
        if(notif.getNotificationType().getType().toString().toLowerCase().equals("deposit") && notif.getAmount() > 0){
            notif.setMessage("You deposited "+notif.getAmount() + " soles into your account at: "+ createdAt.getHour()+":"+createdAt.getMinute()+" on: "
                    + createdAt.getMonth()+"/"+createdAt.getDayOfMonth()+"/"+createdAt.getYear());
        }else
        if(notif.getNotificationType().getType().toString().toLowerCase().equals("Withdrawal") && notif.getAmount() > 0){
            notif.setMessage("You withdrew "+notif.getAmount() + " soles into your account at: "+ createdAt.getHour()+":"+createdAt.getMinute()+" on: "
                    + createdAt.getMonth()+"/"+createdAt.getDayOfMonth()+"/"+createdAt.getYear());
        } else notif.setAmount(-1.0f);

        notificationRepository.save(notif);
    }

    public Notification getLastNotification(){

        Notification notif = getAllNotifications().get(getAllNotifications().size()-1);
        return notif;
    }
}
