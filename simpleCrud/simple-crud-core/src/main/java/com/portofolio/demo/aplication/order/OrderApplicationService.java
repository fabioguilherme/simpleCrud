package com.portofolio.demo.aplication.order;

import com.portofolio.demo.aplication.order.model.CreateOrderRequest;
import com.portofolio.demo.aplication.order.model.OrderDto;
import com.portofolio.demo.domain.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderApplicationService {

    OrderDto save(CreateOrderRequest request);

    void deleteById(Long id);

    void changeStatus(Long orderId, OrderStatus status);

    Optional<OrderDto> getById(Long id);

    List<OrderDto> getAll(Long userId, OrderStatus status);
}
