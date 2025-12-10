package com.sweetcrust.team10_bakery.order.presentation;

import com.sweetcrust.team10_bakery.order.application.OrderCommandHandler;
import com.sweetcrust.team10_bakery.order.application.OrderQueryHandler;
import com.sweetcrust.team10_bakery.order.application.commands.CancelOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2BOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2COrderCommand;
import com.sweetcrust.team10_bakery.order.application.queries.GetAllOrdersByUserIdQuery;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/{orderType}/{userId}")
    public ResponseEntity<Iterable<Order>> getAllOrdersForCustomer(@PathVariable OrderType orderType, @PathVariable UUID userId) {
        GetAllOrdersByUserIdQuery query = new GetAllOrdersByUserIdQuery(orderType, new UserId(userId));
        List<Order> orders = orderQueryHandler.getAllOrdersForCustomer(query);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/shop/{sourceShopId}")
    public ResponseEntity<Iterable<Order>> getOrdersBySourceShopId(@PathVariable UUID sourceShopId) {
        List<Order> orders = orderQueryHandler.getOrdersBySourceShopId(new ShopId(sourceShopId));
        return ResponseEntity.ok(orders);
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

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<Order> confirmOrder(
            @PathVariable("orderId") UUID orderIdStr,
            @RequestParam("sourceShopId") UUID sourceShopIdStr,
            @RequestParam("userId") UUID userIdStr
    ) {
        OrderId orderId = new OrderId(orderIdStr);
        ShopId sourceShopId = new ShopId(sourceShopIdStr);
        UserId userId = new UserId(userIdStr);

        Order confirmed = orderCommandHandler.confirmOrder(orderId, sourceShopId, userId);
        return ResponseEntity.ok(confirmed);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Order> cancelOrder(@RequestBody CancelOrderCommand cancelOrderCommand) {
        Order order = orderCommandHandler.cancelOrder(cancelOrderCommand);
        return ResponseEntity.ok(order);
    }
}
