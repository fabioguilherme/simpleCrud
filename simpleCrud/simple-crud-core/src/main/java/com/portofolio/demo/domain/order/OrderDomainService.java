package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.user.User;

public interface OrderDomainService {

    Order createOrder(Item item, User user, int quantity);

    void updateOrderStatus(Order order, OrderStatus status);
}
