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

        int retry = 0;
        boolean canContinue = true;

        while (retry < 7 && canContinue) {

            try {
                this.orderServiceProcessor.processOrder(orderToProcess);
                log.info("Processed order with id: " + orderToProcess.getId());
                canContinue = false;
            } catch (RuntimeException exception) {
                log.error("Error processing order with id: " + orderToProcess.getId() + ". With error: " + exception.getMessage());
                try {
                    Thread.sleep(250L);
                } catch (InterruptedException e) {
                    log.warn("Thread interrupted with name: " + Thread.currentThread().getName());
                }
            }

            retry++;
        }
    }

    public Order getOrderToProcess() {
        return orderToProcess;
    }

    @Override
    public void run() {
        processOrder(this.orderToProcess);
    }
}
