package com.api.retoBCP.controller;


import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.NotificationType;
import com.api.retoBCP.service.NotificationService;
import com.api.retoBCP.service.NotificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificationTypes")
public class NotificationTypeController {

    @Autowired
    private final NotificationTypeService notificationServiceType;
    public NotificationTypeController(NotificationTypeService notificationServiceType) {
        this.notificationServiceType = notificationServiceType;
    }

    @GetMapping
    public List<NotificationType> getAllNotificationTypes(){
        return notificationServiceType.getAllNotificationTypes();
    }
    @PostMapping
    public void addNotificationType(@RequestBody NotificationType notificationType){
        notificationServiceType.addNotificationType(notificationType);
    }
}
