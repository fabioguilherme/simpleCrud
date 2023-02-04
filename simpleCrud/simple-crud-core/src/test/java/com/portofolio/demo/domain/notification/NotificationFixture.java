package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;

public class NotificationFixture {

    public static Notification.Builder getNotification() {

        String message = "fake message";
        User user = UserFixture.getUser().build();

        return Notification.Builder.with().message(message).user(user);

    }
}
