package com.portofolio.demo.aplication.order;

import com.portofolio.demo.aplication.order.model.CreateOrderRequest;
import com.portofolio.demo.aplication.order.model.OrderDto;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderDomainService;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepositoryService;
import com.portofolio.demo.infrastructure.persistence.order.OrderRepositoryService;
import com.portofolio.demo.infrastructure.persistence.user.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final String URI_BASE;
    private final String ORDER_URI_BASE = "/order/";

    private final UserRepositoryService userRepositoryService;
    private final ItemRepositoryService itemRepositoryService;
    private final OrderDomainService orderDomainService;
    private final OrderRepositoryService orderRepositoryService;

    @Autowired
    public OrderApplicationServiceImpl(UserRepositoryService userRepositoryService, ItemRepositoryService itemRepositoryService,
                                       OrderDomainService orderDomainService, OrderRepositoryService orderRepositoryService, @Value("${uri.base}") String URI_BASE) {
        this.URI_BASE = URI_BASE;
        this.userRepositoryService = userRepositoryService;
        this.itemRepositoryService = itemRepositoryService;
        this.orderDomainService = orderDomainService;
        this.orderRepositoryService = orderRepositoryService;
    }

    @Override
    public OrderDto save(CreateOrderRequest request) {

        User userFound = userRepositoryService.getById(request.getUserId()).orElse(null);
        Item itemFound = itemRepositoryService.getById(request.getItemId()).orElse(null);

        Order orderToPersist = orderDomainService.createOrder(itemFound, userFound, request.getQuantity());
        Order orderPersisted = orderRepositoryService.save(orderToPersist);


        return fromEntity(orderPersisted);
    }

    @Override
    public void deleteById(Long id) {
        orderRepositoryService.deleteById(id);
    }

    @Override
    public void changeStatus(Long orderId, OrderStatus status) {

        Order orderFound = orderRepositoryService.getById(orderId).orElse(null);
        orderDomainService.updateOrderStatus(orderFound, status);

        orderRepositoryService.save(orderFound);
    }

    @Override
    public Optional<OrderDto> getById(Long id) {
        return orderRepositoryService.getById(id).map(this::fromEntity);
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> list = orderRepositoryService.getAll();

        return list.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private OrderDto fromEntity(Order entity) {

        return OrderDto.Builder.with()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .creationDate(entity.getCreationDate())
                .itemName(entity.getItem().getName())
                .userEmail(entity.getUser().getEmail())
                .userName(entity.getUser().getName())
                .uri(URI_BASE + ORDER_URI_BASE + entity.getId())
                .build();
    }
}
