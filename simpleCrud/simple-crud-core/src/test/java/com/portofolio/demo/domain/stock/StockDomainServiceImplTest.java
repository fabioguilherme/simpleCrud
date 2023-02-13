package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StockDomainServiceImplTest {

    private StockDomainService stockDomainService;

    @BeforeEach
    void setUp() {
        this.stockDomainService = new StockDomainServiceImpl();
    }

    @Test
    public void canBuild() throws Exception {
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
    void canAddMoreQuantity() throws Exception {
        // Given
        int moreItemsToAdd = 3;
        int oldQuantity = 1;
        Stock stock = Stock.Builder.with().item(ItemFixture.getItem()).quantity(oldQuantity).build();

        // When
        this.stockDomainService.addStock(stock, moreItemsToAdd);

        // Then
        assertThat(stock.getQuantity()).isEqualTo(oldQuantity + moreItemsToAdd);
    }

    @Test
    void canSubtractQuantity() throws Exception {
        // Given
        int numberItemsToRemove = 3;
        int oldQuantity = 4;
        Stock stock = Stock.Builder.with().item(ItemFixture.getItem()).quantity(oldQuantity).build();

        // When
        this.stockDomainService.subtractStock(stock, numberItemsToRemove);

        // Then
        assertThat(stock.getQuantity()).isEqualTo(oldQuantity - numberItemsToRemove);
    }

    @Test
    void shouldThrowExceptionIfStockIsNullWhenAdding() throws Exception {
        // Given
        int moreItemsToAdd = 1;
        Stock stock = null;

        // When
        // Then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> this.stockDomainService.addStock(stock, moreItemsToAdd)).withMessage("stock can not be null");
    }

    @Test
    void shouldThrowExceptionIfStockIsNullWhenSubtracting() throws Exception {
        // Given
        int moreItemsToAdd = 1;
        Stock stock = null;

        // When
        // Then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> this.stockDomainService.subtractStock(stock, moreItemsToAdd)).withMessage("stock can not be null");
    }
}