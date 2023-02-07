package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationDomainServiceImplTest {

    private NotificationDomainService notificationDomainService;

    @BeforeEach
    void setUp() {
        this.notificationDomainService = new NotificationDomainServiceImpl();
    }

    @Test
    void canCreateANotification() {
        // Given
        String message = "fake message";
        User user = UserFixture.getUser();

        // When
        Notification notification = this.notificationDomainService.createNotification(user, message);

        // Then
        assertThat(notification).isNotNull();

        assertThat(notification.getCreationDate()).isNotNull();
        assertThat(notification.getType()).isEqualTo(NotificationType.EMAIL);
        assertThat(notification.getUser()).isEqualTo(user);
        assertThat(notification.getMessage()).isEqualTo(message);
    }
}