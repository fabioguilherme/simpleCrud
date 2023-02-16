package com.portofolio.demo.models.json.stock;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateStockRequestJsonTest {

    @Test
    void canBuild() {
        // Given
        Integer quantity = 1;

        // When
        UpdateStockRequestJson request = new UpdateStockRequestJson();
        request.setQuantity(quantity);

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getQuantity()).isEqualTo(quantity);

        Set<ConstraintViolation<UpdateStockRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void shouldBeInvalidIfQuantityIsNull() {
        // Given
        Integer quantity = null;

        // When
        UpdateStockRequestJson request = new UpdateStockRequestJson();
        request.setQuantity(quantity);

        // Then
        Set<ConstraintViolation<UpdateStockRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<UpdateStockRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Quantity must not be null");
    }

    @Test
    void shouldBeInvalidIfQuantityIsZero() {
        // Given
        Integer quantity = 0;

        // When
        UpdateStockRequestJson request = new UpdateStockRequestJson();
        request.setQuantity(quantity);

        // Then
        Set<ConstraintViolation<UpdateStockRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<UpdateStockRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Minimum value for the quantity is 1.");
    }
}