package com.portofolio.demo.infrastructure.user;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void canPersist() {
        // Given
        String name = "fakeName";
        String email = "fakeName@email.com";
        User userToStore = User.Builder.with().name(name).email(email).build();

        // When
        User stored = repository.save(userToStore);

        // Then
        assertThat(stored).isNotNull();

        assertThat(stored.getName()).isEqualTo(name);
        assertThat(stored.getEmail()).isEqualTo(email);
    }

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\n" +
            "\tVALUES (1, \"fake-name\",\"fake-name@email.com\");"})
    public void canFindById() {
        // Given
        long id = 1L;

        // When
        Optional<User> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\n" +
            "\tVALUES (1, \"fake-name\",\"fake-name@email.com\");"})
    public void canDeleteById() {
        // Given
        long id = 1L;

        // When
        repository.deleteById(id);

        // Then
        Optional<User> storedOptional = repository.findById(id);
        assertThat(storedOptional).isEmpty();
    }
}