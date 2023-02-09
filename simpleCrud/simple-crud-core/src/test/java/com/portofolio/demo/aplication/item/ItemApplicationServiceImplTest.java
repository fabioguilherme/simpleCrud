package com.portofolio.demo.aplication.item;

import com.portofolio.demo.aplication.item.model.CreateItemRequest;
import com.portofolio.demo.aplication.item.model.ItemDto;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemDomainService;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepositoryService;
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
public class ItemApplicationServiceImplTest {

    private final String URI_BASE = "fake-uribase";
    private final String ITEM_URI_BASE = "/item/";

    @Mock
    private ItemDomainService itemDomainService;
    @Mock
    private ItemRepositoryService itemRepositoryService;

    private ItemApplicationServiceImpl applicationService;

    @BeforeEach
    public void setUp() throws Exception {
        this.applicationService = new ItemApplicationServiceImpl(itemDomainService, itemRepositoryService, URI_BASE);
    }

    @Test
    public void canSaveAnItem() throws Exception {
        // Given
        String name = "fake-name";
        CreateItemRequest request = CreateItemRequest.withName(name);
        Item itemPersisted = ItemFixture.getItemWithName(name);

        Mockito.when(itemDomainService.createItem(name)).thenReturn(itemPersisted);
        Mockito.when(itemRepositoryService.save(any())).thenReturn(ItemFixture.getItemWithName(name));

        // When
        ItemDto response = applicationService.save(request);

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getUri()).isEqualTo(URI_BASE + ITEM_URI_BASE + itemPersisted.getId());

        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);

        Mockito.verify(itemDomainService).createItem(name);
        Mockito.verify(itemRepositoryService).save(argumentCaptor.capture());

        Item itemCaptured = argumentCaptor.getValue();

        assertThat(itemCaptured).isNotNull();

        assertThat(itemCaptured.getName()).isEqualTo(name);
    }

    @Test
    public void canDeleteById() {
        // Given
        Long id = 1L;

        // When
        applicationService.deleteById(id);

        // Then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(itemRepositoryService).deleteById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void canGetItemById() throws Exception {
        // Given
        Long id = 1L;
        Item itemFound = ItemFixture.getItem();

        Mockito.when(itemRepositoryService.getById(id)).thenReturn(Optional.of(itemFound));

        // When
        Optional<ItemDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isPresent();

        ItemDto dto = response.get();

        assertThat(dto.getName()).isEqualTo(itemFound.getName());
        assertThat(dto.getId()).isEqualTo(itemFound.getId());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ITEM_URI_BASE + itemFound.getId());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(itemRepositoryService).getById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void shouldReturnOptionalEmptyIfItemDoNotExists() throws Exception {
        // Given
        Long id = 1L;

        Mockito.when(itemRepositoryService.getById(id)).thenReturn(Optional.empty());

        // When
        Optional<ItemDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isEmpty();
    }

    @Test
    public void canGetAll() throws Exception {
        // Given
        Item itemFound = ItemFixture.getItem();

        Mockito.when(itemRepositoryService.getAll()).thenReturn(Collections.singletonList(itemFound));

        // When
        List<ItemDto> response = applicationService.getAll();

        // Then
        assertThat(response).hasSize(1);

        ItemDto dto = response.get(0);

        assertThat(dto.getName()).isEqualTo(itemFound.getName());
        assertThat(dto.getId()).isEqualTo(itemFound.getId());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ITEM_URI_BASE + itemFound.getId());
    }

}