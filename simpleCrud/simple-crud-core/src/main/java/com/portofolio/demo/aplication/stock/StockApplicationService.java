package com.portofolio.demo.aplication.stock;

import com.portofolio.demo.aplication.stock.model.CreateStockRequest;
import com.portofolio.demo.aplication.stock.model.StockDto;

import java.util.List;
import java.util.Optional;

public interface StockApplicationService {
    StockDto save(CreateStockRequest request);

    void deleteById(Long id);

    void addStockToItem(Long stockId, int quantity);

    void subtractStockToItem(Long stockId, int quantity);

    Optional<StockDto> getById(Long id);

    List<StockDto> getAll();
}
