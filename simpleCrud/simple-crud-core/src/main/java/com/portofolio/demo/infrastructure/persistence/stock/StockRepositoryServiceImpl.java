package com.portofolio.demo.infrastructure.persistence.stock;

import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockRepositoryServiceImpl implements StockRepositoryService {

    private final ItemRepository itemRepository;

    private final StockRepository repository;

    @Autowired
    public StockRepositoryServiceImpl(ItemRepository itemRepository, StockRepository repository) {
        this.itemRepository = itemRepository;
        this.repository = repository;
    }

    @Override
    public Stock save(Stock stock) {

        if (stock == null) {
            throw new IllegalArgumentException("Stock can not be null");
        }

        return repository.save(stock);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<Stock> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        return repository.findById(id);
    }

    @Override
    public List<Stock> getAll() {
        Iterable<Stock> iterable = repository.findAll();

        List<Stock> list = new ArrayList<Stock>();
        iterable.forEach(list::add);

        return list;
    }
}
