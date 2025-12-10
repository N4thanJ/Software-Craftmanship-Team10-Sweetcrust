package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.order.application.queries.GetAllOrdersByUserIdQuery;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderQueryHandler {

    private final OrderRepository orderRepository;

    public OrderQueryHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(OrderId orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceException("order", "order not found"));
    }

    public List<Order> getOrdersBySourceShopId(ShopId sourceShopId) {
        List<Order> orders = orderRepository.findBySourceShopId(sourceShopId);
        if (orders == null || orders.isEmpty()) {
            throw new OrderServiceException("order", "orders not found for the given source shop id " + sourceShopId);
        }
        return orders;
    }

    public List<Order> getAllOrdersForCustomer(GetAllOrdersByUserIdQuery query) {
        List<Order> orders = orderRepository.findByCustomerId(query.userId());

        if (orders == null || orders.isEmpty()) {
            throw new OrderServiceException("order", "orders not found for the given customer id " + query.userId());
        }

        return orders.stream().filter(o -> o.getOrderType() == query.orderType()).toList();
    }
}
