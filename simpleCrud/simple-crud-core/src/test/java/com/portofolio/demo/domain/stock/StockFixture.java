package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;

public class StockFixture {

    public static Stock getStock() {

        Item item = ItemFixture.getItem();
        int quantity = 2;


        return Stock.Builder.with().item(item).quantity(quantity).build();
    }

    public static Stock getStockWithItem(Item item, int quantity) {
        return Stock.Builder.with().item(item).quantity(quantity).build();
    }
}
