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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BatchOrderServiceImpl implements BatchOrderService {

    private final Logger log = LoggerFactory.getLogger(BatchOrderServiceImpl.class);


    private final OrderRepositoryService orderRepositoryService;

    private final StockRepositoryService stockRepositoryService;

    private final StockDomainService stockDomainService;

    private final PlatformTransactionManager transactionManager;

    private final OrderDomainService orderDomainService;

    private final NotificationDomainService notificationDomainService;

    private final NotificationRepository notificationRepository;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public BatchOrderServiceImpl(OrderRepositoryService orderRepositoryService, StockRepositoryService stockRepositoryService,
                                 StockDomainService stockDomainService, PlatformTransactionManager transactionManager,
                                 OrderDomainService orderDomainService, NotificationDomainService notificationDomainService,
                                 NotificationRepository notificationRepository) {
        this.orderRepositoryService = orderRepositoryService;
        this.stockRepositoryService = stockRepositoryService;
        this.stockDomainService = stockDomainService;
        this.transactionManager = transactionManager;
        this.orderDomainService = orderDomainService;
        this.notificationDomainService = notificationDomainService;
        this.notificationRepository = notificationRepository;
        this.transactionTemplate = new TransactionTemplate(this.transactionManager);
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
    }

    @Override
    public void processOrders() {

        log.info("Processing orders");

        List<Order> orders = orderRepositoryService.getOrdersNotDone();

        long startTime = System.currentTimeMillis();

        for (Order order : orders) {
            try {

                log.info("Processing order with id: " + order.getId());

                TransactionCallback<Void> callback = status -> {
                    processOrder(order);
                    return null;
                };

                transactionTemplate.execute(callback);

                log.info("Processed order with id: " + order.getId());

            } catch (RuntimeException e) {
                log.error("Error processing order with id: " + order.getId() + "\n" + "Error: " + e.getMessage());
            }
        }

        long finishTime = System.currentTimeMillis();

        log.info("the process finished in: " + (finishTime - startTime) / 1000 + " seconds");

    }

    private void processOrder(Order orderToProcess) {

        //we will ignore any business validation here because they will be treated at the responsible layers

        int quantity = orderToProcess.getQuantity();

        Optional<Stock> stockOptional = stockRepositoryService.getStockByItemId(orderToProcess.getItem().getId());
        Stock stock = stockOptional.orElse(null);
        stockDomainService.subtractStock(stock, quantity);
        stockRepositoryService.save(stock);
        orderDomainService.updateOrderStatus(orderToProcess, OrderStatus.DONE);
        orderRepositoryService.save(orderToProcess);
        Notification notification = notificationDomainService.createNotification(orderToProcess.getUser(), "Order processed with the id: " + orderToProcess.getId());
        notificationRepository.save(notification);

    }
}
