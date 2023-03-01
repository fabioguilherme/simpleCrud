package com.portofolio.demo.infrastructure.persistence.stock;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.domain.stock.StockFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StockRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private ItemRepository repositoryItem;
    @Autowired
    private StockRepository repository;

    @Test
    public void canPersist() throws Exception {
        // Given
        Item item = repositoryItem.save(ItemFixture.getItem());
        int quantity = 3;

        // When
        Stock stockStored = repository.save(StockFixture.getStockWithItem(item, quantity));

        // Then
        assertThat(stockStored).isNotNull();

        assertThat(stockStored.getItem().getId()).isEqualTo(item.getId());
        assertThat(stockStored.getQuantity()).isEqualTo(quantity);
        assertThat(stockStored.getCreationDate()).isNotNull();
    }

    @Test
    public void canFindById() throws Exception {
        // Given
        Item item = repositoryItem.save(ItemFixture.getItem());
        Stock stock = repository.save(StockFixture.getStockWithItem(item, 3));
        long id = stock.getId();

        // When
        Optional<Stock> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    public void canFindByItemId() throws Exception {
        // Given
        Item item = repositoryItem.save(ItemFixture.getItem());
        repository.save(StockFixture.getStockWithItem(item, 3));
        long id = item.getId();

        // When
        Optional<Stock> storedOptional = repository.findStockByItemId(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    public void canDeleteById() throws Exception {
        // Given
        Item item = repositoryItem.save(ItemFixture.getItem());
        Stock stock = repository.save(StockFixture.getStockWithItem(item, 3));
        long id = stock.getId();

        // When
        repository.deleteById(id);

        // Then
        Optional<Stock> storedOptional = repository.findById(id);
        assertThat(storedOptional).isEmpty();
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
        repositoryItem.deleteAll();
    }
}