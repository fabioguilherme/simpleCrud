package com.portofolio.demo.aplication.user;

import com.portofolio.demo.aplication.user.model.CreateUserRequest;
import com.portofolio.demo.aplication.user.model.UserDto;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserDomainService;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.infrastructure.persistence.user.UserRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserApplicationServiceImplTest {

    private final String URI_BASE = "fake-uribase";
    private final String USER_URI_BASE = "/user/";

    @Mock
    private UserDomainService userDomainService;
    @Mock
    private UserRepositoryService userRepositoryService;

    private UserApplicationServiceImpl applicationService;

    @BeforeEach
    public void setUp() throws Exception {
        this.applicationService = new UserApplicationServiceImpl(userRepositoryService, userDomainService, URI_BASE);
    }

    @Test
    public void canSaveAnUser() throws Exception {
        // Given
        String name = "fake-name";
        String email = "fake-name@email.com";
        CreateUserRequest request = CreateUserRequest.Builder.with().name(name).email(email).build();
        User userPersisted = UserFixture.getUseWithEmailAndName(email, name);

        Mockito.when(userDomainService.createUser(name, email)).thenReturn(userPersisted);
        Mockito.when(userRepositoryService.save(any())).thenReturn(userPersisted);

        // When
        UserDto response = applicationService.save(request);

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getUri()).isEqualTo(URI_BASE + USER_URI_BASE + userPersisted.getId());

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        Mockito.verify(userDomainService).createUser(name, email);
        Mockito.verify(userRepositoryService).save(argumentCaptor.capture());

        User userCaptured = argumentCaptor.getValue();

        assertThat(userCaptured).isNotNull();

        assertThat(userCaptured.getName()).isEqualTo(name);
    }

    @Test
    public void canUpdateUserEmail() throws Exception {
        // Given
        String name = "fake-name";
        String email = "old-name@email.com";
        Long userId = 1L;
        String newEmail = "new-name@email.com";
        User userPersisted = UserFixture.getUseWithEmailAndName(newEmail, name);

        Mockito.when(userRepositoryService.save(any())).thenReturn(userPersisted);

        // When
        UserDto response = applicationService.updateUserEmail(userId, newEmail);

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getUri()).isEqualTo(URI_BASE + USER_URI_BASE + userPersisted.getId());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(userDomainService).updateEmail(any(), argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(newEmail);

        Mockito.verify(userRepositoryService).save(any());
        Mockito.verify(userRepositoryService).getById(userId);
    }

    @Test
    public void canDeleteById() {
        // Given
        Long id = 1L;

        // When
        applicationService.deleteById(id);

        // Then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(userRepositoryService).deleteById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void canGetUserById() throws Exception {
        // Given
        Long id = 1L;
        User userFound = UserFixture.getUser();

        Mockito.when(userRepositoryService.getById(id)).thenReturn(Optional.of(userFound));

        // When
        Optional<UserDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isPresent();

        UserDto dto = response.get();

        assertThat(dto.getName()).isEqualTo(userFound.getName());
        assertThat(dto.getEmail()).isEqualTo(userFound.getEmail());
        assertThat(dto.getId()).isEqualTo(userFound.getId());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + USER_URI_BASE + userFound.getId());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(userRepositoryService).getById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void shouldReturnOptionalEmptyIfUserDoNotExists() throws Exception {
        // Given
        Long id = 1L;

        Mockito.when(userRepositoryService.getById(id)).thenReturn(Optional.empty());

        // When
        Optional<UserDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isEmpty();
    }

    @Test
    public void canGetAll() throws Exception {
        // Given
        User userFound = UserFixture.getUser();

        Mockito.when(userRepositoryService.getAll()).thenReturn(Collections.singletonList(userFound));

        // When
        List<UserDto> response = applicationService.getAll();

        // Then
        assertThat(response).hasSize(1);

        UserDto dto = response.get(0);

        assertThat(dto.getName()).isEqualTo(userFound.getName());
        assertThat(dto.getEmail()).isEqualTo(userFound.getEmail());
        assertThat(dto.getId()).isEqualTo(userFound.getId());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + USER_URI_BASE + userFound.getId());
    }
}