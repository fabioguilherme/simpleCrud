package com.portofolio.demo.userInterface.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.demo.aplication.order.OrderApplicationService;
import com.portofolio.demo.aplication.order.model.CreateOrderRequest;
import com.portofolio.demo.aplication.order.model.OrderDto;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.models.json.order.ChangeOrderStatusRequestJson;
import com.portofolio.demo.models.json.order.CreateOrderRequestJson;
import com.portofolio.demo.models.json.order.Order;
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

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderApplicationService orderApplicationService;

    @Test
    void canGetOrder() throws Exception {
        // Given
        Long orderId = 1L;
        String itemName = "fake-name";
        String userName = "user-fake-name";
        String userEmail = "fake-name@fake.com";
        LocalDateTime creationDate = LocalDateTime.now();
        int quantity = 5;
        String uri = "fake-uri";

        OrderDto orderFound = OrderDto.Builder.with()
                .id(orderId)
                .itemName(itemName)
                .userName(userName)
                .userEmail(userEmail)
                .creationDate(creationDate)
                .quantity(quantity)
                .uri(uri).build();

        Order orderExcepted = new Order();

        orderExcepted.setId(orderId);
        orderExcepted.setItemName(itemName);
        orderExcepted.setUserName(userName);
        orderExcepted.setUserEmail(userEmail);
        orderExcepted.setCreationDate(creationDate);
        orderExcepted.setQuantity(quantity);
        orderExcepted.setUri(uri);

        when(orderApplicationService.getById(orderId)).thenReturn(Optional.of(orderFound));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/order/{id}", orderId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(orderApplicationService).getById(orderId);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(orderExcepted));
    }

    @Test
    void shouldReturnA404IfOrderDoNotExists() throws Exception {
        // Given
        Long orderId = 1L;

        when(orderApplicationService.getById(orderId)).thenReturn(Optional.empty());

        // When
        // Then
        this.mockMvc.perform(get("/api/order/{id}", orderId)).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void canDelete() throws Exception {
        // Given
        Long orderId = 1L;

        doNothing().when(orderApplicationService).deleteById(orderId);

        // When
        MvcResult result = this.mockMvc.perform(delete("/api/order/{id}", orderId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(orderApplicationService).deleteById(orderId);
    }

    @Test
    void canGetAll() throws Exception {
        // Given
        Long orderId = 1L;
        String itemName = "fake-name";
        String userName = "user-fake-name";
        String userEmail = "fake-name@fake.com";
        LocalDateTime creationDate = LocalDateTime.now();
        int quantity = 5;
        String uri = "fake-uri";

        OrderDto orderFound = OrderDto.Builder.with()
                .id(orderId)
                .itemName(itemName)
                .userName(userName)
                .userEmail(userEmail)
                .creationDate(creationDate)
                .quantity(quantity)
                .uri(uri).build();

        Order orderExcepted = new Order();

        orderExcepted.setId(orderId);
        orderExcepted.setItemName(itemName);
        orderExcepted.setUserName(userName);
        orderExcepted.setUserEmail(userEmail);
        orderExcepted.setCreationDate(creationDate);
        orderExcepted.setQuantity(quantity);
        orderExcepted.setUri(uri);

        Long orderId2 = 2L;
        String itemName2 = "fake-name2";
        String userName2 = "user-fake-name2";
        String userEmail2 = "fake-name2@fake.com";
        LocalDateTime creationDate2 = LocalDateTime.now();
        int quantity2 = 5;
        String uri2 = "fake-uri2";

        OrderDto orderFound2 = OrderDto.Builder.with()
                .id(orderId2)
                .itemName(itemName2)
                .userName(userName2)
                .userEmail(userEmail2)
                .creationDate(creationDate2)
                .quantity(quantity2)
                .uri(uri2).build();

        Order orderExcepted2 = new Order();

        orderExcepted2.setId(orderId2);
        orderExcepted2.setItemName(itemName2);
        orderExcepted2.setUserName(userName2);
        orderExcepted2.setUserEmail(userEmail2);
        orderExcepted2.setCreationDate(creationDate2);
        orderExcepted2.setQuantity(quantity2);
        orderExcepted2.setUri(uri2);

        List<Order> listExcepted = Lists.list(orderExcepted, orderExcepted2);

        when(orderApplicationService.getAll()).thenReturn(Lists.list(orderFound, orderFound2));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/order", orderId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(orderApplicationService).getAll();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(listExcepted));
    }

    @Test
    public void canDOAPost201() throws Exception {
        // Given
        Long userId = 1L;
        Long itemId = 1L;

        Long orderId = 1L;
        String itemName = "fake-name";
        String userName = "user-fake-name";
        String userEmail = "fake-name@fake.com";
        LocalDateTime creationDate = LocalDateTime.now();
        int quantity = 5;
        String uri = "fake-uri";

        OrderDto orderPersisted = OrderDto.Builder.with()
                .id(orderId)
                .itemName(itemName)
                .userName(userName)
                .userEmail(userEmail)
                .creationDate(creationDate)
                .quantity(quantity)
                .uri(uri).build();

        Order orderExcepted = new Order();

        orderExcepted.setId(orderId);
        orderExcepted.setItemName(itemName);
        orderExcepted.setUserName(userName);
        orderExcepted.setUserEmail(userEmail);
        orderExcepted.setCreationDate(creationDate);
        orderExcepted.setQuantity(quantity);
        orderExcepted.setUri(uri);

        CreateOrderRequestJson requestJson = new CreateOrderRequestJson();
        requestJson.setUserId(userId);
        requestJson.setItemId(itemId);
        requestJson.setQuantity(quantity);


        when(orderApplicationService.save(any())).thenReturn(orderPersisted);

        // When
        MvcResult result = this.mockMvc.perform(post("/api/order")
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        // Then
        ArgumentCaptor<CreateOrderRequest> requestCaptor = ArgumentCaptor.forClass(CreateOrderRequest.class);

        Mockito.verify(orderApplicationService).save(requestCaptor.capture());

        CreateOrderRequest request = requestCaptor.getValue();

        assertThat(request).isNotNull();
        assertThat(request.getItemId()).isEqualTo(itemId);
        assertThat(request.getUserId()).isEqualTo(userId);
        assertThat(request.getQuantity()).isEqualTo(quantity);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(orderExcepted));
    }

    @Test
    public void canDOAPatch200Add() throws Exception {
        // Given
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.DONE;

        ChangeOrderStatusRequestJson requestJson = new ChangeOrderStatusRequestJson();
        requestJson.setStatus(newStatus.name());

        doNothing().when(orderApplicationService).changeStatus(orderId, newStatus);

        // When
        this.mockMvc.perform(patch("/api/order/" + orderId)
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        // Then
        Mockito.verify(orderApplicationService).changeStatus(orderId, newStatus);
    }
}