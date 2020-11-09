package com.api.retoBCP.service;

import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.model.UserNotificationSubscription;
import com.api.retoBCP.repository.NotificationRepository;
import com.api.retoBCP.repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    public List<Notification> findByUserId(Integer id){
        List<Notification> notifs = notificationRepository.getByUserId(id);


        if(notifs.size() >= 0){
            return notifs;
        }else{
            return null;
        }

    }
    public void deleteAll(){
        this.notificationRepository.deleteAll();
    }

    public List<Notification> findReadByUserId(Integer id){
        List<Notification> notifs = notificationRepository.findReadByUser(id);


        if(notifs.size() >= 0){
            return notifs;
        }else{
            return null;
        }

    }
    public Notification findById(Integer id){
        Notification notif = notificationRepository.findById(id).get();
        return notif;
    }
    public Notification save(Notification notif){
        return notificationRepository.save(notif);
    }

    public List<Notification> findUnreadByUserId(Integer id){
        List<Notification> notifs = notificationRepository.findAll();
        List<Notification> userNotifs = new ArrayList<>();
        for(Notification notif : notifs){
            if(notif.getUser_id().equals(id) && notif.isReadNotif()==false){
                userNotifs.add(notif);
            }
        }

        if(userNotifs.size() >= 0){
            return userNotifs;
        }else{
            return null;
        }

    }

    public Notification addNotification(Notification notif){



        OffsetDateTime createdAt = OffsetDateTime.now(ZoneId.of("America/New_York"));


        Optional<NotificationType> temp = notificationTypeRepository.findById(notif.getNotificationType().getId());
        notif.setNotificationType(temp.get());
        notif.setCreatedAt(createdAt);


        if(notif.getNotificationType().getType().toString().toLowerCase().equals("loggedin")){
            notif.setMessage("You accessed your account at: "+ createdAt.getHour()+":"+createdAt.getMinute()+" on: "
            + createdAt.getMonth()+"/"+createdAt.getDayOfMonth()+"/"+createdAt.getYear());
        }else
        if(notif.getNotificationType().getType().toString().toLowerCase().equals("deposit") && notif.getAmount() > 0){
            notif.setMessage("You deposited "+notif.getAmount() + " soles into your account at: "+ createdAt.getHour()+":"+createdAt.getMinute()+" on: "
                    + createdAt.getMonth()+"/"+createdAt.getDayOfMonth()+"/"+createdAt.getYear());
        }else
        if(notif.getNotificationType().getType().toString().toLowerCase().equals("withdrawal") && notif.getAmount() > 0){
            notif.setMessage("You withdrew "+notif.getAmount() + " soles into your account at: "+ createdAt.getHour()+":"+createdAt.getMinute()+" on: "
                    + createdAt.getMonth()+"/"+createdAt.getDayOfMonth()+"/"+createdAt.getYear());
        } else if(notif.getNotificationType().getType().toString().toLowerCase().equals("deposit3rd") && notif.getAmount() > 0){
            String tempString = notif.getMessage();
            notif.setMessage("User "+tempString+ " deposited " +notif.getAmount() + " soles into your account at: "+ createdAt.getHour()+":"+createdAt.getMinute()+" on: "
                    + createdAt.getMonth()+"/"+createdAt.getDayOfMonth()+"/"+createdAt.getYear());
        }else notif.setAmount(-1.0f);

        notificationRepository.save(notif);
        return notif;
    }


    public void deleteAllByUser(Integer id){
        List<Notification> notifs = notificationRepository.findAll();
        List<Notification> userNotifs = new ArrayList<>();
        for(Notification notif : notifs){
            if(notif.getUser_id().equals(id) ){
                userNotifs.add(notif);
            }
        }

        userNotifs.forEach(not -> notificationRepository.delete(not));

    }
}
