package com.portofolio.demo.models.json.notification;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateNotificationRequestJsonTest {

    @Test
    void canBuild() {
        // Given
        Long userId = 1L;
        String message = "fake-message";

        // When
        CreateNotificationRequestJson notification = new CreateNotificationRequestJson();
        notification.setUserId(userId);
        notification.setMessage(message);

        // Then
        assertThat(notification).isNotNull();

        assertThat(notification.getUserId()).isEqualTo(userId);
        assertThat(notification.getMessage()).isEqualTo(message);

        Set<ConstraintViolation<CreateNotificationRequestJson>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(notification);


        assertThat(vialations.size()).isEqualTo(0);
    }

    @Test
    void shouldBeInvalidIfUserIdIsNull() {
        // Given
        Long userId = null;
        String message = "fake-message";

        // When
        CreateNotificationRequestJson notification = new CreateNotificationRequestJson();
        notification.setUserId(userId);
        notification.setMessage(message);

        // Then
        assertThat(notification).isNotNull();

        assertThat(notification.getUserId()).isEqualTo(userId);
        assertThat(notification.getMessage()).isEqualTo(message);

        Set<ConstraintViolation<CreateNotificationRequestJson>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(notification);


        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateNotificationRequestJson>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("must not be null");
    }

    @Test
    void shouldBeInvalidIfMessageIsNull() {
        // Given
        Long userId = 1L;
        String message = null;

        // When
        CreateNotificationRequestJson notification = new CreateNotificationRequestJson();
        notification.setUserId(userId);
        notification.setMessage(message);

        // Then
        assertThat(notification).isNotNull();

        assertThat(notification.getUserId()).isEqualTo(userId);
        assertThat(notification.getMessage()).isEqualTo(message);

        Set<ConstraintViolation<CreateNotificationRequestJson>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(notification);


        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateNotificationRequestJson>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("must not be null");
    }

    @Test
    void shouldBeInvalidIfEmptyIsNull() {
        // Given
        Long userId = 1L;
        String message = "";

        // When
        CreateNotificationRequestJson notification = new CreateNotificationRequestJson();
        notification.setUserId(userId);
        notification.setMessage(message);

        // Then
        assertThat(notification).isNotNull();

        assertThat(notification.getUserId()).isEqualTo(userId);
        assertThat(notification.getMessage()).isEqualTo(message);

        Set<ConstraintViolation<CreateNotificationRequestJson>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(notification);


        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateNotificationRequestJson>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Message of the notification can not be empty");
    }

}