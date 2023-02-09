package com.portofolio.demo.aplication.notification;

import com.portofolio.demo.aplication.notification.model.CreateNotificationRequest;
import com.portofolio.demo.aplication.notification.model.NotificationDto;
import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.notification.NotificationDomainService;
import com.portofolio.demo.domain.notification.NotificationFixture;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.infrastructure.persistence.notification.NotificationRepositoryService;
import com.portofolio.demo.infrastructure.persistence.user.UserRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class NotificationApplicationServiceImplTest {

    private final String URI_BASE = "fake-uribase";
    private final String ITEM_URI_BASE = "/notification/";

    @Mock
    private UserRepositoryService userRepositoryService;
    @Mock
    private NotificationDomainService notificationDomainService;
    @Mock
    private NotificationRepositoryService notificationRepositoryService;

    private NotificationApplicationServiceImpl applicationService;

    @BeforeEach
    public void setUp() throws Exception {
        this.applicationService = new NotificationApplicationServiceImpl(userRepositoryService, notificationRepositoryService, notificationDomainService, URI_BASE);
    }

    @Test
    public void canSaveAnNotification() throws Exception {
        // Given
        String message = "fake-name";
        Long userId = 1L;
        CreateNotificationRequest request = CreateNotificationRequest.Builder.with().message(message).userId(userId).build();
        User user = UserFixture.getUser();
        Notification notificationPersisted = NotificationFixture.getNotificationForUser(user, message);

        Mockito.when(userRepositoryService.getById(userId)).thenReturn(Optional.of(user));
        Mockito.when(notificationDomainService.createNotification(user, message)).thenReturn(notificationPersisted);
        Mockito.when(notificationRepositoryService.save(any())).thenReturn(notificationPersisted);

        // When
        NotificationDto response = applicationService.save(request);

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getId()).isEqualTo(notificationPersisted.getId());
        assertThat(response.getMessage()).isEqualTo(notificationPersisted.getMessage());
        assertThat(response.getUri()).isEqualTo(URI_BASE + ITEM_URI_BASE + notificationPersisted.getId());
        assertThat(response.getUserName()).isEqualTo(notificationPersisted.getUser().getName());
        assertThat(response.getUserEmail()).isEqualTo(notificationPersisted.getUser().getEmail());

        ArgumentCaptor<Notification> argumentCaptor = ArgumentCaptor.forClass(Notification.class);

        Mockito.verify(userRepositoryService).getById(userId);
        Mockito.verify(notificationDomainService).createNotification(user, message);
        Mockito.verify(notificationRepositoryService).save(any());
    }

    @Test
    public void canDeleteById() {
        // Given
        Long id = 1L;

        // When
        applicationService.deleteById(id);

        // Then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(notificationRepositoryService).deleteById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void canGetNotificationById() throws Exception {
        // Given
        Long id = 1L;
        Notification notificationFound = NotificationFixture.getNotification();

        Mockito.when(notificationRepositoryService.getById(id)).thenReturn(Optional.of(notificationFound));

        // When
        Optional<NotificationDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isPresent();

        NotificationDto dto = response.get();

        assertThat(response).isNotNull();

        assertThat(dto.getId()).isEqualTo(notificationFound.getId());
        assertThat(dto.getMessage()).isEqualTo(notificationFound.getMessage());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ITEM_URI_BASE + notificationFound.getId());
        assertThat(dto.getUserName()).isEqualTo(notificationFound.getUser().getName());
        assertThat(dto.getUserEmail()).isEqualTo(notificationFound.getUser().getEmail());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(notificationRepositoryService).getById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void shouldReturnOptionalEmptyIfNotificationDoNotExists() throws Exception {
        // Given
        Long id = 1L;
        Mockito.when(notificationRepositoryService.getById(id)).thenReturn(Optional.empty());

        // When
        Optional<NotificationDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isEmpty();
    }

    @Test
    public void canGetAll() throws Exception {
        // Given
        Notification notificationFound = NotificationFixture.getNotification();

        Mockito.when(notificationRepositoryService.getAll()).thenReturn(Collections.singletonList(notificationFound));

        // When
        List<NotificationDto> response = applicationService.getAll();

        // Then
        assertThat(response).hasSize(1);

        NotificationDto dto = response.get(0);

        assertThat(dto.getId()).isEqualTo(notificationFound.getId());
        assertThat(dto.getMessage()).isEqualTo(notificationFound.getMessage());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ITEM_URI_BASE + notificationFound.getId());
        assertThat(dto.getUserName()).isEqualTo(notificationFound.getUser().getName());
        assertThat(dto.getUserEmail()).isEqualTo(notificationFound.getUser().getEmail());
    }
}