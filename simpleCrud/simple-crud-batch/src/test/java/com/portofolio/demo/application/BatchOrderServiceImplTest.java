package com.portofolio.demo.application;

import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.infrastructure.persistence.order.OrderRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.task.TaskExecutor;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BatchOrderServiceImplTest {


    @Mock
    private OrderRepositoryService orderRepositoryService;
    @Mock
    private OrderServiceProcessor orderServiceProcessor;
    @Mock
    private TaskExecutor taskExecutor;

    private BatchOrderServiceImpl batchOrderOrderService;

    @BeforeEach
    void setUp() {
        this.batchOrderOrderService = new BatchOrderServiceImpl(orderRepositoryService, orderServiceProcessor, taskExecutor);
    }

    @Test
    void canProcessAnOrder() {
        // Given
        int quantity = 3;
        Order order = Mockito.mock(Order.class);

        when(orderRepositoryService.getOrdersNotDone()).thenReturn(Collections.singletonList(order));

        // When
        batchOrderOrderService.processOrders();

        // Then
        ArgumentCaptor<TaskOrderProcessor> argumentCaptorTask = ArgumentCaptor.forClass(TaskOrderProcessor.class);
        verify(taskExecutor).execute(argumentCaptorTask.capture());

        assertThat(argumentCaptorTask.getValue()).isNotNull();
        assertThat(argumentCaptorTask.getValue().getOrderToProcess()).isEqualTo(order);
    }
}