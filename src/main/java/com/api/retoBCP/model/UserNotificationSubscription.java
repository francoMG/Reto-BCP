package com.api.retoBCP.model;

import javax.persistence.*;


public class UserNotificationSubscription {


    private Integer id;
    private Integer user_id;
    private Integer notificationType_id;


    public UserNotificationSubscription(){}

    public UserNotificationSubscription(Integer user_id, Integer notificationType_id) {

        this.user_id = user_id;
        this.notificationType_id = notificationType_id;
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

    public Integer getNotificationType_id() {
        return notificationType_id;
    }

    public void setNotificationType_id(Integer notificationType_id) {
        this.notificationType_id = notificationType_id;
    }
}
