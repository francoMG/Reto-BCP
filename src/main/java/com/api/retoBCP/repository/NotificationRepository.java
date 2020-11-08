package com.api.retoBCP.repository;

import com.api.retoBCP.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {

    @Query(value = "SELECT * FROM notificaciones u WHERE u.user_id = :id ",
            nativeQuery = true)
    List<Notification> getByUserId(
            @Param("id") Integer id);

    @Query(value = "SELECT * FROM notificaciones u WHERE u.user_id = :id AND u.read_notif = FALSE ",
            nativeQuery = true)
    List<Notification> findReadByUser(
            @Param("id") Integer id);
}
