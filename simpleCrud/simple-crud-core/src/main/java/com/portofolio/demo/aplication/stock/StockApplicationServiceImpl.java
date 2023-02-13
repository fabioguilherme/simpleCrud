package com.portofolio.demo.aplication.stock;

import com.portofolio.demo.aplication.stock.model.CreateStockRequest;
import com.portofolio.demo.aplication.stock.model.StockDto;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.domain.stock.StockDomainService;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepositoryService;
import com.portofolio.demo.infrastructure.persistence.stock.StockRepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockApplicationServiceImpl implements StockApplicationService {

    private final String URI_BASE;
    private final String STOCK_URI_BASE = "/stock/";
    private final ItemRepositoryService itemRepositoryService;

    private final StockRepositoryService stockRepositoryService;

    private final StockDomainService stockDomainService;

    public StockApplicationServiceImpl(ItemRepositoryService itemRepositoryService, StockRepositoryService stockRepositoryService,
                                       StockDomainService stockDomainService, @Value("${uri.base}") String URI_BASE) {
        this.itemRepositoryService = itemRepositoryService;
        this.stockRepositoryService = stockRepositoryService;
        this.stockDomainService = stockDomainService;
        this.URI_BASE = URI_BASE;
    }


    @Override
    public StockDto save(CreateStockRequest request) {

        Item itemFound = itemRepositoryService.getById(request.getItemId()).orElse(null);
        Stock stockToPersist = stockDomainService.createStock(itemFound, request.getQuantity());
        Stock stockPersisted = stockRepositoryService.save(stockToPersist);


        return fromEntity(stockToPersist);
    }


    @Override
    public void deleteById(Long id) {
        stockRepositoryService.deleteById(id);
    }

    @Override
    public void addStockToItem(Long stockId, int quantity) {
        Stock stockToPersist = stockRepositoryService.getById(stockId).orElse(null);
        stockDomainService.addStock(stockToPersist, quantity);

        stockRepositoryService.save(stockToPersist);

    }

    @Override
    public void subtractStockToItem(Long stockId, int quantity) {
        Stock stockToPersist = stockRepositoryService.getById(stockId).orElse(null);
        stockDomainService.subtractStock(stockToPersist, quantity);

        stockRepositoryService.save(stockToPersist);
    }

    @Override
    public Optional<StockDto> getById(Long id) {
        return stockRepositoryService.getById(id).map(this::fromEntity);
    }

    @Override
    public List<StockDto> getAll() {
        List<Stock> list = stockRepositoryService.getAll();

        return list.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private StockDto fromEntity(Stock entity) {

        return StockDto.Builder.with()
                .id(entity.getId())
                .itemName(entity.getItem().getName())
                .quantity(entity.getQuantity())
                .uri(URI_BASE + STOCK_URI_BASE + entity.getId())
                .build();
    }
}
