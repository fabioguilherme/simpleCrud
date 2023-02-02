package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.Item.Item;
import com.portofolio.demo.domain.Item.ItemFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StockTest {

    @Test
    public void canBuild() {
        // Given
        int quantity = 1;
        Item item = ItemFixture.getItem();

        // When
        Stock stock = Stock.Builder.with().item(item).quantity(quantity).build();

        // Then
        assertThat(stock).isNotNull();

        assertThat(stock.getItem()).isEqualTo(item);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
        assertThat(stock.getCreationDate()).isNotNull();
    }

    @Test
    public void shouldThrowBusinessExceptionIfItemIsNull() {
        // Given
        int quantity = 1;
        Item item = null;
        Stock.Builder stockBuilder = Stock.Builder.with().item(item).quantity(quantity);

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(stockBuilder::build).withMessage("Item can not be null");
    }

    @Test
    public void shouldThrowBusinessExceptionIfQuantityIsBellowZero() {
        // Given
        int quantity = -1;
        Item item = ItemFixture.getItem();
        Stock.Builder stockBuilder = Stock.Builder.with().item(item).quantity(quantity);

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(stockBuilder::build).withMessage("Quantity can not be bellow zero");
    }
}