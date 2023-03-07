package com.portofolio.demo.application;

import com.portofolio.demo.domain.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskOrderProcessor implements Runnable {

    private final Logger log = LoggerFactory.getLogger(TaskOrderProcessor.class);
    private final OrderServiceProcessor orderServiceProcessor;
    private final Order orderToProcess;

    public TaskOrderProcessor(OrderServiceProcessor orderServiceProcessor, Order orderToProcess) {
        this.orderServiceProcessor = orderServiceProcessor;
        this.orderToProcess = orderToProcess;
    }

    private void processOrder(Order orderToProcess) {

        log.info("Processing order with id: " + orderToProcess.getId() + " in thread: " + Thread.currentThread().getName());

        try {
            this.orderServiceProcessor.processOrder(orderToProcess);
            log.info("Processed order with id: " + orderToProcess.getId());
        } catch (RuntimeException exception) {
            log.error("Error processing order with id: " + orderToProcess.getId());
        }

    }

    @Override
    public void run() {
        processOrder(this.orderToProcess);
    }
}
