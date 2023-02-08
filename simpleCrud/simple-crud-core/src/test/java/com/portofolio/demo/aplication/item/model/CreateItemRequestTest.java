package com.portofolio.demo.aplication.item.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateItemRequestTest {

    @Test
    void canBuild() {
        // Given
        String name = "fake-name";

        // When
        CreateItemRequest request = CreateItemRequest.withName(name);

        // Then
        assertThat(request.getName()).isEqualTo(name);
    }
}