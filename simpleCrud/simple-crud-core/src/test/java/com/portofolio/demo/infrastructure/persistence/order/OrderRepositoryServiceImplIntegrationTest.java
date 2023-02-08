package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderDomainService;
import com.portofolio.demo.domain.order.OrderFixture;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepository;
import com.portofolio.demo.infrastructure.persistence.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class OrderRepositoryServiceImplIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderDomainService domainService;

    @Autowired
    private OrderRepositoryService service;

    @Test
    public void canCreate() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        Item item = itemRepository.save(ItemFixture.getItem());
        int quantity = 5;
        Order orderToPersist = OrderFixture.getOrderWithUserAndItem(user, item, quantity);

        // When
        Order orderPersisted = service.save(orderToPersist);

        // Then
        Optional<Order> orderFound = repository.findById(orderPersisted.getId());

        assertThat(orderFound).isPresent();

        Order order = orderFound.get();

        assertThat(order.getItem()).isNotNull();
        assertThat(order.getUser()).isNotNull();

        assertThat(order.getQuantity()).isEqualTo(quantity);
    }

    @Test
    public void shouldThrowAnExceptionIfItemIsNullWhenCreating() {
        // Given
        Order orderToPersist = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.save(orderToPersist)).withMessage("Order can not be null");
    }

    @Test
    public void canUpdateStatus() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        Item item = itemRepository.save(ItemFixture.getItem());
        int quantity = 5;
        Order orderToPersist = OrderFixture.getOrderWithUserAndItem(user, item, quantity);
        Order orderToUpdate = service.save(orderToPersist);
        OrderStatus finalStatus = OrderStatus.DONE;

        // When
        domainService.updateOrderStatus(orderToUpdate, finalStatus);
        Order orderUpdated = service.save(orderToUpdate);


        // Then
        assertThat(orderUpdated).isNotNull();

        assertThat(orderUpdated.getItem()).isNotNull();

        assertThat(orderUpdated.getStatus()).isEqualTo(finalStatus);
    }

    @Test
    public void canDeleteById() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        Item item = itemRepository.save(ItemFixture.getItem());
        int quantity = 5;
        Order orderToPersist = service.save(OrderFixture.getOrderWithUserAndItem(user, item, quantity));
        Long id = orderToPersist.getId();

        // When
        service.deleteById(id);

        // Then
        Optional<Order> orderFoundOptional = repository.findById(id);

        assertThat(orderFoundOptional).isEmpty();
    }

    @Test
    public void shouldThrowAnExceptionIfIdIsNullWhenDeleting() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.deleteById(id)).withMessage("Id can not be null");
    }

    @Test
    public void canGetById() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        Item item = itemRepository.save(ItemFixture.getItem());
        int quantity = 5;
        Order orderToPersist = service.save(OrderFixture.getOrderWithUserAndItem(user, item, quantity));
        Long id = orderToPersist.getId();

        // When
        Optional<Order> orderFoundOptional = service.getById(id);

        // Then
        assertThat(orderFoundOptional).isPresent();
    }

    @Test
    public void shouldThrowAnExceptionIfIdIsNullWhenGettingById() {
        // Given
        Long id = null;

        // When
        // Then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> service.getById(id)).withMessage("Id can not be null");
    }

    @Test
    public void canGetAll() throws Exception {
        // Given
        User user = userRepository.save(UserFixture.getUser());
        Item item = itemRepository.save(ItemFixture.getItem());
        int quantity = 5;
        Order orderToPersist = service.save(OrderFixture.getOrderWithUserAndItem(user, item, quantity));

        int quantity2 = 8;
        Order orderToPersist2 = service.save(OrderFixture.getOrderWithUserAndItem(user, item, quantity2));
        Long id = orderToPersist.getId();


        // When
        List<Order> list = service.getAll();

        // Then
        assertThat(list).hasSize(2);

        assertThat(list).anyMatch(o -> o.getItem().getName().equals(orderToPersist.getItem().getName()) && orderToPersist.getUser().getName().equals(user.getName()) && orderToPersist.getQuantity() == quantity);
        assertThat(list).anyMatch(o -> o.getItem().getName().equals(orderToPersist2.getItem().getName()) && orderToPersist2.getUser().getName().equals(user.getName()) && orderToPersist2.getQuantity() == quantity2);
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}