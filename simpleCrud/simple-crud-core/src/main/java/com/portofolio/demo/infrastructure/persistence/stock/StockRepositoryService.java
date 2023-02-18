package com.portofolio.demo.infrastructure.persistence.stock;


import com.portofolio.demo.domain.stock.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepositoryService {

    Stock save(Stock stock);

    void deleteById(Long id);

    Optional<Stock> getById(Long id);

    List<Stock> getAll(Long itemId);
}
