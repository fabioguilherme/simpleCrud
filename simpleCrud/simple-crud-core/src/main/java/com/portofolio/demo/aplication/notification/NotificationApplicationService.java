package com.portofolio.demo.aplication.notification;

import com.portofolio.demo.aplication.notification.model.CreateNotificationRequest;
import com.portofolio.demo.aplication.notification.model.NotificationDto;

import java.util.List;
import java.util.Optional;

public interface NotificationApplicationService {

    NotificationDto save(CreateNotificationRequest request);

    void deleteById(Long id);

    Optional<NotificationDto> getById(Long id);

    List<NotificationDto> getAll();
}
