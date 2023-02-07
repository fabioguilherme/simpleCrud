package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;

public class NotificationFixture {

    public static Notification getNotification() {

        String message = "fake message";
        User user = UserFixture.getUser();

        return Notification.Builder.with().message(message).user(user).build();

    }

    public static Notification getNotificationForUser(User user, String message) {

        return Notification.Builder.with().message(message).user(user).build();

    }
}
