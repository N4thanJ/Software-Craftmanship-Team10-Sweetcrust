package com.sweetcrust.team10_bakery.order.presentation;

import com.sweetcrust.team10_bakery.order.application.OrderCommandService;
import com.sweetcrust.team10_bakery.order.application.OrderQueryService;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
