package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;

public interface StockDomainService {

    Stock createStock(Item item, int quantity);

    void addStock(Stock stock, int quantity);

    void subtractStock(Stock stock, int quantity);
}

