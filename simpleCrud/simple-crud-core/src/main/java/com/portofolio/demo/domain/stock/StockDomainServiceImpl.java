package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.shared.errors.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class StockDomainServiceImpl implements StockDomainService {
    @Override
    public Stock createStock(Item item, int quantity) {
        return Stock.Builder.with().item(item).quantity(quantity).build();
    }

    @Override
    public void addStock(Stock stock, int quantity) {

        if (stock == null) {
            throw new BusinessException("stock can not be null", null);
        }

        stock.addStock(quantity);
    }

    @Override
    public void subtractStock(Stock stock, int quantity) {

        if (stock == null) {
            throw new BusinessException("stock can not be null", null);
        }

        stock.subtractStock(quantity);
    }
}
