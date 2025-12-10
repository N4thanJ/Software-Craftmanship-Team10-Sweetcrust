package com.sweetcrust.team10_bakery.order.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2BOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2COrderCommand;
import com.sweetcrust.team10_bakery.order.application.events.OrderCreatedEvent;
import com.sweetcrust.team10_bakery.order.application.events.OrderEventPublisher;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.policies.DiscountCodePolicy;
import com.sweetcrust.team10_bakery.order.domain.policies.DiscountCodeRegistry;
import com.sweetcrust.team10_bakery.order.domain.policies.DiscountPolicy;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderCommandHandlerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private DiscountCodePolicy discountCodePolicy;

    @Mock
    private DiscountPolicy discountPolicy;

    @Mock
    private DiscountCodeRegistry discountCodeRegistry;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    @InjectMocks
    private OrderCommandHandler orderCommandHandler;

    @Test
    void givenValidData_whenCreateB2COrder_thenOrderIsCreated() {
        // given
        UserId customerId = new UserId();
        CartId cartId = new CartId();

        Address address = Address.builder()
                .setStreet("123 Sourdough Street")
                .setCity("Bread City")
                .setPostalCode("12345")
                .setCountry("Baguette Kingdom")
                .build();

        LocalDateTime futureDate = LocalDateTime.now().plusDays(2);
        String discountCode = "SWEETCRUST20";

        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                futureDate,
                customerId,
                cartId,
                discountCode
        );

        User customer = mock(User.class);
        Cart cart = mock(Cart.class);
        Shop shop = mock(Shop.class);
        DiscountCodePolicy policy = mock(DiscountCodePolicy.class);

        when(customer.getRole()).thenReturn(UserRole.CUSTOMER);
        when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        when(shopRepository.findAll()).thenReturn(List.of(shop));
        when(shop.getShopId()).thenReturn(new ShopId());

        when(cart.getCartItems()).thenReturn(List.of());

        when(discountCodeRegistry.getByCode(discountCode)).thenReturn(policy);
        when(policy.applyDiscount(any())).thenReturn(BigDecimal.ZERO);
        when(policy.discountRate()).thenReturn(BigDecimal.ZERO);

        // when
        orderCommandHandler.createB2COrder(command);

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderEventPublisher, times(1))
                .publish(any(OrderCreatedEvent.class));

    }


    @Test
    void givenNullDeliveryDate_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        Address address = Address.builder()
                .setStreet("456 Baguette Boulevard")
                .setCity("Croissant City")
                .setPostalCode("67890")
                .setCountry("Pastry Land")
                .build();
        String discountCode = "SWEETCRUST20";
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                null,
                new UserId(),
                new CartId(),
                discountCode);

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2COrder(command));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("Delivery date cannot be null", exception.getMessage());
    }

    @Test
    void givenPastDeliveryDate_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        Address address = Address.builder()
                .setStreet("789 Croissant Corner")
                .setCity("Muffin Town")
                .setPostalCode("11111")
                .setCountry("Doughnut Nation")
                .build();
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        String discountCode = "SWEETCRUST20";
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                pastDate,
                new UserId(),
                new CartId(),
                discountCode);

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2COrder(command));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("Delivery date cannot be in the past", exception.getMessage());
    }

    @Test
    void givenNonExistentCustomer_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        UserId customerId = new UserId();
        Address address = Address.builder()
                .setStreet("321 Pumpernickel Plaza")
                .setCity("Rye City")
                .setPostalCode("22222")
                .setCountry("Flour Republic")
                .build();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(3);
        String discountCode = "SWEETCRUST20";
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                futureDate,
                customerId,
                new CartId(),
                discountCode
        );

        when(userRepository.findById(customerId)).thenReturn(Optional.empty());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2COrder(command));

        // then
        assertEquals("customerId", exception.getField());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void givenBakerRole_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        UserId bakerId = new UserId();
        CartId cartId = new CartId();
        Address address = Address.builder()
                .setStreet("555 Rye Road")
                .setCity("Wheat Village")
                .setPostalCode("33333")
                .setCountry("Grain Empire")
                .build();

        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        String discountCode = "SWEETCRUST20";

        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                futureDate,
                bakerId,
                cartId,
                discountCode
        );

        User baker = mock(User.class);
        when(baker.getRole()).thenReturn(UserRole.BAKER);
        when(userRepository.findById(bakerId)).thenReturn(Optional.of(baker));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(mock(Cart.class)));
        when(shopRepository.findAll()).thenReturn(List.of(mock(Shop.class)));

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2COrder(command));

        // then
        assertEquals("customerId", exception.getField());
        assertEquals("Only users with customer role can make B2C orders", exception.getMessage());
    }


    @Test
    void givenValidData_whenCreateB2BOrder_thenOrderIsCreated() {
        // given
        ShopId orderingShopId = new ShopId();
        ShopId sourceShopId = new ShopId();
        UserId bakerId = new UserId();
        CartId cartId = new CartId();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(5);

        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                futureDate,
                orderingShopId,
                sourceShopId,
                bakerId,
                cartId
        );

        Shop orderingShop = mock(Shop.class);
        Address shopAddress = Address.builder()
                .setStreet("42 Flour Power Lane")
                .setCity("Yeast Heights")
                .setPostalCode("44444")
                .setCountry("Levain Land")
                .build();
        when(orderingShop.getAddress()).thenReturn(shopAddress);
        when(orderingShop.getEmail()).thenReturn("ordering@shop.com");
        when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(orderingShop));

        Shop sourceShop = mock(Shop.class);
        when(sourceShop.getEmail()).thenReturn("source@shop.com");
        when(shopRepository.findById(sourceShopId)).thenReturn(Optional.of(sourceShop));

        User baker = mock(User.class);
        when(baker.getRole()).thenReturn(UserRole.BAKER);
        when(userRepository.findById(bakerId)).thenReturn(Optional.of(baker));

        Cart cart = mock(Cart.class);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cart.getCartItems()).thenReturn(List.of());

        lenient().when(discountPolicy.applyDiscount(any())).thenReturn(BigDecimal.ZERO);
        lenient().when(discountPolicy.discountRate()).thenReturn(BigDecimal.ZERO);

        // when
        orderCommandHandler.createB2BOrder(command);

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderEventPublisher, times(1))
                .publish(any(OrderCreatedEvent.class));
    }


    @Test
    void givenPastDeliveryDate_whenCreateB2BOrder_thenExceptionIsThrown() {
        // given
        LocalDateTime pastDate = LocalDateTime.now().minusHours(2);
        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                pastDate,
                new ShopId(),
                new ShopId(),
                new UserId(),
                new CartId());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2BOrder(command));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("Delivery date cannot be in the past", exception.getMessage());
    }

    @Test
    void givenNonExistentShop_whenCreateB2BOrder_thenExceptionIsThrown() {
        // given
        ShopId nonExistentShopId = new ShopId();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(7);
        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                futureDate,
                nonExistentShopId,
                new ShopId(),
                new UserId(),
                new CartId());

        when(shopRepository.findById(nonExistentShopId)).thenReturn(Optional.empty());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2BOrder(command));

        // then
        assertEquals("orderingShopId", exception.getField());
        assertEquals("Shop not found", exception.getMessage());
    }

    @Test
    void givenNonExistentUser_whenCreateB2BOrder_thenExceptionIsThrown() {
        // given
        ShopId orderingShopId = new ShopId();
        ShopId sourceShopId = new ShopId();
        UserId nonExistentUserId = new UserId();
        CartId cartId = new CartId();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(4);

        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                futureDate,
                orderingShopId,
                sourceShopId,
                nonExistentUserId,
                cartId
        );

        Shop orderingShop = mock(Shop.class);
        when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(orderingShop));

        Shop sourceShop = mock(Shop.class);
        when(shopRepository.findById(sourceShopId)).thenReturn(Optional.of(sourceShop));

        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2BOrder(command));

        // then
        assertEquals("userId", exception.getField());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void givenCustomerRole_whenCreateB2BOrder_thenExceptionIsThrown() {
        // given
        ShopId orderingShopId = new ShopId();
        ShopId sourceShopId = new ShopId();
        UserId customerId = new UserId();
        CartId cartId = new CartId();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(6);

        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                futureDate,
                orderingShopId,
                sourceShopId,
                customerId,
                cartId
        );

        Shop orderingShop = mock(Shop.class);
        Shop sourceShop = mock(Shop.class);
        when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(orderingShop));
        when(shopRepository.findById(sourceShopId)).thenReturn(Optional.of(sourceShop));

        User customer = mock(User.class);
        when(customer.getRole()).thenReturn(UserRole.CUSTOMER);
        when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Cart cart = mock(Cart.class);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class,
                () -> orderCommandHandler.createB2BOrder(command));

        // then
        assertEquals("userId", exception.getField());
        assertEquals("Only users with baker role can make B2B orders", exception.getMessage());
    }
}