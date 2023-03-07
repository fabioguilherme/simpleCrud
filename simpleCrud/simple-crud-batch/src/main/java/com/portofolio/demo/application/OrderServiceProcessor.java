package com.portofolio.demo.application;

import com.portofolio.demo.domain.order.Order;


public interface OrderServiceProcessor {
    void processOrder(Order orderToProcess) throws RuntimeException;
}
