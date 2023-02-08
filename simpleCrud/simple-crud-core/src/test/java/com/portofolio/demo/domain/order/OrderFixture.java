package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class OrderFixture {

    public static Order getOrder() throws Exception {
        Item item = ItemFixture.getItem();
        User user = UserFixture.getUser();
        int quantity = 20;

        Order order = Order.Builder.with().item(item).quantity(quantity).user(user).build();

        Field field = order.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, order, 1L);

        return order;
    }

    public static Order getOrderWithUserAndItem(User user, Item item, int quantity) throws Exception {
        Order order = Order.Builder.with().item(item).quantity(quantity).user(user).build();

        Field field = order.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, order, 1L);

        return order;
    }
}
