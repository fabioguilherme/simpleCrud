package com.portofolio.demo.infrastructure.persistence.notification;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.notification.NotificationFixture;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.infrastructure.persistence.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class NotificationRepositoryServiceImplIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepositoryService service;


    @Test
    public void canCreate() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        String message = "fake-message";
        Notification notificationToPersist = NotificationFixture.getNotificationForUser(user, message);

        // When
        Notification notificationPersisted = service.save(notificationToPersist);

        // Then
        Optional<Notification> notificationFoundOptional = repository.findById(notificationPersisted.getId());

        assertThat(notificationFoundOptional).isPresent();

        Notification notification = notificationFoundOptional.get();

        assertThat(notification.getUser()).isNotNull();

        assertThat(notification.getMessage()).isEqualTo(message);
    }

    @Test
    public void shouldThrowAnExceptionIfItemIsNullWhenCreating() {
        // Given
        Notification notificationToPersist = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.save(notificationToPersist)).withMessage("Notification can not be null");
    }

    @Test
    public void canDeleteById() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        String message = "fake-message";
        Notification notificationToPersist = NotificationFixture.getNotificationForUser(user, message);
        Notification notificationPersisted = service.save(notificationToPersist);

        // When
        service.deleteById(notificationPersisted.getId());

        // Then
        Optional<Notification> notificationFoundOptional = repository.findById(notificationPersisted.getId());

        assertThat(notificationFoundOptional).isEmpty();
    }

    @Test
    public void shouldThrowAnExceptionIfIdIsNullWhenDeleting() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.deleteById(id)).withMessage("Id can not be null");
    }

    @Test
    public void canGetById() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        String message = "fake-message";
        Notification notificationToPersist = NotificationFixture.getNotificationForUser(user, message);
        Notification notificationPersisted = service.save(notificationToPersist);
        Long id = notificationPersisted.getId();

        // When
        Optional<Notification> notificationFoundOptional = service.getById(id);

        // Then
        assertThat(notificationFoundOptional).isPresent();
    }

    @Test
    public void shouldThrowAnExceptionIfIdIsNullWhenGettingById() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.getById(id)).withMessage("Id can not be null");
    }

    @Test
    public void canGetAll() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        String message = "fake-message";
        Notification notificationToPersist = NotificationFixture.getNotificationForUser(user, message);
        Notification notificationPersisted = service.save(notificationToPersist);

        String message2 = "fake-message2";
        Notification notificationToPersist2 = NotificationFixture.getNotificationForUser(user, message2);
        Notification notificationPersisted2 = service.save(notificationToPersist2);


        // When
        List<Notification> list = service.getAll(null);

        // Then
        assertThat(list).hasSize(2);

        assertThat(list).anyMatch(i -> i.getMessage().equals(notificationPersisted.getMessage()) && notificationPersisted.getUser().getName().equals(user.getName()));
        assertThat(list).anyMatch(i -> i.getMessage().equals(notificationPersisted2.getMessage()) && notificationPersisted2.getUser().getName().equals(user.getName()));
    }

    @Test
    public void canGetAllByUserId() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        User user2 = userRepository.save(UserFixture.getUser());
        Long userId = user.getId();
        String message = "fake-message";
        Notification notificationToPersist = NotificationFixture.getNotificationForUser(user, message);
        Notification notificationPersisted = service.save(notificationToPersist);

        String message2 = "fake-message2";
        Notification notificationToPersist2 = NotificationFixture.getNotificationForUser(user2, message2);
        service.save(notificationToPersist2);


        // When
        List<Notification> list = service.getAll(userId);

        // Then
        assertThat(list).hasSize(1);

        assertThat(list).anyMatch(i -> i.getUser().getId().equals(userId) && i.getMessage().equals(notificationPersisted.getMessage()) && notificationPersisted.getUser().getName().equals(user.getName()));
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
        userRepository.deleteAll();
    }
}