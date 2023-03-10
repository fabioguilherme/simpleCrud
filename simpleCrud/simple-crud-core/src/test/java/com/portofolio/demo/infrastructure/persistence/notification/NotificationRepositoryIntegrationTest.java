package com.portofolio.demo.infrastructure.persistence.notification;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.notification.NotificationFixture;
import com.portofolio.demo.domain.notification.NotificationType;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.infrastructure.persistence.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository repository;

    @Test
    public void canPersist() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        String message = "this is a fake message";
        int quantity = 3;

        Notification notificationToStore = NotificationFixture.getNotificationForUser(user, message);

        // When
        Notification stored = repository.save(notificationToStore);

        // Then
        assertThat(stored).isNotNull();

        assertThat(stored.getUser().getId()).isEqualTo(user.getId());
        assertThat(stored.getMessage()).isEqualTo(message);
        assertThat(stored.getCreationDate()).isNotNull();
        assertThat(stored.getType()).isEqualTo(NotificationType.EMAIL);
    }

    @Test
    public void canFindById() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        Notification notification = repository.save(NotificationFixture.getNotificationForUser(user, "fake-message"));
        long id = notification.getId();

        // When
        Optional<Notification> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    public void canDeleteById() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        Notification notification = repository.save(NotificationFixture.getNotificationForUser(user, "fake-message"));
        long id = notification.getId();

        // When
        repository.deleteById(id);

        // Then
        Optional<Notification> storedOptional = repository.findById(id);

        assertThat(storedOptional).isEmpty();
    }

    @Test
    public void canFindByUserId() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        repository.save(NotificationFixture.getNotificationForUser(user, "fake-message"));
        repository.save(NotificationFixture.getNotificationForUser(user, "fake-message2"));

        // When
        List<Notification> storedOptional = repository.findNotificationByUserId(user.getId());

        // Then
        assertThat(storedOptional).isNotNull();

        assertThat(storedOptional).hasSize(2);
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
        userRepository.deleteAll();
    }
}
