package com.api.retoBCP.controller;




import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private final NotificationService notificationService;
    public GreetingController(NotificationService notificationService, SimpMessagingTemplate temp) {
        this.notificationService = notificationService;
        this.template = temp;
    }


    /*@MessageMapping("/hello")
    @SendToUser("/topic/greetings")
    public Notification greeting( Notification notif) throws Exception {
        Thread.sleep(1000); // simulated delay

        notificationService.addNotification(notif);
        Notification temp = notificationService.getLastNotification();

        return temp;
    }
    */

    @MessageMapping("/notification")
    public void testing(Notification notif) throws Exception{
        Thread.sleep(1000); // simulated delay

        notificationService.addNotification(notif);
        Notification temp = notificationService.getLastNotification();

        template.convertAndSendToUser(""+notif.getUser_id().toString(),"/topic/greetings",notif);

    }
}