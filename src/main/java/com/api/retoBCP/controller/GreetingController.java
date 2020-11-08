package com.api.retoBCP.controller;




import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.model.UserNotificationSubscription;
import com.api.retoBCP.service.NotificationService;
import com.api.retoBCP.service.NotificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;

@EnableScheduling
@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private final NotificationService notificationService;
    @Autowired
    private final NotificationTypeService notificationTypeService;

    public GreetingController(NotificationService notificationService, SimpMessagingTemplate temp, NotificationTypeService notificationTypeService) {
        this.notificationService = notificationService;
        this.notificationTypeService = notificationTypeService;
        this.template = temp;
    }
    RestTemplate restTemplate = new RestTemplate();


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

        UserNotificationSubscription temp1 = new UserNotificationSubscription();
        temp1.setNotificationType_id(notif.getNotificationType().getId());
        temp1.setUser_id(notif.getUser_id());

        ResponseEntity<UserNotificationSubscription> responseEntity =
                restTemplate.postForEntity(
                        "https://user-subscriptions.herokuapp.com/user-subscriptions/exists/",
                        temp1, UserNotificationSubscription.class);


        if(responseEntity.getStatusCode() == HttpStatus.OK){
            notificationService.addNotification(notif);
            Notification temp = notificationService.getLastNotification();

            template.convertAndSendToUser(""+notif.getUser_id().toString(),"/topic/greetings",temp);

        }

    }

     @Scheduled(fixedDelay = 6 * 10000)
    public void publishUpdates(){

        ResponseEntity<UserNotificationSubscription[]> responseEntity =
                restTemplate.getForEntity(
                        "https://user-subscriptions.herokuapp.com/user-subscriptions/specific/5",
                        UserNotificationSubscription[].class);


        for (UserNotificationSubscription sub:
             responseEntity.getBody()) {
            Notification notif = new Notification();
            notif.setUser_id(sub.getUser_id());
            notif.setDeleted(false);
            notif.setReadNotif(false);
            notif.setAmount(-1.0f);
            notif.setTitle("Promotion!");

            notif.setNotificationType(notificationTypeService.getNotificationTypeById(sub.getNotificationType_id()));

            notif.setMessage("Sign up for a new credit card!");
            notificationService.addNotification(notif);

            template.convertAndSendToUser(""+notif.getUser_id().toString(),"/topic/greetings",notif);

        }

    }

}