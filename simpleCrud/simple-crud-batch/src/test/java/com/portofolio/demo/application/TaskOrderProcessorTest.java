package com.portofolio.demo.application;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.portofolio.demo.domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskOrderProcessorTest {

    @Mock
    private Order orderToProcess;
    @Mock
    private OrderServiceProcessor orderServiceProcessor;

    @InjectMocks
    private TaskOrderProcessor task;

    private Appender appender;

    @BeforeEach
    void setUp() {
        this.appender = new Appender();
        Logger logger = (Logger) LoggerFactory.getLogger(TaskOrderProcessor.class);
        logger.addAppender(appender);
        this.appender.start();
    }

    @Test
    void shouldProcessAnOrder() {
        // Given
        when(orderToProcess.getId()).thenReturn(1L);

        // When
        Thread t = new Thread(task);
        t.run();

        // Then
        verify(orderServiceProcessor).processOrder(orderToProcess);

        int numberEventsExpected = 2;
        assertThat(appender.events).hasSize(numberEventsExpected);
        assertThat(appender.events.get(1).getMessage()).isEqualTo("Processed order with id: 1");
        assertThat(appender.events.get(1).getLevel()).isEqualTo(Level.INFO);

        assertThat(task.getOrderToProcess()).isEqualTo(orderToProcess);
    }

    @Test
    void shouldTreatExceptionIfTheServiceThrowsAnException() {
        // Given
        when(orderToProcess.getId()).thenReturn(1L);
        doThrow(RuntimeException.class).doNothing().when(orderServiceProcessor).processOrder(orderToProcess);

        // When
        Thread t = new Thread(task);
        t.run();

        // Then
        verify(orderServiceProcessor, new Times(2)).processOrder(orderToProcess);

        int numberEventsExpected = 3;
        assertThat(appender.events).hasSize(numberEventsExpected);
        assertThat(appender.events.get(1).getMessage()).contains("Error processing order with id: 1. With error:");
        assertThat(appender.events.get(2).getMessage()).isEqualTo("Processed order with id: 1");
        assertThat(appender.events.get(1).getLevel()).isEqualTo(Level.ERROR);
    }

    @Test
    void shouldRetrySevenTimes() {
        // Given
        when(orderToProcess.getId()).thenReturn(1L);
        doThrow(RuntimeException.class).when(orderServiceProcessor).processOrder(orderToProcess);

        // When
        Thread t = new Thread(task);
        t.run();

        // Then
        verify(orderServiceProcessor, new Times(7)).processOrder(orderToProcess);

        int numberEventsExpected = 8;
        assertThat(appender.events).hasSize(numberEventsExpected);

        List<ILoggingEvent> errorMessages = appender.events.stream().filter(e -> Level.ERROR.equals(e.getLevel())).toList();
        assertThat(errorMessages).hasSize(7);
        assertThat(errorMessages).allMatch(e -> {
            return Level.ERROR.equals(e.getLevel()) && e.getMessage().contains("Error processing order with id: 1. With error:");
        });
        assertThat(appender.events.get(1).getLevel()).isEqualTo(Level.ERROR);
    }


    private static class Appender extends AppenderBase<ILoggingEvent> {
        private final List<ILoggingEvent> events = new ArrayList<>();

        @Override
        protected void append(ILoggingEvent iLoggingEvent) {
            events.add(iLoggingEvent);
        }
    }
}