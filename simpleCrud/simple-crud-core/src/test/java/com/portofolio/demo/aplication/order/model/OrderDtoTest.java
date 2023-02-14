package com.portofolio.demo.aplication.order.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderDtoTest {

    @Test
    void canBuild() {
        // Given
        Long id = 1L;
        int quantity = 5;
        String itemName = "fake-name";
        String userName = "username-fake";
        String userEmail = "user@fakeemail.com";
        LocalDateTime creationDate = LocalDateTime.now();
        String uri = "fake-uri";

        // When
        OrderDto dto = OrderDto.Builder.with()
                .id(id)
                .quantity(quantity)
                .itemName(itemName)
                .userName(userName)
                .userEmail(userEmail)
                .creationDate(creationDate)
                .uri(uri)
                .build();

        // Then
        assertThat(dto).isNotNull();

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getQuantity()).isEqualTo(quantity);
        assertThat(dto.getItemName()).isEqualTo(itemName);
        assertThat(dto.getUserEmail()).isEqualTo(userEmail);
        assertThat(dto.getUserName()).isEqualTo(userName);
        assertThat(dto.getCreationDate()).isEqualTo(creationDate);
        assertThat(dto.getUri()).isEqualTo(uri);

    }
}