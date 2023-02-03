package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationTest {

    @Test
    void canBuild() {
        // Given
        String message = "fake message";
        User user = UserFixture.getUser().build();

        // When
        Notification notification = Notification.Builder.with().message(message).user(user).build();

        // Then
        assertThat(notification).isNotNull();

        assertThat(notification.getCreationDate()).isNotNull();
        assertThat(notification.getType()).isEqualTo(NotificationType.EMAIL);
        assertThat(notification.getUser()).isEqualTo(user);
        assertThat(notification.getMessage()).isEqualTo(message);
    }
}