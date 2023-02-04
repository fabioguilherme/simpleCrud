package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.Item.Item;
import com.portofolio.demo.domain.Item.ItemFixture;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class OrderDomainServiceImplTest {

    private OrderDomainService orderDomainService;

    @Before
    public void setUp() throws Exception {
        this.orderDomainService = new OrderDomainServiceImpl();
    }

    @Test
    public void canCreateAnOrder() {
        // Given
        Item item = ItemFixture.getItem();
        User user = UserFixture.getUser().build();
        int quantity = 20;
        // When
        Order order = this.orderDomainService.createOrder(item, user, quantity);

        // Then
        assertThat(order).isNotNull();

        assertThat(order.getItem()).isEqualTo(item);
        assertThat(order.getUser()).isEqualTo(user);
        assertThat(order.getQuantity()).isEqualTo(quantity);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DRAFT);
        assertThat(order.getCreationDate()).isNotNull();
    }

    @Test
    public void canUpdateTheStatusOfAnOrder() {
        // Given
        Order order = OrderFixture.getOrder().build();
        OrderStatus newStatus = OrderStatus.DONE;

        // When
        this.orderDomainService.updateOrderStatus(order, newStatus);

        // Then
        assertThat(order.getStatus()).isNotNull();

        assertThat(order.getStatus()).isEqualTo(newStatus);
    }

    @Test
    public void shouldThrowABusinessExceptionWhenUpdatingToLowerPositionStatus() throws NoSuchFieldException {
        // Given
        Order order = OrderFixture.getOrder().build();
        OrderStatus newStatus = OrderStatus.DONE;
        order.changeStatus(newStatus);
        Field field = order.getClass().getDeclaredField("status");
        field.setAccessible(true);
        ReflectionUtils.setField(field, order, newStatus);

        // When
        // Then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> this.orderDomainService.updateOrderStatus(order, OrderStatus.DRAFT)).withMessage("Can update the order status to a lower status level");
    }

    @Test
    public void shouldThrowABusinessExceptionWhenUpdatingToNullStatus() throws NoSuchFieldException {
        // Given
        Order order = OrderFixture.getOrder().build();
        OrderStatus newStatus = OrderStatus.DONE;
        order.changeStatus(newStatus);

        // When
        // Then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> this.orderDomainService.updateOrderStatus(order, null)).withMessage("New status can not be null");
    }
}