package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StockTest {

    @Test
    public void canBuild() throws Exception {
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
    public void shouldThrowBusinessExceptionIfQuantityIsBellowZero() throws Exception {
        // Given
        int quantity = -1;
        Item item = ItemFixture.getItem();
        Stock.Builder stockBuilder = Stock.Builder.with().item(item).quantity(quantity);

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(stockBuilder::build).withMessage("Quantity can not be bellow zero");
    }

    @Test
    void canAddMoreQuantity() throws Exception {
        // Given
        int moreItemsToAdd = 3;
        int oldQuantity = 1;
        Stock stock = Stock.Builder.with().item(ItemFixture.getItem()).quantity(oldQuantity).build();

        // When
        stock.addStock(moreItemsToAdd);

        // Then
        assertThat(stock.getQuantity()).isEqualTo(oldQuantity + moreItemsToAdd);
    }

    @Test
    public void shouldThrowBusinessExceptionWhenAddingQuantityBellowZero() throws Exception {
        // Given
        int moreItemsToAdd = -3;
        int oldQuantity = 1;
        Stock stock = Stock.Builder.with().item(ItemFixture.getItem()).quantity(oldQuantity).build();

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> stock.addStock(moreItemsToAdd)).withMessage("Quantity can not be bellow zero");
    }

    @Test
    void canRemoveQuantity() throws Exception {
        // Given
        int numberItemsToRemove = 3;
        int oldQuantity = 4;
        Stock stock = Stock.Builder.with().item(ItemFixture.getItem()).quantity(oldQuantity).build();

        // When
        stock.subtractStock(numberItemsToRemove);

        // Then
        assertThat(stock.getQuantity()).isEqualTo(oldQuantity - numberItemsToRemove);
    }

    @Test
    public void shouldThrowBusinessExceptionWhenSubtractQuantityBellowZero() throws Exception {
        // Given
        int numberItemsToRemove = -3;
        int oldQuantity = 1;
        Stock stock = Stock.Builder.with().item(ItemFixture.getItem()).quantity(oldQuantity).build();

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> stock.subtractStock(numberItemsToRemove)).withMessage("Quantity can not be bellow zero");
    }

    @Test
    public void shouldThrowBusinessExceptionWhenQuantityIsBellowZero() throws Exception {
        // Given
        int numberItemsToRemove = 3;
        int oldQuantity = 1;
        Stock stock = Stock.Builder.with().item(ItemFixture.getItem()).quantity(oldQuantity).build();

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> stock.subtractStock(numberItemsToRemove)).withMessage("Final quantity result can not be bellow zero");
    }
}