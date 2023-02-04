package com.portofolio.demo.models.json;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void canBuild() {
        // Given
        String name = "fakename";

        // When
        Item item = Item.withName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);
    }
}