package com.portofolio.demo.userInterface.controller.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.demo.aplication.item.ItemApplicationService;
import com.portofolio.demo.aplication.item.model.CreateItemRequest;
import com.portofolio.demo.aplication.item.model.ItemDto;
import com.portofolio.demo.models.json.item.CreateItemRequestJson;
import com.portofolio.demo.models.json.item.Item;
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

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemApplicationService itemApplicationService;

    @Test
    void canGetItem() throws Exception {
        // Given
        Long itemId = 1L;
        String itemName = "fake-name";
        String uri = "fake-uri";

        ItemDto itemFound = ItemDto.Builder.with().id(itemId).name(itemName).build();
        Item itemExcepted = new Item();
        itemExcepted.setId(itemId);
        itemExcepted.setName(itemName);
        itemExcepted.setUri(uri);

        when(itemApplicationService.getById(itemId)).thenReturn(Optional.of(itemFound));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/item/{id}", itemId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(itemApplicationService).getById(itemId);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(itemExcepted));
    }

    @Test
    void shouldReturnA404IfItemDoNotExists() throws Exception {
        // Given
        Long itemId = 1L;

        when(itemApplicationService.getById(itemId)).thenReturn(Optional.empty());

        // When
        // Then
        this.mockMvc.perform(get("/api/item/{id}", itemId)).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void canDelete() throws Exception {
        // Given
        Long itemId = 1L;

        doNothing().when(itemApplicationService).deleteById(itemId);

        // When
        MvcResult result = this.mockMvc.perform(delete("/api/item/{id}", itemId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(itemApplicationService).deleteById(itemId);
    }

    @Test
    void canGetAll() throws Exception {
        // Given
        Long itemId = 1L;
        String itemName = "fake-name";

        ItemDto itemFound = ItemDto.Builder.with().id(itemId).name(itemName).build();
        Item itemExcepted = new Item();
        itemExcepted.setId(itemId);
        itemExcepted.setName(itemName);

        Long itemId2 = 2L;
        String itemName2 = "fake-222";

        ItemDto itemFound2 = ItemDto.Builder.with().id(itemId2).name(itemName2).build();
        Item itemExcepted2 = new Item();
        itemExcepted2.setId(itemId2);
        itemExcepted2.setName(itemName2);
        List<Item> listExcepted = Lists.list(itemExcepted, itemExcepted2);

        when(itemApplicationService.getAll()).thenReturn(Lists.list(itemFound, itemFound2));

        // When
        MvcResult result = this.mockMvc.perform(get("/api/item", itemId)).andExpect(status().isOk()).andReturn();

        // Then
        Mockito.verify(itemApplicationService).getAll();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(listExcepted));
    }

    @Test
    public void canDOAPost201() throws Exception {
        // Given
        Long itemId = 1L;
        String itemName = "fake-name";

        ItemDto itemPersisted = ItemDto.Builder.with().id(itemId).name(itemName).build();
        Item itemExcepted = new Item();
        itemExcepted.setId(itemId);
        itemExcepted.setName(itemName);

        CreateItemRequestJson requestJson = new CreateItemRequestJson();
        requestJson.setName(itemName);


        when(itemApplicationService.save(any())).thenReturn(itemPersisted);

        // When
        MvcResult result = this.mockMvc.perform(post("/api/item")
                        .content(objectMapper.writeValueAsString(requestJson))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        // Then
        ArgumentCaptor<CreateItemRequest> requestCaptor = ArgumentCaptor.forClass(CreateItemRequest.class);

        Mockito.verify(itemApplicationService).save(requestCaptor.capture());

        CreateItemRequest request = requestCaptor.getValue();

        assertThat(request).isNotNull();
        assertThat(request.getName()).isEqualTo(itemName);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(itemExcepted));
    }
}