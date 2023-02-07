package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.domain.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderRepositoryServiceImpl implements OrderRepositoryService {

    private final OrderRepository repository;

    @Autowired
    public OrderRepositoryServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order can not be null");
        }

        return repository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<Order> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        return repository.findById(id);
    }

    @Override
    public List<Order> getAll() {
        Iterable<Order> iterable = repository.findAll();

        List<Order> list = new ArrayList<Order>();
        iterable.forEach(list::add);

        return list;
    }
}
