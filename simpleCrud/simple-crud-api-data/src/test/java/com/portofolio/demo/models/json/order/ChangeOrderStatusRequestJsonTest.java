package com.portofolio.demo.models.json.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeOrderStatusRequestJsonTest {

    @Test
    void canBuildDRAFT() {
        // Given
        String status = "DRAFT";
        // When
        ChangeOrderStatusRequestJson json = new ChangeOrderStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeOrderStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(0);
    }

    @Test
    void canBuildPROCESSING() {
        // Given
        String status = "PROCESSING";
        // When
        ChangeOrderStatusRequestJson json = new ChangeOrderStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeOrderStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(0);
    }

    @Test
    void canBuildDONE() {
        // Given
        String status = "DONE";
        // When
        ChangeOrderStatusRequestJson json = new ChangeOrderStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeOrderStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(0);
    }

    @Test
    void shouldBeInvalidIfNull() {
        // Given
        String status = null;
        // When
        ChangeOrderStatusRequestJson json = new ChangeOrderStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeOrderStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<ChangeOrderStatusRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Status must not be null");

    }

    @Test
    void shouldBeInvalidIfWrongStatus() {
        // Given
        String status = "blah";
        // When
        ChangeOrderStatusRequestJson json = new ChangeOrderStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeOrderStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<ChangeOrderStatusRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Status possible values: DRAFT, PROCESSING, DONE");

    }

}