package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.order.application.commands.CreateB2BOrderCommand;
import com.sweetcrust.team10_bakery.order.application.commands.CreateB2COrderCommand;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderCommandServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderCommandService orderCommandService;

    @Test
    void givenValidData_whenCreateB2COrder_thenOrderIsCreated() {
        // given
        UserId customerId = new UserId();
        Address address = new Address("123 Sourdough Street", "Bread City", "12345", "Baguette Kingdom");
        LocalDateTime futureDate = LocalDateTime.now().plusDays(2);
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                futureDate,
                customerId
        );

        User customer = mock(User.class);
        when(customer.getRole()).thenReturn(UserRole.CUSTOMER);
        when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        orderCommandService.createB2COrder(command);

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void givenNullDeliveryDate_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        Address address = new Address("456 Baguette Boulevard", "Croissant City", "67890", "Pastry Land");
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                null,
                new UserId()
        );

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2COrder(command));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("Delivery date cannot be null", exception.getMessage());
    }

    @Test
    void givenPastDeliveryDate_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        Address address = new Address("789 Croissant Corner", "Muffin Town", "11111", "Doughnut Nation");
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                pastDate,
                new UserId()
        );

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2COrder(command));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("Delivery date cannot be in the past", exception.getMessage());
    }

    @Test
    void givenNonExistentCustomer_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        UserId customerId = new UserId();
        Address address = new Address("321 Pumpernickel Plaza", "Rye City", "22222", "Flour Republic");
        LocalDateTime futureDate = LocalDateTime.now().plusDays(3);
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                futureDate,
                customerId
        );

        when(userRepository.findById(customerId)).thenReturn(Optional.empty());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2COrder(command));

        // then
        assertEquals("customerId", exception.getField());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void givenBakerRole_whenCreateB2COrder_thenExceptionIsThrown() {
        // given
        UserId bakerId = new UserId();
        Address address = new Address("555 Rye Road", "Wheat Village", "33333", "Grain Empire");
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        CreateB2COrderCommand command = new CreateB2COrderCommand(
                address,
                futureDate,
                bakerId
        );

        User baker = mock(User.class);
        when(baker.getRole()).thenReturn(UserRole.BAKER);
        when(userRepository.findById(bakerId)).thenReturn(Optional.of(baker));

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2COrder(command));

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
        LocalDateTime futureDate = LocalDateTime.now().plusDays(5);
        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                futureDate,
                orderingShopId,
                sourceShopId,
                bakerId
        );

        Shop shop = mock(Shop.class);
        Address shopAddress = new Address("42 Flour Power Lane", "Yeast Heights", "44444", "Levain Land");
        when(shop.getAddress()).thenReturn(shopAddress);
        when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(shop));

        User baker = mock(User.class);
        when(baker.getRole()).thenReturn(UserRole.BAKER);
        when(userRepository.findById(bakerId)).thenReturn(Optional.of(baker));

        // when
        orderCommandService.createB2BOrder(command);

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void givenPastDeliveryDate_whenCreateB2BOrder_thenExceptionIsThrown() {
        // given
        LocalDateTime pastDate = LocalDateTime.now().minusHours(2);
        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                pastDate,
                new ShopId(),
                new ShopId(),
                new UserId()
        );

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2BOrder(command));

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
                new UserId()
        );

        when(shopRepository.findById(nonExistentShopId)).thenReturn(Optional.empty());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2BOrder(command));

        // then
        assertEquals("orderingShopId", exception.getField());
        assertEquals("Shop not found", exception.getMessage());
    }

    @Test
    void givenNonExistentUser_whenCreateB2BOrder_thenExceptionIsThrown() {
        // given
        ShopId orderingShopId = new ShopId();
        UserId nonExistentUserId = new UserId();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(4);
        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                futureDate,
                orderingShopId,
                new ShopId(),
                nonExistentUserId
        );

        Shop shop = mock(Shop.class);
        when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(shop));
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2BOrder(command));

        // then
        assertEquals("userId", exception.getField());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void givenCustomerRole_whenCreateB2BOrder_thenExceptionIsThrown() {
        // given
        ShopId orderingShopId = new ShopId();
        UserId customerId = new UserId();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(6);
        CreateB2BOrderCommand command = new CreateB2BOrderCommand(
                futureDate,
                orderingShopId,
                new ShopId(),
                customerId
        );

        Shop shop = mock(Shop.class);
        when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(shop));

        User customer = mock(User.class);
        when(customer.getRole()).thenReturn(UserRole.CUSTOMER);
        when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderCommandService.createB2BOrder(command));

        // then
        assertEquals("userId", exception.getField());
        assertEquals("Only users with baker role can make B2B orders", exception.getMessage());
    }
}