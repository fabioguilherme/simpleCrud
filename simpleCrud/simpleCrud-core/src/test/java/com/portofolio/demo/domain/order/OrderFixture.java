package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;

public class OrderFixture {

    public static Order.Builder getOrder() {
        Item item = ItemFixture.getItem();
        User user = UserFixture.getUser().build();
        int quantity = 20;

        return Order.Builder.with().item(item).quantity(quantity).user(user);
    }
}
