package com.portofolio.demo.models.json.notification;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationTest {

    @Test
    void canBuild() {
        // Given
        Long id = 1L;
        String message = "fake-message";
        String userName = "fake-name";
        String userEmail = "fake-email@fake.com";
        String uri = "http:/localhostfake:8080/api/notification/1";
        LocalDateTime creationDate = LocalDateTime.now();

        // When
        Notification json = new Notification();
        json.setId(id);
        json.setMessage(message);
        json.setUserName(userName);
        json.setUserEmail(userEmail);
        json.setUri(uri);
        json.setCreationDate(creationDate);

        // Then

        Set<ConstraintViolation<Notification>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);


        assertThat(violations.size()).isEqualTo(0);


        assertThat(json).isNotNull();

        assertThat(json.getId()).isEqualTo(id);
        assertThat(json.getMessage()).isEqualTo(message);
        assertThat(json.getUserName()).isEqualTo(userName);
        assertThat(json.getUserEmail()).isEqualTo(userEmail);
        assertThat(json.getUri()).isEqualTo(uri);
        assertThat(json.getCreationDate()).isEqualTo(creationDate);

    }
}