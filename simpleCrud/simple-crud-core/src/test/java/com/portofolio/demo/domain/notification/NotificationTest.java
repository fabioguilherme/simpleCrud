package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class NotificationTest {

    @Test
    void canBuild() throws Exception {
        // Given
        String message = "fake message";
        User user = UserFixture.getUser();

        // When
        Notification notification = Notification.Builder.with().message(message).user(user).build();

        // Then
        assertThat(notification).isNotNull();

        assertThat(notification.getCreationDate()).isNotNull();
        assertThat(notification.getType()).isEqualTo(NotificationType.EMAIL);
        assertThat(notification.getUser()).isEqualTo(user);
        assertThat(notification.getMessage()).isEqualTo(message);
    }

    @Test
    void shouldThrowExceptionIfMessageIsNull() throws Exception {
        // Given
        String message = null;
        User user = UserFixture.getUser();

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Notification.Builder.with().message(message).user(user).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Message can not be null");
    }

    @Test
    void shouldThrowExceptionIfUserIsNull() {
        // Given
        String message = "fake-message";
        User user = null;

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Notification.Builder.with().message(message).user(user).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("User can not be null");
    }
}