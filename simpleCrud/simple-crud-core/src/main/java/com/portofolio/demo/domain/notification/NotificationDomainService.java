package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;

public interface NotificationDomainService {

    Notification createNotification(User user, String message);
}
