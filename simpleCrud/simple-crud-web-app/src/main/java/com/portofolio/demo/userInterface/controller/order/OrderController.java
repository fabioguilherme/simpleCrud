package com.portofolio.demo.userInterface.controller.order;

import com.portofolio.demo.aplication.order.OrderApplicationService;
import com.portofolio.demo.aplication.order.model.CreateOrderRequest;
import com.portofolio.demo.aplication.order.model.OrderDto;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.models.json.order.ChangeOrderStatusRequestJson;
import com.portofolio.demo.models.json.order.CreateOrderRequestJson;
import com.portofolio.demo.models.json.order.Order;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import com.portofolio.demo.userInterface.controller.MainController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/order")
public class OrderController extends MainController {

    private final OrderApplicationService service;

    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderApplicationService service) {
        this.service = service;
    }

    @GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private Order getOrder(@PathVariable("orderId") Long orderId) {

        Optional<OrderDto> optionalDto = service.getById(orderId);

        if (optionalDto.isEmpty()) {
            log.info("Order with id " + orderId + " not found");
            throw new ResourceNotFoundException("Order with id " + orderId + " not found", null);
        }

        log.info("Order found with id " + orderId);

        return fromDtoToJson(optionalDto.get());
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrderById(@PathVariable("orderId") Long orderId) {
        service.deleteById(orderId);
        log.info("Order deleted with id " + orderId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(@RequestParam(name = "userId", required = false) Long userId,
                                    @RequestParam(name = "status", required = false) OrderStatus status) {

        List<OrderDto> orderDtos = service.getAll(userId, status);

        log.info("Search ended.");

        return orderDtos.stream().map(this::fromDtoToJson).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@Valid @RequestBody CreateOrderRequestJson request) {

        CreateOrderRequest serviceRequest = CreateOrderRequest.Builder.with().itemId(request.getItemId()).userId(request.getUserId()).quantity(request.getQuantity()).build();

        OrderDto dto = service.save(serviceRequest);

        log.info("Created order.");

        return fromDtoToJson(dto);
    }

    @PatchMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private void changeStatus(@PathVariable("orderId") Long orderId, @Valid @RequestBody ChangeOrderStatusRequestJson request) {

        service.changeStatus(orderId, OrderStatus.valueOf(request.getStatus()));

        log.info("Change the status of the order with id: " + orderId);
    }


    private Order fromDtoToJson(OrderDto orderDto) {

        Order order = new Order();

        order.setId(orderDto.getId());
        order.setItemName(orderDto.getItemName());
        order.setUserEmail(orderDto.getUserEmail());
        order.setUserName(orderDto.getUserName());
        order.setQuantity(orderDto.getQuantity());
        order.setCreationDate(orderDto.getCreationDate());
        order.setUri(orderDto.getUri());
        order.setStatus(orderDto.getStatus().name());

        return order;
    }
}
