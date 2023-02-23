package com.portofolio.demo.application;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemDomainService;
import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.notification.NotificationDomainService;
import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderDomainService;
import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.domain.stock.StockDomainService;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserDomainService;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepository;
import com.portofolio.demo.infrastructure.persistence.notification.NotificationRepository;
import com.portofolio.demo.infrastructure.persistence.order.OrderRepository;
import com.portofolio.demo.infrastructure.persistence.stock.StockRepository;
import com.portofolio.demo.infrastructure.persistence.stock.StockRepositoryService;
import com.portofolio.demo.infrastructure.persistence.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BatchOrderServiceImplIntegrationTest extends IntegrationBaseTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemDomainService itemDomainService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockRepositoryService stockRepositoryService;
    @Autowired
    private StockDomainService stockDomainService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationDomainService notificationDomainService;

    @Autowired
    private BatchOrderServiceImpl batchOrderOrderService;


    @Test
    void canProcessAnOrder() {
        // Given
        Item item = itemRepository.save(itemDomainService.createItem("test-item"));
        User user = userRepository.save(userDomainService.createUser("fake-user", "fake-user@email.com"));
        int quantity = 3;
        Stock stock = stockRepository.save(stockDomainService.createStock(item, quantity));
        Order order = orderRepository.save(orderDomainService.createOrder(item, user, quantity));

        // When
        batchOrderOrderService.processOrders();

        // Then
        Optional<Stock> stockOptional = stockRepository.findById(stock.getId());
        List<Order> ordersNotProcessed = orderRepository.getOrdersNotDone();
        List<Notification> notifications = notificationRepository.findNotificationByUserId(user.getId());


        assertThat(stockOptional).isPresent();
        assertThat(stockOptional.get().getQuantity()).isEqualTo(0);

        assertThat(ordersNotProcessed).hasSize(0);
        assertThat(ordersNotProcessed).noneMatch(orderFound -> order.getId().equals(orderFound.getId()));

        assertThat(notifications).hasSize(1);
        assertThat(notifications.get(0).getMessage()).isEqualTo("Order processed with the id: " + order.getId());
    }

    @Test
    void afterAnExceptionItShouldProcessTheRemainingOrders() {
        // Given
        Item item = itemRepository.save(itemDomainService.createItem("test-item"));
        Item item2 = itemRepository.save(itemDomainService.createItem("test-item2"));
        User user = userRepository.save(userDomainService.createUser("fake-user", "fake-user@email.com"));
        int quantity = 3;
        Stock stock = stockRepository.save(stockDomainService.createStock(item, quantity));
        Order order = orderRepository.save(orderDomainService.createOrder(item, user, quantity));
        Order order2 = orderRepository.save(orderDomainService.createOrder(item2, user, quantity));

        Stock stock2 = stockRepository.save(stockDomainService.createStock(item2, quantity));
        Order order3 = orderRepository.save(orderDomainService.createOrder(item2, user, quantity));

        // When
        batchOrderOrderService.processOrders();

        // Then
        List<Order> ordersNotProcessed = orderRepository.getOrdersNotDone();
        List<Notification> notifications = notificationRepository.findNotificationByUserId(user.getId());


        assertThat(ordersNotProcessed).hasSize(1);
        assertThat(ordersNotProcessed)
                .noneMatch(orderFound -> order.getId().equals(orderFound.getId()))
                .noneMatch(orderFound -> order.getId().equals(orderFound.getId()));

        assertThat(notifications).hasSize(2);

        assertThat(notifications).anySatisfy(notification -> {
            assertThat(notification.getMessage()).contains("Order processed with the id: ");
        }).anySatisfy(notification -> {
            assertThat(notification.getMessage()).contains("Order processed with the id: ");
        });
    }

    @Override
    public void clearDataBase() {
        stockRepository.deleteAll();
        orderRepository.deleteAll();
        notificationRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}