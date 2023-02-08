package com.portofolio.demo.infrastructure.persistence.item;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ItemRepositoryServiceImplIntegrationTest extends IntegrationBaseTest {


    @Autowired
    private ItemRepository repository;

    @Autowired
    private ItemRepositoryService service;


    @Test
    public void canCreate() throws Exception {
        // Given
        Item itemToPersist = ItemFixture.getItem();

        // When
        Item itemPersisted = service.save(itemToPersist);

        // Then
        Optional<Item> itemFoundOptional = repository.findById(itemPersisted.getId());

        assertThat(itemFoundOptional).isPresent();

        Item item = itemFoundOptional.get();

        assertThat(item.getName()).isEqualTo(itemToPersist.getName());
    }

    @Test
    public void shouldThrowAnExceptionIfItemIsNullWhenCreating() throws Exception {
        // Given
        Item itemToPersist = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.save(itemToPersist)).withMessage("Item can not be null");
    }

    @Test
    public void canDeleteById() throws Exception {
        // Given
        Item itemToPersist = ItemFixture.getItem();

        // When
        Item itemPersisted = service.save(itemToPersist);

        // Then
        service.deleteById(itemPersisted.getId());
        Optional<Item> itemFoundOptional = repository.findById(itemPersisted.getId());

        assertThat(itemFoundOptional).isEmpty();
    }

    @Test
    public void shouldThrowAnExceptionIfIdIsNullWhenDeleting() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.deleteById(id)).withMessage("Id can not be null");
    }

    @Test
    public void shouldThrowResourceNotFoundWhenItemNotExists() {
        // Given
        Long id = 5L;

        // When
        // Then
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> service.deleteById(id)).withMessage("Item not found with id: " + id);
    }

    @Test
    public void canGetById() throws Exception {
        // Given
        Item itemToPersist = ItemFixture.getItem();
        Item itemPersisted = service.save(itemToPersist);
        Long id = itemPersisted.getId();

        // When
        Optional<Item> itemFoundOptional = service.getById(itemPersisted.getId());

        // Then
        assertThat(itemFoundOptional).isPresent();
    }

    @Test
    public void shouldThrowAnExceptionIfIdIsNullWhenGettingById() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.getById(id)).withMessage("Id can not be null");
    }

    @Test
    public void canGetAll() throws NoSuchFieldException {
        // Given
        Item itemToPersist = ItemFixture.getItem();
        Item itemToPersist2 = ItemFixture.getItemWithName("FAKE-name-2");
        service.save(itemToPersist);
        service.save(itemToPersist2);


        // When
        List<Item> list = service.getAll();

        // Then
        assertThat(list).hasSize(2);

        assertThat(list).anyMatch(i -> i.getName().equals(itemToPersist.getName()));
        assertThat(list).anyMatch(i -> i.getName().equals(itemToPersist2.getName()));
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
    }
}