package com.api.retoBCP.repository;

import com.api.retoBCP.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType,Integer> {

}
