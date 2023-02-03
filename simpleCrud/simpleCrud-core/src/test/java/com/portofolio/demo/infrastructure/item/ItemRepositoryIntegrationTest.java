package com.portofolio.demo.infrastructure.item;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.Item.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class ItemRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private ItemRepository repository;

    @Test
    public void canPersist() {
        // Given
        String name = "FAKE-name";
        Item entity = Item.withName(name);

        // When
        Item stored = repository.save(entity);

        // Then
        assertThat(stored).isNotNull();
        assertThat(stored.getName()).isEqualTo(name);
    }

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.item (id, name)\n" +
            "\tVALUES (1, \"fake\");"})
    public void canFindById() {
        // Given

        // When
        long id = 1L;
        Optional<Item> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.item (id, name)\n" +
            "\tVALUES (1, \"fake\");"})
    public void canDeleteById() {
        // Given

        // When
        long id = 1L;
        repository.deleteById(id);

        // Then
        Optional<Item> storedOptional = repository.findById(id);
        assertThat(storedOptional).isEmpty();
    }
}