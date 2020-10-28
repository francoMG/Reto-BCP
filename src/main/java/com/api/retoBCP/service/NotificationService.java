package com.api.retoBCP.service;

import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.model.UserNotificationSubscription;
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
    public List<Notification> findByUserId(Integer id){
        List<Notification> notifs = notificationRepository.findAll();
        List<Notification> userNotifs = new ArrayList<>();
        for(Notification notif : notifs){
            if(notif.getUser_id().equals(id)){
                userNotifs.add(notif);
            }
        }

        if(userNotifs.size() >= 0){
            return userNotifs;
        }else{
            return null;
        }

    }

    public List<Notification> findReadByUserId(Integer id){
        List<Notification> notifs = notificationRepository.findAll();
        List<Notification> userNotifs = new ArrayList<>();
        for(Notification notif : notifs){
            if(notif.getUser_id().equals(id) && notif.isReadNotif()==true){
                userNotifs.add(notif);
            }
        }

        if(userNotifs.size() >= 0){
            return userNotifs;
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

    public boolean addNotification(Notification notif, UserNotificationSubscription[] subs){
        Boolean add = false;
        for (UserNotificationSubscription sub:
        subs) {
            if(notif.getNotificationType().getId().equals(sub.getNotificationType_id())){
                add = true;
                break;
            }
        }
        if(add == false) return false;

        LocalDateTime createdAt = LocalDateTime.now();

        Optional<NotificationType> temp = notificationTypeRepository.findById(notif.getNotificationType().getId());
        notif.setNotificationType(temp.get());
        notif.setCreatedAt(createdAt);
        System.out.println(notif.getNotificationType().getType().toString());

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
        return true;
    }

    public Notification getLastNotification(){

        Notification notif = getAllNotifications().get(getAllNotifications().size()-1);
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
