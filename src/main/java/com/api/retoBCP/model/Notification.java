package com.api.retoBCP.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer user_id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    @ManyToOne
    private NotificationType notificationType;
    private boolean readNotif;
    private Float amount;
    private boolean deleted;

    public Notification(Integer id, Integer user_id, String title, boolean readNotif,
                        boolean deleted) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.readNotif = readNotif;
        this.deleted = deleted;



    }

    public Notification() {
    super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isReadNotif() {
        return readNotif;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }


    public void setReadNotif(boolean readNotif) {
        this.readNotif = readNotif;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public NotificationType getNotificationType() {
        return notificationType;
   }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }


}
