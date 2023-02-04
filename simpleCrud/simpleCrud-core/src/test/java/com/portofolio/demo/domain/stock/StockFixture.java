package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;

public class StockFixture {

    public static Stock.Builder getStock() {

        Item item = ItemFixture.getItem();
        int quantity = 2;


        return Stock.Builder.with().item(item).quantity(quantity);
    }
}
