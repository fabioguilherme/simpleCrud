package com.portofolio.demo.models.json.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrderRequestJsonTest {

    @Test
    void canBuild() {
        // Given
        Long itemId = 1L;
        Long userId = 1L;
        int quantity = 5;

        // When
        CreateOrderRequestJson json = new CreateOrderRequestJson();
        json.setItemId(itemId);
        json.setUserId(userId);
        json.setQuantity(quantity);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<CreateOrderRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(0);

        assertThat(json.getItemId()).isEqualTo(itemId);
        assertThat(json.getUserId()).isEqualTo(userId);
        assertThat(json.getQuantity()).isEqualTo(quantity);

    }

    @Test
    void shouldBeInvalidIfItemIdIsNull() {
        // Given
        Long itemId = null;
        Long userId = 1L;
        Integer quantity = 5;

        // When
        CreateOrderRequestJson json = new CreateOrderRequestJson();
        json.setItemId(itemId);
        json.setUserId(userId);
        json.setQuantity(quantity);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<CreateOrderRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<CreateOrderRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("ItemId must not be null");
    }

    @Test
    void shouldBeInvalidIfUserIdIsNull() {
        // Given
        Long itemId = 1L;
        Long userId = null;
        Integer quantity = 5;

        // When
        CreateOrderRequestJson json = new CreateOrderRequestJson();
        json.setItemId(itemId);
        json.setUserId(userId);
        json.setQuantity(quantity);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<CreateOrderRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<CreateOrderRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("UserId must not be null");
    }

    @Test
    void shouldBeInvalidIfQuantityIsNull() {
        // Given
        Long itemId = 1L;
        Long userId = 1L;
        Integer quantity = null;

        // When
        CreateOrderRequestJson json = new CreateOrderRequestJson();
        json.setItemId(itemId);
        json.setUserId(userId);
        json.setQuantity(quantity);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<CreateOrderRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<CreateOrderRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Quantity must not be null");
    }

    @Test
    void shouldBeInvalidIfQuantityIsZero() {
        // Given
        Long itemId = 1L;
        Long userId = 1L;
        Integer quantity = 0;

        // When
        CreateOrderRequestJson json = new CreateOrderRequestJson();
        json.setItemId(itemId);
        json.setUserId(userId);
        json.setQuantity(quantity);

        // Then
        assertThat(json).isNotNull();

        Set<ConstraintViolation<CreateOrderRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(json);

        assertThat(violations).hasSize(1);
        List<ConstraintViolation<CreateOrderRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Minimum value for the quantity is 1.");
    }
}