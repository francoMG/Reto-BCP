package com.api.retoBCP.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    private Integer id;
    private Integer user_id;
    private String title;
    private String message;

    @ManyToOne
    private NotificationType notificationType;
    private boolean readNotif;

    private boolean deleted;

    public Notification(Integer id, Integer user_id, String title, String message, boolean readNotif,
                        boolean deleted) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.message = message;
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


    public boolean getReadNotif() {
        return readNotif;
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
