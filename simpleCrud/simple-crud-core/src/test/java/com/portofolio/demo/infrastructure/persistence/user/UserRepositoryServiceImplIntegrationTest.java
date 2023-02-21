package com.portofolio.demo.infrastructure.persistence.user;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserDomainService;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UserRepositoryServiceImplIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserRepositoryService service;

    @Autowired
    private UserDomainService domainService;

    @Test
    void save() throws Exception {
        // Given
        User userToPersist = UserFixture.getUser();

        // When
        User UserPersisted = service.save(userToPersist);

        // Then
        Optional<User> UserFoundOptional = repository.findById(UserPersisted.getId());

        assertThat(UserFoundOptional).isPresent();

        User User = UserFoundOptional.get();

        assertThat(User.getName()).isEqualTo(userToPersist.getName());
    }

    @Test
    void canUpdateEmail() throws Exception {
        // Given
        User userToPersist = UserFixture.getUser();
        User userPersisted = service.save(userToPersist);
        String email = "new@email.com";

        // When
        domainService.updateEmail(userPersisted, email);
        User UserPersisted = service.save(userPersisted);

        // Then
        Optional<User> userOptional = repository.findById(UserPersisted.getId());

        assertThat(userOptional).isPresent();

        User user = userOptional.get();

        assertThat(user.getName()).isEqualTo(userToPersist.getName());
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldThrowAnExceptionWhenSavingAEmptyUser() {
        // Given
        User userToPersist = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.save(userToPersist)).withMessage("User can not be null");
    }

    @Test
    void deleteById() throws Exception {
        // Given
        User userToPersist = UserFixture.getUser();
        User userPersisted = service.save(userToPersist);
        Long id = userPersisted.getId();

        // When
        service.deleteById(id);

        // Then
        Optional<User> UserFoundOptional = repository.findById(id);

        assertThat(UserFoundOptional).isEmpty();
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
    public void shouldThrowResourceNotFoundWhenUserNotExists() {
        // Given
        Long id = 5L;

        // When
        // Then
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> service.deleteById(id)).withMessage("User not found with id: " + id);
    }

    @Test
    void getById() throws Exception {
        // Given
        User userToPersist = UserFixture.getUser();
        User userPersisted = service.save(userToPersist);
        Long id = userPersisted.getId();

        // When
        Optional<User> UserFoundOptional = service.getById(userPersisted.getId());

        // Then
        assertThat(UserFoundOptional).isPresent();
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
    void shouldThrowAnExceptionWhenSavingIfThereIsAnUserWithThatEmail() throws Exception {
        // Given
        User userToPersist = UserFixture.getUser();
        service.save(userToPersist);

        User newUser = UserFixture.getNewUseWithEmailAndName(userToPersist.getEmail(), "fakeuser2");

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(newUser)).withMessage("Can not persist user because there is another user with that email");
    }

    @Test
    void shouldThrowAnExceptionWhenUpdatingIfThereIsAnUserWithThatEmail() throws Exception {
        // Given
        User userToPersist = UserFixture.getUser();
        service.save(userToPersist);

        User userToPersist2 = UserFixture.getNewUseWithEmailAndName("fakeemail2@email.com", "fake-user");
        User userToPersisted2 = service.save(userToPersist2);

        String email = userToPersist.getEmail();

        // When
        domainService.updateEmail(userToPersisted2, email);
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(userToPersisted2)).withMessage("Can not change email because that email is already taken");

    }

    @Test
    void getAll() throws Exception {
        // Given
        User UserToPersist = UserFixture.getUser();
        User UserToPersist2 = UserFixture.getUseWithEmailAndName("email2@email.com", "FAKE-name-2");
        service.save(UserToPersist);
        service.save(UserToPersist2);


        // When
        List<User> list = service.getAll();

        // Then
        assertThat(list).hasSize(2);

        assertThat(list).anyMatch(i -> i.getName().equals(UserToPersist.getName()));
        assertThat(list).anyMatch(i -> i.getName().equals(UserToPersist2.getName()));
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
    }
}