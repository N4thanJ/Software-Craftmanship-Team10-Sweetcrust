package com.sweetcrust.team10_bakery.order.presentation;

import com.sweetcrust.team10_bakery.order.application.OrderCommandService;
import com.sweetcrust.team10_bakery.order.application.OrderQueryService;
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

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    public OrderRestController(OrderCommandService orderCommandService, OrderQueryService orderQueryService) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        List<Order> orders = orderQueryService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order order = orderQueryService.getOrderById(new OrderId(orderId));
        return ResponseEntity.ok(order);
    }

    @PostMapping("/B2C")
    public ResponseEntity<Order> createB2COrder(@RequestBody CreateB2COrderCommand createB2COrderCommand) {
        Order order = orderCommandService.createB2COrder(createB2COrderCommand);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/B2B")
    public ResponseEntity<Order> createB2BOrder(@RequestBody CreateB2BOrderCommand createB2BOrderCommand) {
        Order order = orderCommandService.createB2BOrder(createB2BOrderCommand);
        return ResponseEntity.ok(order);
    }


}
