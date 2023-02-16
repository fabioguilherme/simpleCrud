package com.portofolio.demo.userInterface.controller.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.demo.aplication.stock.StockApplicationService;
import com.portofolio.demo.aplication.stock.model.CreateStockRequest;
import com.portofolio.demo.aplication.stock.model.StockDto;
import com.portofolio.demo.models.json.stock.CreateStockRequestJson;
import com.portofolio.demo.models.json.stock.Stock;
import com.portofolio.demo.models.json.stock.UpdateStockRequestJson;
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

@WebMvcTest(controllers = StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockApplicationService stockApplicationService;

    @Test
    void canGetStock() throws Exception {
        // Given
        Long stockId = 1L;
        String itemName = "fake-name";
        int quantity = 5;
        String uri = "fake-uri";

        StockDto stockFound = StockDto.Builder.with().id(stockId).itemName(itemName).quantity(quantity).uri(uri).build();

        Stock stockExcepted = new Stock();
        stockExcepted.setId(stockId);
        stockExcepted.setItemName(itemName);
        stockExcepted.setQuantity(quantity);
        stockExcepted.setUri(uri);

        when(stockApplicationService.getById(stockId)).thenReturn(Optional.of(stockFound));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/stock/{id}", stockId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(stockApplicationService).getById(stockId);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(stockExcepted));
    }

    @Test
    void shouldReturnA404IfStockDoNotExists() throws Exception {
        // Given
        Long stockId = 1L;

        when(stockApplicationService.getById(stockId)).thenReturn(Optional.empty());

        // When
        // Then
        this.mockMvc.perform(get("/api/stock/{id}", stockId)).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void canDelete() throws Exception {
        // Given
        Long stockId = 1L;

        doNothing().when(stockApplicationService).deleteById(stockId);

        // When
        MvcResult result = this.mockMvc.perform(delete("/api/stock/{id}", stockId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(stockApplicationService).deleteById(stockId);
    }

    @Test
    void canGetAll() throws Exception {
        // Given
        Long stockId = 1L;
        String itemName = "fake-name";
        int quantity = 5;
        String uri = "fake-uri";

        StockDto stockFound = StockDto.Builder.with().id(stockId).itemName(itemName).quantity(quantity).uri(uri).build();

        Stock stockExcepted = new Stock();
        stockExcepted.setId(stockId);
        stockExcepted.setItemName(itemName);
        stockExcepted.setQuantity(quantity);
        stockExcepted.setUri(uri);

        Long stockId2 = 2L;
        String itemName2 = "fake-name2";
        int quantity2 = 5;
        String uri2 = "fake-uri2";

        StockDto stockFound2 = StockDto.Builder.with().id(stockId2).itemName(itemName2).quantity(quantity2).uri(uri2).build();

        Stock stockExcepted2 = new Stock();
        stockExcepted2.setId(stockId2);
        stockExcepted2.setItemName(itemName2);
        stockExcepted2.setQuantity(quantity2);
        stockExcepted2.setUri(uri2);

        List<Stock> listExcepted = Lists.list(stockExcepted, stockExcepted2);

        when(stockApplicationService.getAll()).thenReturn(Lists.list(stockFound, stockFound2));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/stock", stockId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(stockApplicationService).getAll();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(listExcepted));
    }

    @Test
    public void canDOAPost201() throws Exception {
        // Given
        Long stockId = 1L;
        Long itemId = 1L;
        String itemName = "fake-name";
        int quantity = 5;
        String uri = "fake-uri";

        StockDto stockPersisted = StockDto.Builder.with().id(stockId).itemName(itemName).quantity(quantity).uri(uri).build();

        Stock stockExcepted = new Stock();

        stockExcepted.setId(stockId);
        stockExcepted.setItemName(itemName);
        stockExcepted.setQuantity(quantity);
        stockExcepted.setUri(uri);

        CreateStockRequestJson requestJson = new CreateStockRequestJson();
        requestJson.setItemId(itemId);
        requestJson.setQuantity(quantity);


        when(stockApplicationService.save(any())).thenReturn(stockPersisted);

        // When
        MvcResult result = this.mockMvc.perform(post("/api/stock")
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        // Then
        ArgumentCaptor<CreateStockRequest> requestCaptor = ArgumentCaptor.forClass(CreateStockRequest.class);

        Mockito.verify(stockApplicationService).save(requestCaptor.capture());

        CreateStockRequest request = requestCaptor.getValue();

        assertThat(request).isNotNull();
        assertThat(request.getItemId()).isEqualTo(itemId);
        assertThat(request.getQuantity()).isEqualTo(quantity);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(stockExcepted));
    }

    @Test
    public void canDOAPatch200Add() throws Exception {
        // Given
        Long stockId = 1L;
        int newQuantity = 5;

        UpdateStockRequestJson requestJson = new UpdateStockRequestJson();
        requestJson.setQuantity(newQuantity);

        doNothing().when(stockApplicationService).addStockToItem(stockId, newQuantity);

        // When
        this.mockMvc.perform(patch("/api/stock/" + stockId + "/add")
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        // Then
        Mockito.verify(stockApplicationService).addStockToItem(stockId, newQuantity);
    }

    @Test
    public void canDOAPatch200subtract() throws Exception {
        // Given
        Long stockId = 1L;
        int newQuantity = 5;

        UpdateStockRequestJson requestJson = new UpdateStockRequestJson();
        requestJson.setQuantity(newQuantity);

        doNothing().when(stockApplicationService).addStockToItem(stockId, newQuantity);

        // When
        this.mockMvc.perform(patch("/api/stock/" + stockId + "/subtract")
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        // Then
        Mockito.verify(stockApplicationService).subtractStockToItem(stockId, newQuantity);
    }
}