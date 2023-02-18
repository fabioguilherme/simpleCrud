package com.portofolio.demo.userInterface.controller.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.demo.aplication.notification.NotificationApplicationService;
import com.portofolio.demo.aplication.notification.model.CreateNotificationRequest;
import com.portofolio.demo.aplication.notification.model.NotificationDto;
import com.portofolio.demo.models.json.notification.CreateNotificationRequestJson;
import com.portofolio.demo.models.json.notification.Notification;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationApplicationService notificationApplicationService;

    @Test
    void canGetNotification() throws Exception {
        // Given
        Long id = 1L;
        String message = "fake-message";
        String userName = "fake-name";
        String userEmail = "fake-email@fake.com";
        String uri = "http:/localhostfake:8080/api/notification/1";
        LocalDateTime creationDate = LocalDateTime.now();

        NotificationDto notificationFound = NotificationDto.Builder.with()
                .id(id)
                .message(message)
                .userName(userName)
                .userEmail(userEmail)
                .uri(uri)
                .creationDate(creationDate)
                .build();

        Notification notificationExpected = new Notification();

        notificationExpected.setId(id);
        notificationExpected.setMessage(message);
        notificationExpected.setUserName(userName);
        notificationExpected.setUserEmail(userEmail);
        notificationExpected.setUri(uri);
        notificationExpected.setCreationDate(creationDate);

        when(notificationApplicationService.getById(id)).thenReturn(Optional.of(notificationFound));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/notification/{id}", id)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(notificationApplicationService).getById(id);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(notificationExpected));
    }

    @Test
    void shouldReturnA404IfNotificationDoNotExists() throws Exception {
        // Given
        Long notificationId = 1L;

        when(notificationApplicationService.getById(notificationId)).thenReturn(Optional.empty());

        // When
        // Then
        this.mockMvc.perform(get("/api/notification/{id}", notificationId)).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void canDelete() throws Exception {
        // Given
        Long notificationId = 1L;

        doNothing().when(notificationApplicationService).deleteById(notificationId);

        // When
        MvcResult result = this.mockMvc.perform(delete("/api/notification/{id}", notificationId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(notificationApplicationService).deleteById(notificationId);
    }

    @Test
    void canGetAll() throws Exception {
        // Given
        Long id = 1L;
        String message = "fake-message";
        String userName = "fake-name";
        String userEmail = "fake-email@fake.com";
        String uri = "http:/localhostfake:8080/api/notification/1";
        LocalDateTime creationDate = LocalDateTime.now();

        NotificationDto notificationFound = NotificationDto.Builder.with()
                .id(id)
                .message(message)
                .userName(userName)
                .userEmail(userEmail)
                .uri(uri)
                .creationDate(creationDate)
                .build();

        Notification notificationExcepted = new Notification();

        notificationExcepted.setId(id);
        notificationExcepted.setMessage(message);
        notificationExcepted.setUserName(userName);
        notificationExcepted.setUserEmail(userEmail);
        notificationExcepted.setUri(uri);
        notificationExcepted.setCreationDate(creationDate);

        Long id2 = 1L;
        String message2 = "fake-message";
        String userName2 = "fake-name";
        String userEmail2 = "fake-email@fake.com";
        String uri2 = "http:/localhostfake:8080/api/notification/2";
        LocalDateTime creationDate2 = LocalDateTime.now();

        NotificationDto notificationFound2 = NotificationDto.Builder.with()
                .id(id2)
                .message(message2)
                .userName(userName2)
                .userEmail(userEmail2)
                .uri(uri2)
                .creationDate(creationDate2)
                .build();

        Notification notificationExcepted2 = new Notification();

        notificationExcepted2.setId(id2);
        notificationExcepted2.setMessage(message2);
        notificationExcepted2.setUserName(userName2);
        notificationExcepted2.setUserEmail(userEmail2);
        notificationExcepted2.setUri(uri2);
        notificationExcepted2.setCreationDate(creationDate2);


        List<Notification> listExcepted = Lists.list(notificationExcepted, notificationExcepted2);

        when(notificationApplicationService.getAll(any())).thenReturn(Lists.list(notificationFound, notificationFound2));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/notification")).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(notificationApplicationService).getAll(any());

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(listExcepted));
    }

    @Test
    void canGetNotificationByUserId() throws Exception {
        // Given
        Long userId = 1L;
        Long id = 1L;
        String message = "fake-message";
        String userName = "fake-name";
        String userEmail = "fake-email@fake.com";
        String uri = "http:/localhostfake:8080/api/notification/1";
        LocalDateTime creationDate = LocalDateTime.now();

        NotificationDto notificationFound = NotificationDto.Builder.with()
                .id(id)
                .message(message)
                .userName(userName)
                .userEmail(userEmail)
                .uri(uri)
                .creationDate(creationDate)
                .build();

        Notification notificationExcepted = new Notification();

        notificationExcepted.setId(id);
        notificationExcepted.setMessage(message);
        notificationExcepted.setUserName(userName);
        notificationExcepted.setUserEmail(userEmail);
        notificationExcepted.setUri(uri);
        notificationExcepted.setCreationDate(creationDate);

        Long id2 = 1L;
        String message2 = "fake-message";
        String userName2 = "fake-name";
        String userEmail2 = "fake-email@fake.com";
        String uri2 = "http:/localhostfake:8080/api/notification/2";
        LocalDateTime creationDate2 = LocalDateTime.now();

        NotificationDto notificationFound2 = NotificationDto.Builder.with()
                .id(id2)
                .message(message2)
                .userName(userName2)
                .userEmail(userEmail2)
                .uri(uri2)
                .creationDate(creationDate2)
                .build();

        Notification notificationExcepted2 = new Notification();

        notificationExcepted2.setId(id2);
        notificationExcepted2.setMessage(message2);
        notificationExcepted2.setUserName(userName2);
        notificationExcepted2.setUserEmail(userEmail2);
        notificationExcepted2.setUri(uri2);
        notificationExcepted2.setCreationDate(creationDate2);


        List<Notification> listExcepted = Lists.list(notificationExcepted, notificationExcepted2);

        when(notificationApplicationService.getAll(userId)).thenReturn(Lists.list(notificationFound, notificationFound2));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/notification").param("userId", "1")).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(notificationApplicationService).getAll(userId);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(listExcepted));
    }

    @Test
    void shouldReturnA400IfUserIdIsInvalid() throws Exception {
        // Given

        // When
        MvcResult result = this.mockMvc.perform(get("/api/notification").param("userId", "abc")).andExpect(status().is4xxClientError()).andReturn();

    }

    @Test
    public void canDOAPost201() throws Exception {
        // Given
        Long id = 1L;
        Long userId = 1L;
        String message = "fake-message";
        String userName = "fake-name";
        String userEmail = "fake-email@fake.com";
        String uri = "http:/localhostfake:8080/api/notification/1";
        LocalDateTime creationDate = LocalDateTime.now();

        NotificationDto notificationPersisted = NotificationDto.Builder.with()
                .id(id)
                .message(message)
                .userName(userName)
                .userEmail(userEmail)
                .uri(uri)
                .creationDate(creationDate)
                .build();

        Notification jsonExpected = new Notification();

        jsonExpected.setId(id);
        jsonExpected.setMessage(message);
        jsonExpected.setUserName(userName);
        jsonExpected.setUserEmail(userEmail);
        jsonExpected.setUri(uri);
        jsonExpected.setCreationDate(creationDate);

        CreateNotificationRequestJson requestJson = new CreateNotificationRequestJson();
        requestJson.setUserId(userId);
        requestJson.setMessage(message);


        when(notificationApplicationService.save(any())).thenReturn(notificationPersisted);

        // When
        MvcResult result = this.mockMvc.perform(post("/api/notification")
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        // Then
        ArgumentCaptor<CreateNotificationRequest> requestCaptor = ArgumentCaptor.forClass(CreateNotificationRequest.class);

        Mockito.verify(notificationApplicationService).save(requestCaptor.capture());

        CreateNotificationRequest request = requestCaptor.getValue();

        assertThat(request).isNotNull();
        assertThat(request.getUserId()).isEqualTo(userId);
        assertThat(request.getMessage()).isEqualTo(message);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(jsonExpected));
    }
}