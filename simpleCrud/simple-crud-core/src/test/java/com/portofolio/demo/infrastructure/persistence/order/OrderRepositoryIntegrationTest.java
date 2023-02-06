package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepository;
import com.portofolio.demo.infrastructure.persistence.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryIntegrationTest extends IntegrationBaseTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository repository;

    @Override
    public void beforeTesting() {
        repository.deleteAll();
    }

    @Override
    public void afterTesting() {
        repository.deleteAll();
    }

    @Test
    public void canPersist() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        User user = userRepository.save(UserFixture.getUser().build());
        int quantity = 3;

        Order orderToStore = Order.Builder.with().item(item).user(user).quantity(quantity).build();

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
    public void canFindById() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        User user = userRepository.save(UserFixture.getUser().build());
        Order order = repository.save(Order.Builder.with().item(item).user(user).quantity(3).build());
        long id = order.getId();

        // When
        Optional<Order> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    public void canDeleteById() {
        // Given
        Item item = itemRepository.save(ItemFixture.getItem());
        User user = userRepository.save(UserFixture.getUser().build());
        Order order = repository.save(Order.Builder.with().item(item).user(user).quantity(3).build());
        long id = order.getId();

        // When
        repository.deleteById(id);

        // Then
        Optional<Order> storedOptional = repository.findById(id);

        assertThat(storedOptional).isEmpty();
    }

    @Override
    protected void clearDataBase() {
        repository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}