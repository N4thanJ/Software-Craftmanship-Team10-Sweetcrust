package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2BOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2COrderCommand;
import com.sweetcrust.team10_bakery.order.application.events.OrderCreatedEvent;
import com.sweetcrust.team10_bakery.order.application.events.OrderEventPublisher;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.policies.DiscountPolicy;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderCommandHandler {

    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final DiscountPolicy b2bDiscountPolicy;
    private final CartRepository cartRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderCommandHandler(OrderRepository orderRepository, ShopRepository shopRepository,
                               UserRepository userRepository, DiscountPolicy b2bDiscountPolicy, CartRepository cartRepository,
                               OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.b2bDiscountPolicy = b2bDiscountPolicy;
        this.cartRepository = cartRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    public Order createB2COrder(CreateB2COrderCommand createB2COrderCommand) {
        if (createB2COrderCommand.requestedDeliveryDate() == null) {
            throw new OrderServiceException("requestedDeliveryDate", "Delivery date cannot be null");
        }

        if (createB2COrderCommand.requestedDeliveryDate().isBefore(LocalDateTime.now())) {
            throw new OrderServiceException("requestedDeliveryDate", "Delivery date cannot be in the past");
        }

        User user = userRepository.findById(createB2COrderCommand.customerId())
                .orElseThrow(() -> new OrderServiceException("customerId", "User not found"));

        if (user.getRole() != UserRole.CUSTOMER) {
            throw new OrderServiceException("customerId", "Only users with customer role can make B2C orders");
        }

        Order order = Order.createB2C(
                createB2COrderCommand.deliveryAddress(),
                createB2COrderCommand.requestedDeliveryDate(),
                createB2COrderCommand.customerId(),
                createB2COrderCommand.cartId());

        Order savedOrder = orderRepository.save(order);

        orderEventPublisher.publish(new OrderCreatedEvent(savedOrder, user.getEmail(), null));

        return savedOrder;
    }

    public Order createB2BOrder(CreateB2BOrderCommand createB2BOrderCommand) {
        if (createB2BOrderCommand.requestedDeliveryDate().isBefore(LocalDateTime.now())) {
            throw new OrderServiceException("requestedDeliveryDate", "Delivery date cannot be in the past");
        }

        Shop orderingShop = shopRepository.findById(createB2BOrderCommand.orderingShopId())
                .orElseThrow(() -> new OrderServiceException("orderingShopId", "Shop not found"));

        Shop sourceShop = shopRepository.findById(createB2BOrderCommand.sourceShopId())
                .orElseThrow(() -> new OrderServiceException("sourceShopId", "Shop not found"));

        User user = userRepository.findById(createB2BOrderCommand.userId())
                .orElseThrow(() -> new OrderServiceException("userId", "User not found"));

        if (user.getRole() != UserRole.BAKER) {
            throw new OrderServiceException("userId", "Only users with baker role can make B2B orders");
        }

        var cart = cartRepository.findById(createB2BOrderCommand.cartId())
                .orElseThrow(() -> new OrderServiceException("cartId", "Cart not found"));

        BigDecimal subtotal = cart.getCartItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAfterDiscount = b2bDiscountPolicy.applyDiscount(subtotal);

        Order order = Order.createB2B(
                createB2BOrderCommand.requestedDeliveryDate(),
                createB2BOrderCommand.orderingShopId(),
                createB2BOrderCommand.sourceShopId(),
                createB2BOrderCommand.cartId());

        order.setDeliveryAddress(orderingShop.getAddress());
        order.setSubtotal(subtotal);
        order.setTotalAfterDiscount(totalAfterDiscount);
        order.setDiscountRate(b2bDiscountPolicy.discountRate());

        Order savedOrder = orderRepository.save(order);
        orderEventPublisher.publish(new OrderCreatedEvent(savedOrder, orderingShop.getEmail(), sourceShop.getEmail()));
        return savedOrder;
    }
}
