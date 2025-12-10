package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartItemRepository;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.order.application.commands.CancelOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2BOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2COrderCommand;
import com.sweetcrust.team10_bakery.order.application.events.*;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.policies.DiscountCodePolicy;
import com.sweetcrust.team10_bakery.order.domain.policies.DiscountCodeRegistry;
import com.sweetcrust.team10_bakery.order.domain.policies.DiscountPolicy;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderStatus;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandHandler {

    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final DiscountPolicy discountPolicy;
    private final CartRepository cartRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final DiscountCodeRegistry discountCodeRegistry;
    private final CartItemRepository cartItemRepository;

    public OrderCommandHandler(OrderRepository orderRepository, ShopRepository shopRepository,
                               UserRepository userRepository, DiscountPolicy discountPolicy, CartRepository cartRepository,
                               OrderEventPublisher orderEventPublisher, DiscountCodeRegistry discountCodeRegistry, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.discountPolicy = discountPolicy;
        this.cartRepository = cartRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.discountCodeRegistry = discountCodeRegistry;
        this.cartItemRepository = cartItemRepository;
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

        Cart cart = cartRepository.findById(createB2COrderCommand.cartId())
                .orElseThrow(() -> new OrderServiceException("cartId", "Cart not found"));

        List<Shop> shops = shopRepository.findAll();
        if (shops.isEmpty()) {
            throw new OrderServiceException("shopId", "Shop not found");
        }
        Shop shop = shops.get(ThreadLocalRandom.current().nextInt(shops.size()));

        if (user.getRole() != UserRole.CUSTOMER) {
            throw new OrderServiceException("customerId", "Only users with customer role can make B2C orders");
        }

        Order order = Order.createB2C(
                createB2COrderCommand.deliveryAddress(),
                createB2COrderCommand.requestedDeliveryDate(),
                createB2COrderCommand.customerId(),
                createB2COrderCommand.cartId(),
                shop.getShopId());

        BigDecimal subtotal = cartItemRepository.findByCartId(cart.getCartId()).stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String discountCode = null;
        try {
            discountCode = createB2COrderCommand.discountCode();
        } catch (NoSuchMethodError | AbstractMethodError | Exception ignored) {
            // not provided == null
        }

        DiscountCodePolicy policy = discountCodeRegistry.getByCode(discountCode);
        BigDecimal totalAfterDiscount = policy.applyDiscount(subtotal);

        order.setSubtotal(subtotal);
        order.setTotalAfterDiscount(totalAfterDiscount);
        order.setDiscountRate(policy.discountRate());

        order.setDiscountCode(discountCode != null ? discountCode.trim().toUpperCase() : null);

        Order savedOrder = orderRepository.save(order);

        orderEventPublisher.publish(new OrderCreatedEvent(savedOrder, user.getEmail(), shop.getEmail()));

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

        Cart cart = cartRepository.findById(createB2BOrderCommand.cartId())
                .orElseThrow(() -> new OrderServiceException("cartId", "Cart not found"));

        if (user.getRole() != UserRole.BAKER) {
            throw new OrderServiceException("userId", "Only users with baker role can make B2B orders");
        }

        BigDecimal subtotal = cartItemRepository.findByCartId(cart.getCartId()).stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAfterDiscount = discountPolicy.applyDiscount(subtotal);

        Order order = Order.createB2B(
                createB2BOrderCommand.requestedDeliveryDate(),
                createB2BOrderCommand.orderingShopId(),
                createB2BOrderCommand.sourceShopId(),
                createB2BOrderCommand.cartId());

        order.setDeliveryAddress(orderingShop.getAddress());
        order.setSubtotal(subtotal);
        order.setTotalAfterDiscount(totalAfterDiscount);
        order.setDiscountRate(discountPolicy.discountRate());

        Order savedOrder = orderRepository.save(order);
        orderEventPublisher.publish(new OrderCreatedEvent(savedOrder, orderingShop.getEmail(), sourceShop.getEmail()));
        return savedOrder;
    }

    public Order confirmOrder(OrderId orderId, ShopId sourceShopId, UserId userId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceException("orderId", "Order not found"));

        Shop sourceShop = shopRepository.findById(sourceShopId)
                .orElseThrow(() -> new OrderServiceException("sourceShopId", "Source shop not found"));

        if (!order.getSourceShopId().equals(sourceShop.getShopId())) {
            throw new OrderServiceException("sourceShopId", "This shop is not the source shop for the order");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new OrderServiceException("userId", "User not found"));

        if (user.getRole() != UserRole.BAKER && user.getRole() != UserRole.ADMIN) {
            throw new OrderServiceException("userId", "Only baker or admin users can confirm orders");
        }

        order.confirm();
        Order savedOrder = orderRepository.save(order);

        User customer = (order.getCustomerId() != null)
                ? userRepository.findById(order.getCustomerId()).orElse(null)
                : null;

        orderEventPublisher.publish(new OrderConfirmedEvent(
                savedOrder,
                customer != null ? customer.getEmail() : null,
                sourceShop.getEmail()
        ));

        return savedOrder;
    }

    @Scheduled(cron = "0 30 0 * * *")
    public void updateStatusesDaily() {
        LocalDate today = LocalDate.now();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.plusDays(1).atStartOfDay().minusNanos(1);

        LocalDateTime tomorrowStart = today.plusDays(1).atStartOfDay();
        LocalDateTime tomorrowEnd = today.plusDays(2).atStartOfDay().minusNanos(1);

        var toShip = orderRepository.findByStatusAndRequestedDeliveryDateBetween(
                OrderStatus.CONFIRMED, tomorrowStart, tomorrowEnd
        );
        toShip.forEach(order -> {
            try {
                order.markShipped();
            } catch (Exception ignored) {}
        });
        if (!toShip.isEmpty()) {
            orderRepository.saveAll(toShip);

            toShip.forEach(order -> {
                Shop sourceShop = shopRepository.findById(order.getSourceShopId()).orElse(null);
                User customer = (order.getCustomerId() != null)
                        ? userRepository.findById(order.getCustomerId()).orElse(null)
                        : null;
                orderEventPublisher.publish(new OrderShippedEvent(
                        order,
                        customer != null ? customer.getEmail() : null,
                        sourceShop != null ? sourceShop.getEmail() : null
                ));
            });
        }

        var toDeliver = orderRepository.findByStatusAndRequestedDeliveryDateBetween(
                OrderStatus.CONFIRMED, todayStart, todayEnd
        );

        toDeliver.addAll(orderRepository.findByStatusAndRequestedDeliveryDateBetween(
                OrderStatus.SHIPPED, todayStart, todayEnd
        ));

        toDeliver.forEach(order -> {
            try {
                if (order.getStatus() == OrderStatus.CONFIRMED) {
                    order.markShipped();
                }
                order.deliver();
            } catch (Exception ignored) {}
        });
        if (!toDeliver.isEmpty()) {
            orderRepository.saveAll(toDeliver);

            toDeliver.forEach(order -> {
                Shop sourceShop = shopRepository.findById(order.getSourceShopId()).orElse(null);
                User customer = (order.getCustomerId() != null)
                        ? userRepository.findById(order.getCustomerId()).orElse(null)
                        : null;
                orderEventPublisher.publish(new OrderDeliveredEvent(
                        order,
                        customer != null ? customer.getEmail() : null,
                        sourceShop != null ? sourceShop.getEmail() : null
                ));
            });
        }
    }

    public Order cancelOrder(CancelOrderCommand cancelOrderCommand) {
        Order order = orderRepository.findById(cancelOrderCommand.orderId())
                .orElseThrow(() -> new OrderServiceException("orderId", "Order not found"));

        User user = userRepository.findById(cancelOrderCommand.userId())
                .orElseThrow(() -> new OrderServiceException("userId", "User not found"));

        if (order.getOrderingShopId() != null) {
            if (user.getRole() != UserRole.BAKER && user.getRole() != UserRole.ADMIN) {
                throw new OrderServiceException("userId", "Only users with baker or admin role can cancel B2B orders");
            }

            Shop orderingShop = shopRepository.findById(order.getOrderingShopId())
                    .orElseThrow(() -> new OrderServiceException("orderingShopId", "Shop not found"));
            Shop sourceShop = shopRepository.findById(order.getSourceShopId())
                    .orElseThrow(() -> new OrderServiceException("sourceShopId", "Shop not found"));

            order.cancel();

            Order savedOrder = orderRepository.save(order);
            orderEventPublisher.publish(new OrderCancelledEvent(
                    savedOrder,
                    orderingShop.getEmail(),
                    sourceShop.getEmail()
            ));

            return savedOrder;
        } else {
            if (!order.getCustomerId().equals(cancelOrderCommand.userId()) && user.getRole() != UserRole.ADMIN) {
                throw new OrderServiceException("userId", "Only the customer who placed the order can cancel it");
            }

            order.cancel();

            Order savedOrder = orderRepository.save(order);

            orderEventPublisher.publish(new OrderCancelledEvent(
                    savedOrder,
                    user.getEmail(),
                    null
            ));

            return savedOrder;
        }
    }
}

