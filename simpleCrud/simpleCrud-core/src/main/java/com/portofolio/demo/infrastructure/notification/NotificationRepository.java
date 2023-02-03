package com.portofolio.demo.infrastructure.notification;

import com.portofolio.demo.domain.notification.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
