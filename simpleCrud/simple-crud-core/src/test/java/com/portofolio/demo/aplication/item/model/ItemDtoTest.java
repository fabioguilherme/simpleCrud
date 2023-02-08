package com.portofolio.demo.aplication.item.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemDtoTest {
    @Test
    void canBuild() {
        // Given
        String name = "fake-name";
        String uri = "http://fake/api/item/1";
        Long id = 1L;

        // When
        ItemDto response = ItemDto.Builder.with().name(name).uri(uri).id(id).build();

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getUri()).isEqualTo(uri);
    }
}