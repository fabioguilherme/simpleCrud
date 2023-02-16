package com.portofolio.demo.models.json.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeStatusRequestJsonTest {

    @Test
    void canBuildDRAFT() {
        // Given
        String status = "DRAFT";
        // When
        ChangeStatusRequestJson json = new ChangeStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(0);
    }

    @Test
    void canBuildPROCESSING() {
        // Given
        String status = "PROCESSING";
        // When
        ChangeStatusRequestJson json = new ChangeStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(0);
    }

    @Test
    void canBuildDONE() {
        // Given
        String status = "DONE";
        // When
        ChangeStatusRequestJson json = new ChangeStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(0);
    }

    @Test
    void shouldBeInvalidIfNull() {
        // Given
        String status = null;
        // When
        ChangeStatusRequestJson json = new ChangeStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<ChangeStatusRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Status must not be null");

    }

    @Test
    void shouldBeInvalidIfWrongStatus() {
        // Given
        String status = "blah";
        // When
        ChangeStatusRequestJson json = new ChangeStatusRequestJson();
        json.setStatus(status);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<ChangeStatusRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<ChangeStatusRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Status possible values: DRAFT, PROCESSING,DONE");

    }

}