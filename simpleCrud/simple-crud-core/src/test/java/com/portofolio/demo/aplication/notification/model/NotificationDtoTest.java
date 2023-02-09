package com.portofolio.demo.aplication.notification.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationDtoTest {

    @Test
    void canBuild() {
        // Given
        Long id = 1L;
        String message = "fake-message";
        String userName = "fake-name";
        String userEmail = "fake-email@fake.com";
        String uri = "http:/localhostfake:8080/api/user/1";
        LocalDateTime creationDate = LocalDateTime.now();

        // When
        NotificationDto dto = NotificationDto.Builder.with().id(id).message(message).userName(userName).userEmail(userEmail).uri(uri).build();

        // Then
        assertThat(dto).isNotNull();

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getMessage()).isEqualTo(message);
        assertThat(dto.getUserName()).isEqualTo(userName);
        assertThat(dto.getUserEmail()).isEqualTo(userEmail);
        assertThat(dto.getUri()).isEqualTo(uri);
    }
}