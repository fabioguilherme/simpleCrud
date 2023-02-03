package com.portofolio.demo.domain.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemDomainServiceImplTest {

    private ItemDomainService service;

    @BeforeEach
    void setUp() {
        this.service = new ItemServiceDomainImpl();
    }

    @Test
    void canCreateAnewItem() {
        // Given
        String name = "fake-name";

        // When
        Item newItem = this.service.createItem(name);

        // Then
        assertThat(newItem).isNotNull();

        assertThat(newItem.getName()).isEqualTo(name);
    }
}