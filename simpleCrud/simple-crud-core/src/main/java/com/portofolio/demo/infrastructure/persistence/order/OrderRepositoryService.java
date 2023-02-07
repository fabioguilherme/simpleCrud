package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryService {

    Order save(Order stock);

    void deleteById(Long id);

    Optional<Order> getById(Long id);

    List<Order> getAll();
}
