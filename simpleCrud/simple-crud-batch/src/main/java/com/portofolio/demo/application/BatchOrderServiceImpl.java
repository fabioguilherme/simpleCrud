package com.portofolio.demo.application;

import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.infrastructure.persistence.order.OrderRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchOrderServiceImpl implements BatchOrderService {

    private final Logger log = LoggerFactory.getLogger(BatchOrderServiceImpl.class);


    private final OrderRepositoryService orderRepositoryService;

    private final OrderServiceProcessor orderServiceProcessor;

    private final TaskExecutor taskExecutor;

    @Autowired
    public BatchOrderServiceImpl(OrderRepositoryService orderRepositoryService, OrderServiceProcessor orderServiceProcessor, TaskExecutor taskExecutor) {
        this.orderRepositoryService = orderRepositoryService;
        this.orderServiceProcessor = orderServiceProcessor;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void processOrders() {

        log.info("Processing orders and creating tasks");

        List<Order> orders = orderRepositoryService.getOrdersNotDone();

        for (Order order : orders) {

            TaskOrderProcessor task = new TaskOrderProcessor(orderServiceProcessor, order);
            taskExecutor.execute(task);
            log.info("Created task for order with id: " + order.getId());
        }

        log.info("Add all the orders to the taskExecutor");
    }

}
