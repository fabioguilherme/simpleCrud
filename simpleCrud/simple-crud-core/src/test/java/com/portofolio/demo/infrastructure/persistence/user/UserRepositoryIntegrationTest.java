package com.portofolio.demo.infrastructure.persistence.user;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserRepositoryIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private UserRepository repository;

    @Override
    public void beforeTesting() {
        repository.deleteAll();
    }

    @Override
    public void afterTesting() {
        repository.deleteAll();
    }


    @Test
    public void canPersist() {
        // Given
        String name = "fakeName";
        String email = "fakeName@email.com";
        User userToStore = User.Builder.with().name(name).email(email).build();

        // When
        User stored = repository.save(userToStore);

        // Then
        Assertions.assertThat(stored).isNotNull();

        Assertions.assertThat(stored.getName()).isEqualTo(name);
        Assertions.assertThat(stored.getEmail()).isEqualTo(email);
    }

    @Test
    public void canFindById() {
        // Given
        User userPersisted = repository.save(UserFixture.getUser().build());
        Long id = userPersisted.getId();

        // When
        Optional<User> storedOptional = repository.findById(id);

        // Then
        Assertions.assertThat(storedOptional).isPresent();
    }

    @Test
    public void canDeleteById() {
        // Given
        User userPersisted = repository.save(UserFixture.getUser().build());
        Long id = userPersisted.getId();

        // When
        repository.deleteById(id);

        // Then
        Optional<User> storedOptional = repository.findById(id);
        Assertions.assertThat(storedOptional).isEmpty();
    }
}
