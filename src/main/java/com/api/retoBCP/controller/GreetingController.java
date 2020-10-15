package com.api.retoBCP.controller;




import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @Autowired
    private final NotificationService notificationService;
    public GreetingController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Notification greeting( Notification notif) throws Exception {
        Thread.sleep(1000); // simulated delay


        //NotificationType notifTYPE = new NotificationType();
        //notifTYPE.setId(1);

        //notif.setNotificationType(notifTYPE);
        //notif.setUser_id(1);
        //notif.setAmount(Float.parseFloat(message));
        //notif.setTitle("title");
        //notif.setReadNotif(false);
        //notif.setDeleted(false);
        System.out.println(notif.getAmount());
        notificationService.addNotification(notif);
        Notification temp = notificationService.getLastNotification();

        return temp;
    }

}