package com.portofolio.demo.infrastructure.order;

import com.portofolio.demo.IntegrationBaseTest;
import com.portofolio.demo.domain.Item.Item;
import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.infrastructure.item.ItemRepository;
import com.portofolio.demo.infrastructure.user.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

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
    @Sql(statements = {"INSERT INTO simple_crud_test.item (id, name)\tVALUES (1, \"fake\");" +
            "INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\tVALUES (1, \"fake-name\",\"fake-name@email.com\");"})
    public void canPersist() {
        // Given
        Item item = itemRepository.findById(1L).get();
        User user = userRepository.findById(1L).get();
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
    @Sql(statements = {"INSERT INTO simple_crud_test.item (id, name)\tVALUES (1, \"fake\");" +
            "INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\tVALUES (1, \"fake-name\",\"fake-name@email.com\");" +
            "INSERT INTO simple_crud_test.simple_crud_order (id, item_id,user_id,quantity,creation_date,order_status)\n" +
            "\tVALUES (1,1,1,30,CURRENT_TIMESTAMP,'DRAFT');"})
    public void canFindById() {
        // Given
        long id = 1L;

        // When
        Optional<Order> storedOptional = repository.findById(id);

        // Then
        assertThat(storedOptional).isPresent();
    }

    @Test
    @Sql(statements = {"INSERT INTO simple_crud_test.item (id, name)\tVALUES (1, \"fake\");" +
            "INSERT INTO simple_crud_test.simple_crud_user (id, name, email)\tVALUES (1, \"fake-name\",\"fake-name@email.com\");" +
            "INSERT INTO simple_crud_test.simple_crud_order (id, item_id,user_id,quantity,creation_date,order_status)\n" +
            "\tVALUES (1,1,1,30,CURRENT_TIMESTAMP,'DRAFT');"})
    public void canDeleteById() {
        // Given
        long id = 1L;

        // When
        repository.deleteById(id);

        // Then
        Optional<Order> storedOptional = repository.findById(id);

        assertThat(storedOptional).isEmpty();
    }
}