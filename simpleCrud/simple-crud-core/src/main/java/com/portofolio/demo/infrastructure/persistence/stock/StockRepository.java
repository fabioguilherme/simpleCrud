package com.portofolio.demo.infrastructure.persistence.stock;

import com.portofolio.demo.domain.stock.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.item.id = :itemId")
    Optional<Stock> findStockByItemId(@Param("itemId") Long itemId);
}
