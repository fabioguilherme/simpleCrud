package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;

public class NotificationDomainServiceImpl implements NotificationDomainService {


    @Override
    public Notification createNotification(User user, String message) {
        return Notification.Builder.with().user(user).message(message).build();
    }
}
