package com.portofolio.demo.infrastructure.persistence.stock;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.domain.stock.StockDomainService;
import com.portofolio.demo.domain.stock.StockFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StockRepositoryServiceImplIntegrationTest extends IntegrationBaseTest {


    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockRepositoryService service;

    @Autowired
    private StockDomainService domainService;


    @Test
    void canCreate() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        int quantity = 5;
        com.portofolio.demo.domain.stock.Stock stockToPersist = StockFixture.getStockWithItem(item, quantity);

        // When
        com.portofolio.demo.domain.stock.Stock stockPersisted = service.save(stockToPersist);

        // Then
        Optional<com.portofolio.demo.domain.stock.Stock> stockFoundOptional = repository.findById(stockPersisted.getId());

        assertThat(stockFoundOptional).isPresent();

        com.portofolio.demo.domain.stock.Stock stock = stockFoundOptional.get();

        assertThat(stock.getItem()).isNotNull();

        assertThat(stock.getQuantity()).isEqualTo(quantity);
    }

    @Test
    void shouldThrowAnExceptionIfItemIsNullWhenCreating() {
        // Given
        Stock stockToPersist = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.save(stockToPersist)).withMessage("Stock can not be null");
    }

    @Test
    void canUpdate() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        int originalQuantity = 5;
        int plusQuantity = 1;
        Stock stockToPersisted = StockFixture.getStockWithItem(item, originalQuantity);

        // When
        domainService.addStock(stockToPersisted, plusQuantity);
        Stock stockUpdated = service.save(stockToPersisted);

        // Then
        assertThat(stockUpdated).isNotNull();

        assertThat(stockUpdated.getItem()).isNotNull();

        assertThat(stockUpdated.getQuantity()).isEqualTo(originalQuantity + plusQuantity);
    }

    @Test
    void canDeleteById() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        Stock stockToPersisted = repository.save(StockFixture.getStockWithItem(item, 5));
        Long id = stockToPersisted.getId();

        // When
        service.deleteById(id);

        // Then
        Optional<Stock> notificationFoundOptional = repository.findById(id);

        assertThat(notificationFoundOptional).isEmpty();
    }

    @Test
    void shouldThrowAnExceptionIfIdIsNullWhenDeleting() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.deleteById(id)).withMessage("Id can not be null");
    }

    @Test
    void canGetById() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        Stock stockToPersisted = repository.save(StockFixture.getStockWithItem(item, 5));
        Long id = stockToPersisted.getId();

        // When
        Optional<Stock> stockFoundOptional = service.getById(id);

        // Then
        assertThat(stockFoundOptional).isPresent();
    }

    @Test
    void shouldThrowAnExceptionIfIdIsNullWhenGettingById() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.getById(id)).withMessage("Id can not be null");
    }

    @Test
    void canGetAll() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        Stock stockToPersist = StockFixture.getStockWithItem(item, 5);
        Stock stockPersisted = service.save(stockToPersist);

        Item item2 = itemRepository.save(ItemFixture.getItemWithName("item2"));
        Stock stockToPersist2 = StockFixture.getStockWithItem(item2, 6);
        Stock stockPersisted2 = service.save(stockToPersist2);


        // When
        List<Stock> list = service.getAll();

        // Then
        assertThat(list).hasSize(2);

        assertThat(list).anyMatch(s -> s.getItem().getName().equals(stockPersisted.getItem().getName()) && stockPersisted.getQuantity() == s.getQuantity());
        assertThat(list).anyMatch(s -> s.getItem().getName().equals(stockPersisted2.getItem().getName()) && stockPersisted2.getQuantity() == s.getQuantity());
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
        itemRepository.deleteAll();
    }
}