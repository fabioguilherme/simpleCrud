package com.portofolio.demo.domain.stock;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.Item.Item;
import com.portofolio.demo.infrastructure.item.ItemRepository;
import com.portofolio.demo.infrastructure.stock.StockRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StockRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private ItemRepository repositoryItem;
    @Autowired
    private StockRepository repository;

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.item (id, name)\n" +
            "\tVALUES (1, \"fake\");"})
    public void canPersist() {
        // Given
        Item item = repositoryItem.findById(1L).get();
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
    @Sql(statements = {"INSERT INTO simple_crud_test.item (id, name)\tVALUES (1, \"fake\");" +
            "INSERT INTO stock(id, item_id, quantity, creation_date) values (1,1,3,CURRENT_TIMESTAMP);"})
    public void canFindById() {
        // Given
        long id = 1L;

        // When
        Optional<Stock> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    @Sql(statements = {
            "INSERT INTO simple_crud_test.item (id, name)\tVALUES (1, \"fake\");" +
                    "INSERT INTO stock(id, item_id, quantity, creation_date) values (1,1,3,CURRENT_TIMESTAMP);"})
    public void canDeleteById() {
        // Given
        long id = 1L;

        // When
        repository.deleteById(id);

        // Then
        Optional<Stock> storedOptional = repository.findById(id);
        assertThat(storedOptional).isEmpty();
    }
}