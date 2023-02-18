package com.portofolio.demo.infrastructure.persistence.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepository;
import com.portofolio.demo.shared.errors.BusinessException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockRepositoryServiceImpl implements StockRepositoryService {

    private final ItemRepository itemRepository;

    private final StockRepository repository;

    private final EntityManager entityManager;

    @Autowired
    public StockRepositoryServiceImpl(ItemRepository itemRepository, StockRepository repository, EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public Stock save(Stock stock) {

        if (stock == null) {
            throw new IllegalArgumentException("Stock can not be null");
        }

        if (stock.getId() == null) {

            Optional<Stock> stockOptional = repository.findStockByItemId(stock.getItem().getId());

            if (stockOptional.isPresent()) {
                throw new BusinessException("There already exists a stock for the item id: " + stock.getItem().getId(), null);
            }
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
    public List<Stock> getAll(Long itemId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);

        Root<Stock> book = cq.from(Stock.class);


        if (itemId != null) {

            List<Predicate> predicates = new ArrayList<>();

            Join<Stock, Item> item = book.join("item");

            predicates.add(cb.equal(item.get("id"), itemId));

            cq.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(cq).getResultList();
    }
}
