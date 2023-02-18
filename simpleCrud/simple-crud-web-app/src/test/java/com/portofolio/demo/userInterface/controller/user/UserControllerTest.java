package com.portofolio.demo.userInterface.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.demo.aplication.user.UserApplicationService;
import com.portofolio.demo.aplication.user.model.CreateUserRequest;
import com.portofolio.demo.aplication.user.model.UserDto;
import com.portofolio.demo.models.json.user.CreateUserRequestJson;
import com.portofolio.demo.models.json.user.UpdateUserEmailRequestJson;
import com.portofolio.demo.models.json.user.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    void canGetUser() throws Exception {
        // Given
        Long userId = 1L;
        String userName = "fake-name";
        String userEmail = "fake-name@email.com";
        String uri = "fake-uri";

        UserDto userFound = UserDto.Builder.with().id(userId).name(userName).email(userEmail).uri(uri).build();

        User userExpected = new User();
        userExpected.setId(userId);
        userExpected.setName(userName);
        userExpected.setEmail(userEmail);
        userExpected.setUri(uri);

        when(userApplicationService.getById(userId)).thenReturn(Optional.of(userFound));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/user/{id}", userId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(userApplicationService).getById(userId);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(userExpected));
    }

    @Test
    void shouldReturnA404IfUserDoNotExists() throws Exception {
        // Given
        Long userId = 1L;

        when(userApplicationService.getById(userId)).thenReturn(Optional.empty());

        // When
        // Then
        this.mockMvc.perform(get("/api/user/{id}", userId)).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void canDelete() throws Exception {
        // Given
        Long userId = 1L;

        doNothing().when(userApplicationService).deleteById(userId);

        // When
        MvcResult result = this.mockMvc.perform(delete("/api/user/{id}", userId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(userApplicationService).deleteById(userId);
    }

    @Test
    void canGetAll() throws Exception {
        // Given
        Long userId = 1L;
        String userName = "fake-name";

        UserDto userFound = UserDto.Builder.with().id(userId).name(userName).build();
        User userExcepted = new User();
        userExcepted.setId(userId);
        userExcepted.setName(userName);

        Long userId2 = 2L;
        String userName2 = "fake-222";

        UserDto userFound2 = UserDto.Builder.with().id(userId2).name(userName2).build();
        User userExcepted2 = new User();
        userExcepted2.setId(userId2);
        userExcepted2.setName(userName2);
        List<User> listExcepted = Lists.list(userExcepted, userExcepted2);

        when(userApplicationService.getAll()).thenReturn(Lists.list(userFound, userFound2));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/user")).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(userApplicationService).getAll();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(listExcepted));
    }

    @Test
    public void canDOAPost201() throws Exception {
        // Given
        Long userId = 1L;
        String userName = "fake-name";
        String userEmail = "fake-name@email.com";
        String uri = "fake-uri";

        UserDto userPersisted = UserDto.Builder.with().id(userId).name(userName).email(userEmail).uri(uri).build();

        User userExcepted = new User();
        userExcepted.setId(userId);
        userExcepted.setName(userName);
        userExcepted.setUri(uri);
        userExcepted.setEmail(userEmail);

        CreateUserRequestJson requestJson = new CreateUserRequestJson();
        requestJson.setName(userName);
        requestJson.setEmail(userEmail);


        when(userApplicationService.save(any())).thenReturn(userPersisted);

        // When
        MvcResult result = this.mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        // Then
        ArgumentCaptor<CreateUserRequest> requestCaptor = ArgumentCaptor.forClass(CreateUserRequest.class);

        Mockito.verify(userApplicationService).save(requestCaptor.capture());

        CreateUserRequest request = requestCaptor.getValue();

        assertThat(request).isNotNull();
        assertThat(request.getName()).isEqualTo(userName);
        assertThat(request.getEmail()).isEqualTo(userEmail);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(userExcepted));
    }

    @Test
    public void canDOAPatch201() throws Exception {
        // Given
        Long userId = 1L;
        String userName = "fake-name";
        String userEmail = "new-fake-name@email.com";
        String uri = "fake-uri";

        UserDto userPersisted = UserDto.Builder.with().id(userId).name(userName).email(userEmail).uri(uri).build();

        User userExcepted = new User();

        userExcepted.setId(userId);
        userExcepted.setName(userName);
        userExcepted.setUri(uri);
        userExcepted.setEmail(userEmail);

        UpdateUserEmailRequestJson requestJson = new UpdateUserEmailRequestJson();
        requestJson.setEmail(userEmail);


        when(userApplicationService.updateUserEmail(userId, userEmail)).thenReturn(userPersisted);

        // When
        MvcResult result = this.mockMvc.perform(patch("/api/user/" + userId)
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        // Then
        Mockito.verify(userApplicationService).updateUserEmail(userId, userEmail);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(userExcepted));
    }

}