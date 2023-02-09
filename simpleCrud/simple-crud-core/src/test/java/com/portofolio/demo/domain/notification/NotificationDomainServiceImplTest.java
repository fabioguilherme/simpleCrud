package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class NotificationDomainServiceImplTest {

    private NotificationDomainService notificationDomainService;

    @BeforeEach
    void setUp() {
        this.notificationDomainService = new NotificationDomainServiceImpl();
    }

    @Test
    void canCreateANotification() throws Exception {
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

    @Test
    void shouldThrowAnExceptionWhenCreatingANotificationWithNullUser() throws Exception {
        // Given
        String message = "fake message";
        User user = null;

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> this.notificationDomainService.createNotification(user, message)).withMessage("User and message can not be null");
    }

    @Test
    void shouldThrowAnExceptionWhenCreatingANotificationWithNullMessage() throws Exception {
        // Given
        String message = null;
        User user = UserFixture.getUser();

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> this.notificationDomainService.createNotification(user, message)).withMessage("User and message can not be null");
    }
}