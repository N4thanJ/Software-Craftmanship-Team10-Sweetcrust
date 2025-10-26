package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.order.application.commands.CreateB2BOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2COrderCommand;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;

    public OrderCommandService(OrderRepository orderRepository, ShopRepository shopRepository) {
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
    }

    public Order createB2COrder(CreateB2COrderCommand createB2COrderCommand) {
        if (createB2COrderCommand.requestedDeliveryDate() == null) {
            throw new OrderServiceException("requestedDeliveryDate", "Delivery date cannot be null");
        }

        if (createB2COrderCommand.requestedDeliveryDate().isBefore(LocalDateTime.now())) {
            throw new OrderServiceException("requestedDeliveryDate", "Delivery date cannot be in the past");
        }

        Order order = Order.createB2C(
                createB2COrderCommand.orderType(),
                createB2COrderCommand.deliveryAddress(),
                createB2COrderCommand.requestedDeliveryDate(),
                createB2COrderCommand.customerId()
        );

        return orderRepository.save(order);
    }

    public Order createB2BOrder(CreateB2BOrderCommand createB2BOrderCommand) {
        if (createB2BOrderCommand.requestedDeliveryDate().isBefore(LocalDateTime.now())) {
            throw new OrderServiceException("requestedDeliveryDate", "Delivery date cannot be in the past");
        }

        Shop orderingShop = shopRepository.findById(createB2BOrderCommand.orderingShopId())
                .orElseThrow(() -> new OrderServiceException("orderingShopId", "Shop id cannot be null"));

        Order order = Order.createB2B(
                createB2BOrderCommand.orderType(),
                createB2BOrderCommand.requestedDeliveryDate(),
                createB2BOrderCommand.orderingShopId(),
                createB2BOrderCommand.sourceShopId()
        );

        order.setDeliveryAddress(orderingShop.getAddress());

        return orderRepository.save(order);
    }
}
