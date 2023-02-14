package com.portofolio.demo.aplication.order.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrderRequestTest {

    @Test
    void canCreate() {
        // Given
        Long itemId = 1L;
        Long userId = 1L;

        // When
        CreateOrderRequest request = CreateOrderRequest.Builder.with().itemId(itemId).userId(userId).build();

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getItemId()).isEqualTo(itemId);
        assertThat(request.getUserId()).isEqualTo(userId);
    }
}