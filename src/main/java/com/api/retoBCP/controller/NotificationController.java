package com.api.retoBCP.controller;

import com.api.retoBCP.model.Notification;
import com.api.retoBCP.service.NotificationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications(){
        return notificationService.getAllNotifications();
    }

    @PostMapping
    public void addNotification(@RequestBody Notification notification){
        notificationService.addNotification(notification);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Notification>> getNotification(@PathVariable Integer id){
        List<Notification> notifs = notificationService.findById(id);
        if( notifs != null){
            return new ResponseEntity<>(notifs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }


}
