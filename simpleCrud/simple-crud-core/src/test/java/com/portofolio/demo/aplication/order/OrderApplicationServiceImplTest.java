package com.portofolio.demo.aplication.order;

import com.portofolio.demo.aplication.order.model.CreateOrderRequest;
import com.portofolio.demo.aplication.order.model.OrderDto;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemFixture;
import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderDomainService;
import com.portofolio.demo.domain.order.OrderFixture;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserFixture;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepositoryService;
import com.portofolio.demo.infrastructure.persistence.order.OrderRepositoryService;
import com.portofolio.demo.infrastructure.persistence.user.UserRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderApplicationServiceImplTest {

    private final String URI_BASE = "fake-uribase";
    private final String ORDER_URI_BASE = "/order/";

    @Mock
    private ItemRepositoryService itemRepositoryService;
    @Mock
    private OrderDomainService orderDomainService;
    @Mock
    private OrderRepositoryService orderRepositoryService;
    @Mock
    private UserRepositoryService userRepositoryService;
    @Mock
    private OrderApplicationServiceImpl applicationService;

    @BeforeEach
    public void setUp() throws Exception {
        this.applicationService = new OrderApplicationServiceImpl(userRepositoryService, itemRepositoryService, orderDomainService, orderRepositoryService, URI_BASE);
    }

    @Test
    public void canSaveAnOrder() throws Exception {
        // Given
        Long itemId = 1L;
        Long userId = 1L;
        int quantity = 5;
        CreateOrderRequest request = CreateOrderRequest.Builder.with().itemId(itemId).userId(userId).quantity(quantity).build();
        Item itemReturned = ItemFixture.getItem();
        User userReturned = UserFixture.getUser();
        Order orderToPersist = OrderFixture.getNewOrderWithUserAndItem(userReturned, itemReturned, quantity);
        Order orderPersisted = OrderFixture.getOrderWithUserAndItem(userReturned, itemReturned, quantity);


        Mockito.when(itemRepositoryService.getById(itemId)).thenReturn(Optional.of(itemReturned));
        Mockito.when(userRepositoryService.getById(userId)).thenReturn(Optional.of(userReturned));
        Mockito.when(orderDomainService.createOrder(itemReturned, userReturned, quantity)).thenReturn(orderToPersist);
        Mockito.when(orderRepositoryService.save(orderToPersist)).thenReturn(orderPersisted);

        // When
        OrderDto response = applicationService.save(request);

        // Then
        assertThat(response).isNotNull();

        assertThat(response.getItemName()).isEqualTo(orderPersisted.getItem().getName());
        assertThat(response.getUserName()).isEqualTo(userReturned.getName());
        assertThat(response.getUserEmail()).isEqualTo(userReturned.getEmail());
        assertThat(response.getId()).isEqualTo(orderPersisted.getId());
        assertThat(response.getQuantity()).isEqualTo(orderPersisted.getQuantity());
        assertThat(response.getCreationDate()).isNotNull();
        assertThat(response.getUri()).isEqualTo(URI_BASE + ORDER_URI_BASE + orderPersisted.getId());

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);

        verify(itemRepositoryService).getById(itemId);
        verify(userRepositoryService).getById(userId);
        verify(orderDomainService).createOrder(itemReturned, userReturned, quantity);
        verify(orderRepositoryService).save(argumentCaptor.capture());

        Order orderCaptured = argumentCaptor.getValue();

        assertThat(orderCaptured).isNotNull();
    }

    @Test
    public void canDeleteById() {
        // Given
        Long id = 1L;

        // When
        applicationService.deleteById(id);

        // Then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(orderRepositoryService).deleteById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void canChangeStatusAndSave() throws Exception {
        // Given
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.DONE;
        int quantity = 5;

        Item itemReturned = ItemFixture.getItem();
        User userReturned = UserFixture.getUser();

        Order orderFound = OrderFixture.getOrderWithUserAndItem(userReturned, itemReturned, quantity);


        Mockito.when(orderRepositoryService.getById(orderId)).thenReturn(Optional.of(orderFound));
        Mockito.when(orderRepositoryService.save(orderFound)).thenReturn(orderFound);

        // When
        applicationService.changeStatus(orderId, newStatus);

        // Then
        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);

        verify(orderRepositoryService).save(argumentCaptor.capture());
        verify(orderDomainService).updateOrderStatus(orderFound, newStatus);
        verify(orderRepositoryService).save(argumentCaptor.capture());
    }

    @Test
    public void canGetOrderById() throws Exception {
        // Given
        Long id = 1L;
        Order orderFound = OrderFixture.getOrder();

        Mockito.when(orderRepositoryService.getById(id)).thenReturn(Optional.of(orderFound));

        // When
        Optional<OrderDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isPresent();

        OrderDto dto = response.get();

        assertThat(response).isNotNull();

        assertThat(dto.getItemName()).isEqualTo(orderFound.getItem().getName());
        assertThat(dto.getUserName()).isEqualTo(orderFound.getUser().getName());
        assertThat(dto.getUserEmail()).isEqualTo(orderFound.getUser().getEmail());
        assertThat(dto.getId()).isEqualTo(orderFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(orderFound.getQuantity());
        assertThat(dto.getCreationDate()).isNotNull();
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ORDER_URI_BASE + orderFound.getId());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(orderRepositoryService).getById(argumentCaptor.capture());

        Long idCaptured = argumentCaptor.getValue();

        assertThat(idCaptured).isNotNull();

        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    public void shouldReturnOptionalEmptyIfOrderDoNotExists() {
        // Given
        Long id = 1L;

        Mockito.when(orderRepositoryService.getById(id)).thenReturn(Optional.empty());

        // When
        Optional<OrderDto> response = applicationService.getById(id);

        // Then
        assertThat(response).isEmpty();
    }

    @Test
    public void canGetAll() throws Exception {
        // Given
        Order orderFound = OrderFixture.getOrder();

        Mockito.when(orderRepositoryService.getAll(null, null)).thenReturn(Collections.singletonList(orderFound));

        // When
        List<OrderDto> response = applicationService.getAll(null, null);

        // Then
        assertThat(response).hasSize(1);

        OrderDto dto = response.get(0);

        assertThat(dto.getItemName()).isEqualTo(orderFound.getItem().getName());
        assertThat(dto.getUserName()).isEqualTo(orderFound.getUser().getName());
        assertThat(dto.getUserEmail()).isEqualTo(orderFound.getUser().getEmail());
        assertThat(dto.getId()).isEqualTo(orderFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(orderFound.getQuantity());
        assertThat(dto.getCreationDate()).isNotNull();
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ORDER_URI_BASE + orderFound.getId());
    }

    @Test
    public void canGetAllByUserIdAndOrderStatus() throws Exception {
        // Given
        Order orderFound = OrderFixture.getOrder();
        OrderStatus status = orderFound.getStatus();
        Long userId = orderFound.getUser().getId();

        Mockito.when(orderRepositoryService.getAll(userId, status)).thenReturn(Collections.singletonList(orderFound));

        // When
        List<OrderDto> response = applicationService.getAll(userId, status);

        // Then
        assertThat(response).hasSize(1);

        OrderDto dto = response.get(0);

        assertThat(dto.getItemName()).isEqualTo(orderFound.getItem().getName());
        assertThat(dto.getUserName()).isEqualTo(orderFound.getUser().getName());
        assertThat(dto.getUserEmail()).isEqualTo(orderFound.getUser().getEmail());
        assertThat(dto.getId()).isEqualTo(orderFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(orderFound.getQuantity());
        assertThat(dto.getCreationDate()).isNotNull();
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ORDER_URI_BASE + orderFound.getId());

        verify(orderRepositoryService).getAll(userId, status);
    }

    @Test
    public void canGetAllByUserId() throws Exception {
        // Given
        Order orderFound = OrderFixture.getOrder();
        Long userId = orderFound.getUser().getId();


        Mockito.when(orderRepositoryService.getAll(userId, null)).thenReturn(Collections.singletonList(orderFound));

        // When
        List<OrderDto> response = applicationService.getAll(userId, null);

        // Then
        assertThat(response).hasSize(1);

        OrderDto dto = response.get(0);

        assertThat(dto.getItemName()).isEqualTo(orderFound.getItem().getName());
        assertThat(dto.getUserName()).isEqualTo(orderFound.getUser().getName());
        assertThat(dto.getUserEmail()).isEqualTo(orderFound.getUser().getEmail());
        assertThat(dto.getId()).isEqualTo(orderFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(orderFound.getQuantity());
        assertThat(dto.getCreationDate()).isNotNull();
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ORDER_URI_BASE + orderFound.getId());

        verify(orderRepositoryService).getAll(userId, null);
    }

    @Test
    public void canGetAllByStatus() throws Exception {
        // Given
        Order orderFound = OrderFixture.getOrder();
        OrderStatus status = orderFound.getStatus();

        Mockito.when(orderRepositoryService.getAll(null, status)).thenReturn(Collections.singletonList(orderFound));

        // When
        List<OrderDto> response = applicationService.getAll(null, status);

        // Then
        assertThat(response).hasSize(1);

        OrderDto dto = response.get(0);

        assertThat(dto.getItemName()).isEqualTo(orderFound.getItem().getName());
        assertThat(dto.getUserName()).isEqualTo(orderFound.getUser().getName());
        assertThat(dto.getUserEmail()).isEqualTo(orderFound.getUser().getEmail());
        assertThat(dto.getId()).isEqualTo(orderFound.getId());
        assertThat(dto.getQuantity()).isEqualTo(orderFound.getQuantity());
        assertThat(dto.getCreationDate()).isNotNull();
        assertThat(dto.getUri()).isEqualTo(URI_BASE + ORDER_URI_BASE + orderFound.getId());

        verify(orderRepositoryService).getAll(null, status);
    }
}