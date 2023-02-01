package com.portofolio.demo.persistence;

import com.portofolio.demo.domain.Item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:test/application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemRepositoryIntegrationTest {

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
}