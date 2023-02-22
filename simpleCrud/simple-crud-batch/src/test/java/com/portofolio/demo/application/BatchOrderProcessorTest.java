package com.portofolio.demo.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BatchOrderProcessorTest {

    private BatchOrderProcessor batch;

    @Mock
    private BatchOrderService orderService;

    @BeforeEach
    void setUp() {
        this.batch = new BatchOrderProcessor(orderService);
    }

    @Test
    void canProcess() {
        // Given

        // When
        batch.processOrders();
        
        // Then
        Mockito.verify(orderService).processOrders();
    }
}