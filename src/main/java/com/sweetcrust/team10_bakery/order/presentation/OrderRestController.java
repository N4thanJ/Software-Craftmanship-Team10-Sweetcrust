package com.sweetcrust.team10_bakery.order.presentation;

import com.sweetcrust.team10_bakery.order.application.OrderCommandHandler;
import com.sweetcrust.team10_bakery.order.application.OrderQueryHandler;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2BOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2COrderCommand;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@Tag(name = "Order Management", description = "Endpoints related to order status fetching en orderItems")
public class OrderRestController {

    private final OrderCommandHandler orderCommandHandler;
    private final OrderQueryHandler orderQueryHandler;

    public OrderRestController(OrderCommandHandler orderCommandHandler, OrderQueryHandler orderQueryHandler) {
        this.orderCommandHandler = orderCommandHandler;
        this.orderQueryHandler = orderQueryHandler;
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        List<Order> orders = orderQueryHandler.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order order = orderQueryHandler.getOrderById(new OrderId(orderId));
        return ResponseEntity.ok(order);
    }

    @PostMapping("/B2C")
    public ResponseEntity<Order> createB2COrder(@RequestBody CreateB2COrderCommand createB2COrderCommand) {
        Order order = orderCommandHandler.createB2COrder(createB2COrderCommand);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/B2B")
    public ResponseEntity<Order> createB2BOrder(@RequestBody CreateB2BOrderCommand createB2BOrderCommand) {
        Order order = orderCommandHandler.createB2BOrder(createB2BOrderCommand);
        return ResponseEntity.ok(order);
    }


}
