package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class NotificationFixture {

    public static Notification getNotification() throws Exception {

        String message = "fake message";
        User user = UserFixture.getUser();

        Notification notification = Notification.Builder.with().user(user).message(message).build();

        Field field = notification.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, notification, 1L);

        return notification;

    }

    public static Notification getNotificationForUser(User user, String message) throws Exception {

        Notification notification = Notification.Builder.with().user(user).message(message).build();

        Field field = notification.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, notification, 1L);

        return notification;

    }
}
