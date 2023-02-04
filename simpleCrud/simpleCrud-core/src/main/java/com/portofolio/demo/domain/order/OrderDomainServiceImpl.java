package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.Item.Item;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.shared.errors.BusinessException;

public class OrderDomainServiceImpl implements OrderDomainService {

    @Override
    public Order createOrder(Item item, User user, int quantity) {
        return Order.Builder.with().item(item).user(user).quantity(quantity).build();
    }

    @Override
    public void updateOrderStatus(Order order, OrderStatus status) {

        if (status == null) {
            throw new BusinessException("New status can not be null");
        }

        if (order.getStatus().getPosition() > status.getPosition()) {
            throw new BusinessException("Can update the order status to a lower status level");
        }

        order.changeStatus(status);
    }
}
