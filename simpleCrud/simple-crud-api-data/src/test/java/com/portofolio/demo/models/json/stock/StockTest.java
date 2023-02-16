package com.portofolio.demo.models.json.stock;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockTest {

    @Test
    void canBuild() {
        // Given
        Long id = 1L;
        int quantity = 5;
        String itemName = "fake-item";
        String uri = "fake-uri";

        // When
        Stock request = new Stock();

        request.setId(id);
        request.setQuantity(quantity);
        request.setItemName(itemName);
        request.setUri(uri);

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getId()).isEqualTo(id);
        assertThat(request.getQuantity()).isEqualTo(quantity);
        assertThat(request.getItemName()).isEqualTo(itemName);
        assertThat(request.getUri()).isEqualTo(uri);
    }
}