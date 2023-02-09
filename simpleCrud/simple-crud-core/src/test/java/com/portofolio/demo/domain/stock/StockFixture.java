package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class StockFixture {

    public static Stock getStock() throws Exception {

        Item item = ItemFixture.getItem();
        int quantity = 2;

        Stock stock = Stock.Builder.with().item(item).quantity(quantity).build();

        Field field = stock.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, stock, 1L);

        return stock;
    }

    public static Stock getStockWithItem(Item item, int quantity) throws Exception {
        Stock stock = Stock.Builder.with().item(item).quantity(quantity).build();

        Field field = stock.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, stock, 1L);

        return stock;
    }

    public static Stock getNewStockWithItem(Item item, int quantity) {
        return Stock.Builder.with().item(item).quantity(quantity).build();
    }
}
