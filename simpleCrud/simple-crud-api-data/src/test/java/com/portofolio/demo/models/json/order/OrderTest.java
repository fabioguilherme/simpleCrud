package com.portofolio.demo.models.json.order;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    void canBuild() {

        Long id = 1L;
        int quantity = 5;
        String itemName = "fake-name";
        String userName = "username-fake";
        String userEmail = "user@fakeemail.com";
        LocalDateTime creationDate = LocalDateTime.now();
        String uri = "fake-uri";
        String status = "fake-status";

        // When
        Order order = new Order();
        order.setId(id);
        order.setQuantity(quantity);
        order.setItemName(itemName);
        order.setUserName(userName);
        order.setUserEmail(userEmail);
        order.setCreationDate(creationDate);
        order.setUri(uri);
        order.setStatus(status);

        // Then
        assertThat(order).isNotNull();

        assertThat(order.getId()).isEqualTo(id);
        assertThat(order.getQuantity()).isEqualTo(quantity);
        assertThat(order.getItemName()).isEqualTo(itemName);
        assertThat(order.getUserEmail()).isEqualTo(userEmail);
        assertThat(order.getUserName()).isEqualTo(userName);
        assertThat(order.getCreationDate()).isEqualTo(creationDate);
        assertThat(order.getUri()).isEqualTo(uri);
        assertThat(order.getStatus()).isEqualTo(status);
    }
}