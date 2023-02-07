package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.shared.errors.BusinessException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class OrderTest {

    @Test
    void canBuild() {
        // Given
        Item item = ItemFixture.getItem();
        User user = UserFixture.getUser();
        int quantity = 20;
        // When
        Order order = Order.Builder.with().item(item).quantity(quantity).user(user).build();

        // Then
        assertThat(order).isNotNull();

        assertThat(order.getItem()).isEqualTo(item);
        assertThat(order.getUser()).isEqualTo(user);
        assertThat(order.getQuantity()).isEqualTo(quantity);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DRAFT);
        assertThat(order.getCreationDate()).isNotNull();
    }

    @Test
    void shouldThrowABusinessExceptionIfItemIsNUll() {
        // Given
        Item item = null;
        User user = UserFixture.getUser();
        int quantity = 20;

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Order.Builder.with().item(item).quantity(quantity).user(user);
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Item can not be null");
    }

    @Test
    void shouldThrowABusinessExceptionIfUserIsNUll() {
        // Given
        Item item = ItemFixture.getItem();
        User user = null;
        int quantity = 20;

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Order.Builder.with().item(item).quantity(quantity).user(user).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("User can not be null");
    }

    @Test
    void shouldThrowABusinessExceptionIfQuantityEqualZero() {
        // Given
        Item item = ItemFixture.getItem();
        User user = UserFixture.getUser();
        int quantity = 0;

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Order.Builder.with().item(item).quantity(quantity).user(user).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Quantity can not be zero");
    }

    @Test
    void shouldThrowABusinessExceptionIfQuantityBellowZero() {
        // Given
        Item item = ItemFixture.getItem();
        User user = UserFixture.getUser();
        int quantity = -1;

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Order.Builder.with().item(item).quantity(quantity).user(user).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Quantity can not be bellow zero");
    }

    @Test
    void shouldThrowABusinessExceptionIfOrderStatusIsNull() {
        // Given
        Order order = OrderFixture.getOrder();
        OrderStatus newStatus = null;

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> order.changeStatus(newStatus);
        ;
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Status can not be null");
    }

    @Test
    void canUpdateOrderStatus() {
        // Given
        Order order = OrderFixture.getOrder();
        OrderStatus newStatus = OrderStatus.DONE;

        // When
        order.changeStatus(newStatus);

        // Then
        assertThat(order.getStatus()).isNotNull();

        assertThat(order.getStatus()).isEqualTo(newStatus);
    }
}