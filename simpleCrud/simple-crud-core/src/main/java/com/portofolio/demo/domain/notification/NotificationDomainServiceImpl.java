package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.shared.errors.BusinessException;

public class NotificationDomainServiceImpl implements NotificationDomainService {


    @Override
    public Notification createNotification(User user, String message) {

        if (user == null || message == null) {
            throw new BusinessException("User and message can not be null", null);
        }

        return Notification.Builder.with().user(user).message(message).build();
    }
}
