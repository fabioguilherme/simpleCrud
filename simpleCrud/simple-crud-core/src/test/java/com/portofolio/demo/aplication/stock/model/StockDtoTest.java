package com.portofolio.demo.aplication.stock.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockDtoTest {

    @Test
    void canBuild() {
        // Given
        Long id = 1L;
        int quantity = 5;
        String itemName = "fake-item";
        String uri = "fake-uri";

        // When
        StockDto dto = StockDto.Builder.with()
                .id(id)
                .quantity(quantity)
                .itemName(itemName)
                .uri(uri).build();

        // Then
        assertThat(dto).isNotNull();

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getQuantity()).isEqualTo(quantity);
        assertThat(dto.getItemName()).isEqualTo(itemName);
        assertThat(dto.getUri()).isEqualTo(uri);
    }
}