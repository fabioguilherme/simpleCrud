package com.portofolio.demo.infrastructure.persistence.stock;

import com.portofolio.demo.domain.stock.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
}
