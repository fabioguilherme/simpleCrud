package com.portofolio.demo.userInterface.controller.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.demo.aplication.item.ItemApplicationService;
import com.portofolio.demo.aplication.item.model.ItemDto;
import com.portofolio.demo.models.json.item.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        ItemDto itemFound = ItemDto.Builder.with().id(itemId).name(itemName).build();
        Item itemExcepted = new Item();
        itemExcepted.setId(itemId);
        itemExcepted.setName(itemName);

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
}