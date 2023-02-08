package com.portofolio.demo.infrastructure.persistence.item;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class ItemRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private ItemRepository repository;


    @Test
    public void canPersist() throws Exception {
        // Given
        Item entity = ItemFixture.getItem();

        // When
        Item stored = repository.save(entity);

        // Then
        assertThat(stored).isNotNull();
        assertThat(stored.getName()).isEqualTo(entity.getName());
    }

    @Test
    public void canFindById() throws Exception {
        // Given
        Item stored = repository.save(ItemFixture.getItem());
        Long id = stored.getId();

        // When
        Optional<Item> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    public void canDeleteById() throws Exception {
        // Given
        Item stored = repository.save(ItemFixture.getItem());
        Long id = stored.getId();

        // When
        repository.deleteById(id);

        // Then
        Optional<Item> storedOptional = repository.findById(id);
        assertThat(storedOptional).isEmpty();
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
    }
}