package com.portofolio.demo.aplication.notification.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateNotificationRequestTest {

    @Test
    void canBuild() {
        // Given
        Long userID = 1L;
        String message = "fake-message";

        // When
        CreateNotificationRequest request = CreateNotificationRequest.Builder.with().userId(userID).message(message).build();

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getUserId()).isEqualTo(userID);
        assertThat(request.getMessage()).isEqualTo(message);
    }
}