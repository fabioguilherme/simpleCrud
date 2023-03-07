package com.portofolio.demo.application;

import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.notification.NotificationDomainService;
import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderDomainService;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.domain.stock.Stock;
import com.portofolio.demo.domain.stock.StockDomainService;
import com.portofolio.demo.infrastructure.persistence.notification.NotificationRepository;
import com.portofolio.demo.infrastructure.persistence.order.OrderRepositoryService;
import com.portofolio.demo.infrastructure.persistence.stock.StockRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderServiceProcessorImpl implements OrderServiceProcessor {

    private final Logger log = LoggerFactory.getLogger(OrderServiceProcessorImpl.class);


    private final OrderRepositoryService orderRepositoryService;

    private final StockRepositoryService stockRepositoryService;

    private final StockDomainService stockDomainService;

    private final PlatformTransactionManager transactionManager;

    private final OrderDomainService orderDomainService;

    private final NotificationDomainService notificationDomainService;

    private final NotificationRepository notificationRepository;

    public OrderServiceProcessorImpl(OrderRepositoryService orderRepositoryService, StockRepositoryService stockRepositoryService,
                                     StockDomainService stockDomainService, PlatformTransactionManager transactionManager, OrderDomainService orderDomainService, NotificationDomainService notificationDomainService, NotificationRepository notificationRepository) {
        this.orderRepositoryService = orderRepositoryService;
        this.stockRepositoryService = stockRepositoryService;
        this.stockDomainService = stockDomainService;
        this.transactionManager = transactionManager;
        this.orderDomainService = orderDomainService;
        this.notificationDomainService = notificationDomainService;
        this.notificationRepository = notificationRepository;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @Override
    public void processOrder(Order orderToProcess) throws RuntimeException {

        this.log.info("Processing order with id: " + orderToProcess.getId());

        int quantity = orderToProcess.getQuantity();

        Optional<Stock> stockOptional = stockRepositoryService.getStockByItemId(orderToProcess.getItem().getId());
        Stock stock = stockOptional.orElse(null);
        stockDomainService.subtractStock(stock, quantity);
        stockRepositoryService.save(stock);
        orderDomainService.updateOrderStatus(orderToProcess, OrderStatus.DONE);
        orderRepositoryService.save(orderToProcess);
        Notification notification = notificationDomainService.createNotification(orderToProcess.getUser(), "Order processed with the id: " + orderToProcess.getId());
        notificationRepository.save(notification);

        this.log.info("Processed order with id: " + orderToProcess.getId());
    }
}
