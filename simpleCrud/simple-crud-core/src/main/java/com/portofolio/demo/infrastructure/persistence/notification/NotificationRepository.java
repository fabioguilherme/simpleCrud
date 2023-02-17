package com.portofolio.demo.infrastructure.persistence.notification;

import com.portofolio.demo.domain.notification.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    @Query("SELECT n from Notification n WHERE n.user.id = :userId")
    List<Notification> findNotificationByUserId(@Param("userId") Long userId);
}
