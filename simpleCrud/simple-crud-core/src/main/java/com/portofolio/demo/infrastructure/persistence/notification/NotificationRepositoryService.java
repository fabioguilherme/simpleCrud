package com.portofolio.demo.infrastructure.persistence.notification;

import com.portofolio.demo.domain.notification.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryService {

    Notification save(Notification notification);

    void deleteById(Long id);

    Optional<Notification> getById(Long id);

    List<Notification> getAll();
}
