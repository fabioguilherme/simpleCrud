package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.Item.Item;
import com.portofolio.demo.domain.Item.ItemFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockDomainServiceImplTest {

    private StockDomainService stockDomainService;

    @BeforeEach
    void setUp() {
        this.stockDomainService = new StockDomainServiceImpl();
    }

    @Test
    public void canBuild() {
        // Given
        int quantity = 1;
        Item item = ItemFixture.getItem();

        // When
        Stock stock = this.stockDomainService.createStock(item, quantity);

        // Then
        assertThat(stock).isNotNull();

        assertThat(stock.getItem()).isEqualTo(item);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
        assertThat(stock.getCreationDate()).isNotNull();
    }

    @Test
    void canAddMoreQuantity() {
        // Given
        int moreItemsToAdd = 3;
        int oldQuantity = 1;
        Stock stock = StockFixture.getStock().quantity(oldQuantity).build();

        // When
        this.stockDomainService.addStock(stock, moreItemsToAdd);

        // Then
        assertThat(stock.getQuantity()).isEqualTo(oldQuantity + moreItemsToAdd);
    }

    @Test
    void canRemoveQuantity() {
        // Given
        int numberItemsToRemove = 3;
        int oldQuantity = 4;
        Stock stock = StockFixture.getStock().quantity(oldQuantity).build();

        // When
        this.stockDomainService.subtractStock(stock, numberItemsToRemove);

        // Then
        assertThat(stock.getQuantity()).isEqualTo(oldQuantity - numberItemsToRemove);
    }
}