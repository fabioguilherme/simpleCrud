package com.portofolio.demo.aplication.item;

import com.portofolio.demo.aplication.item.model.CreateItemRequest;
import com.portofolio.demo.aplication.item.model.ItemDto;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemDomainService;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ItemApplicationServiceImplTest {

    private final String uri_base = "fake-uribase";
    private final String ITEM_URI_BASE = "/item/";

    @Mock
    private ItemDomainService itemDomainService;
    @Mock
    private ItemRepositoryService itemRepositoryService;

    private ItemApplicationServiceImpl applicationService;

    @Before
    public void setUp() throws Exception {
        this.applicationService = new ItemApplicationServiceImpl(itemDomainService, itemRepositoryService, uri_base);
    }

    @Test
    public void canSaveAnItem() throws NoSuchFieldException {
        // Given
        String name = "fake-name";
        CreateItemRequest request = CreateItemRequest.withName(name);

        Mockito.when(itemDomainService.createItem(name)).thenReturn(ItemFixture.getItemWithName(name));
        Mockito.when(itemRepositoryService.save(any())).thenReturn(ItemFixture.getItemWithName(name));

        // When
        ItemDto response = applicationService.save(request);

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getUri()).isEqualTo(uri_base + ITEM_URI_BASE + 1L);

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

    //    Optional<ItemDto> getById(Long id);

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

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(itemRepositoryService).getById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

//
//    List<ItemDto> getAll();

    @Test
    public void canGetAll() throws Exception {
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

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(itemRepositoryService).getById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

}