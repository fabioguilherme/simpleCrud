package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.Item.Item;

public class StockDomainServiceImpl implements StockDomainService {
    @Override
    public Stock createStock(Item item, int quantity) {
        return Stock.Builder.with().item(item).quantity(quantity).build();
    }

    @Override
    public void addStock(Stock stock, int quantity) {
        stock.addStock(quantity);
    }

    @Override
    public void subtractStock(Stock stock, int quantity) {
        stock.subtractStock(quantity);
    }
}
