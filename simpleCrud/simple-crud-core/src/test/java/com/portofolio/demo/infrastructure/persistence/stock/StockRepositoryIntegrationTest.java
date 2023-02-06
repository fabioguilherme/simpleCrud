package com.portofolio.demo.infrastructure.persistence.stock;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.stock.Stock;
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
    public void canPersist() {
        // Given
        Item item = repositoryItem.save(ItemFixture.getItem());
        int quantity = 3;
        Stock stockToStore = Stock.Builder.with().item(item).quantity(quantity).build();

        // When
        Stock stored = repository.save(stockToStore);

        // Then
        assertThat(stored).isNotNull();

        assertThat(stored.getItem().getId()).isEqualTo(item.getId());
        assertThat(stored.getQuantity()).isEqualTo(quantity);
        assertThat(stored.getCreationDate()).isNotNull();
    }

    @Test
    public void canFindById() {
        // Given
        Item item = repositoryItem.save(ItemFixture.getItem());
        Stock stock = repository.save(Stock.Builder.with().item(item).quantity(3).build());
        long id = stock.getId();

        // When
        Optional<Stock> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    public void canDeleteById() {
        // Given
        Item item = repositoryItem.save(ItemFixture.getItem());
        Stock stock = repository.save(Stock.Builder.with().item(item).quantity(3).build());
        long id = stock.getId();

        // When
        repository.deleteById(id);

        // Then
        Optional<Stock> storedOptional = repository.findById(id);
        assertThat(storedOptional).isEmpty();
    }

    @Override
    protected void clearDataBase() {
        repository.deleteAll();
        repositoryItem.deleteAll();
    }
}