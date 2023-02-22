package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.order.Order;
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

public class OrderRepositoryIntegrationTest extends IntegrationBaseTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository repository;

    @Test
    public void canPersist() throws Exception {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        User user = userRepository.save(UserFixture.getUser());
        int quantity = 3;

        Order orderToStore = OrderFixture.getOrderWithUserAndItem(user, item, quantity);

        // When
        Order stored = repository.save(orderToStore);

        // Then
        assertThat(stored).isNotNull();

        assertThat(stored.getItem().getId()).isEqualTo(item.getId());
        assertThat(stored.getUser().getId()).isEqualTo(user.getId());
        assertThat(stored.getQuantity()).isEqualTo(quantity);
        assertThat(stored.getCreationDate()).isNotNull();
        assertThat(stored.getStatus()).isEqualTo(OrderStatus.DRAFT);
    }

    @Test
    public void canFindById() throws Exception {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        User user = userRepository.save(UserFixture.getUser());
        Order order = repository.save(OrderFixture.getOrderWithUserAndItem(user, item, 3));
        long id = order.getId();

        // When
        Optional<Order> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    public void canDeleteById() throws Exception {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        User user = userRepository.save(UserFixture.getUser());
        Order order = repository.save(OrderFixture.getOrderWithUserAndItem(user, item, 3));
        long id = order.getId();

        // When
        repository.deleteById(id);

        // Then
        Optional<Order> storedOptional = repository.findById(id);

        assertThat(storedOptional).isEmpty();
    }

    @Test
    public void canGetOrdersNotDone() throws Exception {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        User user = userRepository.save(UserFixture.getUser());
        Order orderNotDone = repository.save(OrderFixture.getOrderWithUserAndItem(user, item, 3));
        repository.save(OrderFixture.getOrderWithUserAndItemDone(user, item, 3));
        long id = orderNotDone.getId();

        // When
        List<Order> ordersToProcess = repository.getOrdersNotDone();

        // Then
        assertThat(ordersToProcess).hasSize(1);

        assertThat(ordersToProcess).anySatisfy(order -> {
            assertThat(order.getId()).isEqualTo(id);
        });
    }

    @Override
    public void clearDataBase() {
        repository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}