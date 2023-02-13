package com.portofolio.demo.aplication.stock.model;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateStockRequestTest {
    @Test
    public void canBuild() {
        // Given
        Long itemId = 1L;
        int quantity = 5;

        // When
        CreateStockRequest request = CreateStockRequest.Builder.with().itemId(itemId).quantity(quantity).build();

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getItemId()).isEqualTo(itemId);
        assertThat(request.getQuantity()).isEqualTo(quantity);
    }
}