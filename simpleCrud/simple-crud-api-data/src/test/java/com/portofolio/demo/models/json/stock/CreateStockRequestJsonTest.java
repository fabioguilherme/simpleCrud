package com.portofolio.demo.models.json.stock;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateStockRequestJsonTest {

    @Test
    void canBuild() {
        // Given
        Long itemId = 1L;
        Integer quantity = 1;

        // When
        CreateStockRequestJson request = new CreateStockRequestJson();
        request.setItemId(itemId);
        request.setQuantity(quantity);

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getItemId()).isEqualTo(itemId);
        assertThat(request.getQuantity()).isEqualTo(quantity);

        Set<ConstraintViolation<CreateStockRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void shouldBeInvalidIfItemIdIsNull() {
        // Given
        Long itemId = null;
        Integer quantity = 1;

        // When
        CreateStockRequestJson request = new CreateStockRequestJson();
        request.setItemId(itemId);
        request.setQuantity(quantity);

        // Then
        Set<ConstraintViolation<CreateStockRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateStockRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("ItemId must not be null");
    }

    @Test
    void shouldBeInvalidIfQuantityIsNull() {
        // Given
        Long itemId = 1L;
        Integer quantity = null;

        // When
        CreateStockRequestJson request = new CreateStockRequestJson();
        request.setItemId(itemId);
        request.setQuantity(quantity);

        // Then
        Set<ConstraintViolation<CreateStockRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateStockRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Quantity must not be null");
    }

    @Test
    void shouldBeInvalidIfQuantityIsZero() {
        // Given
        Long itemId = 1L;
        Integer quantity = 0;

        // When
        CreateStockRequestJson request = new CreateStockRequestJson();
        request.setItemId(itemId);
        request.setQuantity(quantity);

        // Then
        Set<ConstraintViolation<CreateStockRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateStockRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Minimum value for the quantity is 1.");
    }
}