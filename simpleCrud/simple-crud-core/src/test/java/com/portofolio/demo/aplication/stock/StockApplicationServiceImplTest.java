package com.portofolio.demo.aplication.stock;

import com.portofolio.demo.aplication.stock.model.CreateStockRequest;
import com.portofolio.demo.aplication.stock.model.StockDto;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.domain.stock.StockDomainService;
import com.portofolio.demo.domain.stock.StockFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepositoryService;
import com.portofolio.demo.infrastructure.persistence.stock.StockRepositoryService;
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

@ExtendWith(MockitoExtension.class)
public class StockApplicationServiceImplTest {

    private final String URI_BASE = "fake-uribase";
    private final String STOCK_URI_BASE = "/stock/";

    @Mock
    private ItemRepositoryService itemRepositoryService;
    @Mock
    private StockDomainService stockDomainService;
    @Mock
    private StockRepositoryService stockRepositoryService;

    private StockApplicationServiceImpl applicationService;

    @BeforeEach
    public void setUp() throws Exception {
        this.applicationService = new StockApplicationServiceImpl(itemRepositoryService, stockRepositoryService, stockDomainService, URI_BASE);
    }

    @Test
    public void canSaveAnStock() throws Exception {
        // Given
        Long itemId = 1L;
        int quantity = 6;
        CreateStockRequest request = CreateStockRequest.Builder.with().itemId(itemId).quantity(quantity).build();
        Item itemReturned = ItemFixture.getItem();
        Stock stockToPersist = StockFixture.getStockWithItem(itemReturned, quantity);
        Stock stockPersisted = StockFixture.getStockWithItem(itemReturned, quantity);


        Mockito.when(itemRepositoryService.getById(itemId)).thenReturn(Optional.of(itemReturned));
        Mockito.when(stockDomainService.createStock(itemReturned, quantity)).thenReturn(stockToPersist);
        Mockito.when(stockRepositoryService.save(stockToPersist)).thenReturn(stockPersisted);

        // When
        StockDto response = applicationService.save(request);

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getItemName()).isEqualTo(stockPersisted.getItem().getName());
        assertThat(response.getId()).isEqualTo(stockPersisted.getId());
        assertThat(response.getQuantity()).isEqualTo(stockPersisted.getQuantity());
        assertThat(response.getUri()).isEqualTo(URI_BASE + STOCK_URI_BASE + stockToPersist.getId());

        ArgumentCaptor<Stock> argumentCaptor = ArgumentCaptor.forClass(Stock.class);

        Mockito.verify(itemRepositoryService).getById(itemId);
        Mockito.verify(stockDomainService).createStock(itemReturned, quantity);
        Mockito.verify(stockRepositoryService).save(argumentCaptor.capture());

        Stock stockCaptured = argumentCaptor.getValue();

        assertThat(stockCaptured).isNotNull();

        assertThat(stockCaptured.getItem()).isEqualTo(itemReturned);
        assertThat(stockCaptured.getQuantity()).isEqualTo(quantity);
    }

    @Test
    public void canDeleteById() {
        // Given
        Long id = 1L;

        // When
        applicationService.deleteById(id);

        // Then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(stockRepositoryService).deleteById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void canGetStockById() throws Exception {
        // Given
        Long id = 1L;
        Stock stockFound = StockFixture.getStock();

        Mockito.when(stockRepositoryService.getById(id)).thenReturn(Optional.of(stockFound));

        // When
        Optional<StockDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isPresent();

        StockDto dto = response.get();

        assertThat(dto.getItemName()).isEqualTo(stockFound.getItem().getName());
        assertThat(dto.getId()).isEqualTo(stockFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(stockFound.getQuantity());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + STOCK_URI_BASE + stockFound.getId());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(stockRepositoryService).getById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void shouldReturnOptionalEmptyIfStockDoNotExists() {
        // Given
        Long id = 1L;

        Mockito.when(stockRepositoryService.getById(id)).thenReturn(Optional.empty());

        // When
        Optional<StockDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isEmpty();
    }

    @Test
    public void canGetAll() throws Exception {
        // Given
        Stock stockFound = StockFixture.getStock();

        Mockito.when(stockRepositoryService.getAll(null)).thenReturn(Collections.singletonList(stockFound));

        // When
        List<StockDto> response = applicationService.getAll(null);

        // Then
        assertThat(response).hasSize(1);

        StockDto dto = response.get(0);

        assertThat(dto.getItemName()).isEqualTo(stockFound.getItem().getName());
        assertThat(dto.getId()).isEqualTo(stockFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(stockFound.getQuantity());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + STOCK_URI_BASE + stockFound.getId());
    }

    @Test
    public void canGetAllByItemId() throws Exception {
        // Given
        Stock stockFound = StockFixture.getStock();
        Long itemId = stockFound.getItem().getId();

        Mockito.when(stockRepositoryService.getAll(itemId)).thenReturn(Collections.singletonList(stockFound));

        // When
        List<StockDto> response = applicationService.getAll(itemId);

        // Then
        assertThat(response).hasSize(1);

        StockDto dto = response.get(0);

        assertThat(dto.getItemName()).isEqualTo(stockFound.getItem().getName());
        assertThat(dto.getId()).isEqualTo(stockFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(stockFound.getQuantity());
        assertThat(dto.getUri()).isEqualTo(URI_BASE + STOCK_URI_BASE + stockFound.getId());
    }


    @Test
    public void canAddStockToItem() throws Exception {
        // Given
        Long stockId = 1L;
        int quantity = 3;
        Stock stockReturned = StockFixture.getStock();

        Mockito.when(stockRepositoryService.getById(stockId)).thenReturn(Optional.of(stockReturned));

        // When
        applicationService.addStockToItem(stockId, quantity);

        // Then
        ArgumentCaptor<Stock> argumentCaptor = ArgumentCaptor.forClass(Stock.class);

        Mockito.verify(stockRepositoryService).getById(stockId);
        Mockito.verify(stockDomainService).addStock(stockReturned, quantity);
        Mockito.verify(stockRepositoryService).save(argumentCaptor.capture());

        Stock stockCaptured = argumentCaptor.getValue();

        assertThat(stockCaptured).isNotNull();

        assertThat(stockCaptured.getId()).isEqualTo(stockCaptured.getId());
    }

    @Test
    public void canSubtractStockToItem() throws Exception {
        // Given
        Long stockId = 1L;
        int quantity = 3;
        Stock stockReturned = StockFixture.getStock();

        Mockito.when(stockRepositoryService.getById(stockId)).thenReturn(Optional.of(stockReturned));

        // When
        applicationService.subtractStockToItem(stockId, quantity);

        // Then
        ArgumentCaptor<Stock> argumentCaptor = ArgumentCaptor.forClass(Stock.class);

        Mockito.verify(stockRepositoryService).getById(stockId);
        Mockito.verify(stockDomainService).subtractStock(stockReturned, quantity);
        Mockito.verify(stockRepositoryService).save(argumentCaptor.capture());

        Stock stockCaptured = argumentCaptor.getValue();

        assertThat(stockCaptured).isNotNull();

        assertThat(stockCaptured.getId()).isEqualTo(stockCaptured.getId());
    }
}