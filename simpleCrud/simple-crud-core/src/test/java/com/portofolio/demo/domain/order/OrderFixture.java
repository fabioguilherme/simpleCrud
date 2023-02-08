package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;

public class OrderFixture {

    public static Order getOrder() throws Exception {
        Item item = ItemFixture.getItem();
        User user = UserFixture.getUser();
        int quantity = 20;

        return Order.Builder.with().item(item).quantity(quantity).user(user).build();
    }

    public static Order getOrderWithUserAndItem(User user, Item item, int quantity) {
        return Order.Builder.with().item(item).quantity(quantity).user(user).build();
    }
}
