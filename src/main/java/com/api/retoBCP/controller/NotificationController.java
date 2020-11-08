package com.api.retoBCP.controller;

import com.api.retoBCP.model.Notification;
import com.api.retoBCP.model.UserNotificationSubscription;
import com.api.retoBCP.service.NotificationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    RestTemplate restTemplate = new RestTemplate();
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
        List<Notification> notifs = notificationService.findByUserId(id);
        if( notifs != null){
            return new ResponseEntity<>(notifs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("read/{id}")
    public  ResponseEntity<List<Notification>> getReadNotifications(@PathVariable Integer id){
        List<Notification> notifs = notificationService.findReadByUserId(id);
        if( notifs != null){
            return new ResponseEntity<>(notifs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public void deleteAll(){
        notificationService.deleteAll();
    }
    @GetMapping("unread/{id}")
    public  ResponseEntity<List<Notification>> getUnReadNotifications(@PathVariable Integer id){
        List<Notification> notifs = notificationService.findUnreadByUserId(id);
        if( notifs != null){
            return new ResponseEntity<>(notifs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?>deleteAllByUser(@PathVariable Integer id){
        notificationService.deleteAllByUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateEmployee(
                                   @Validated @RequestBody Notification notif)  {

        
        Notification temp = notificationService.save(notif);

        return  new ResponseEntity<>(HttpStatus.OK);
    }


}
