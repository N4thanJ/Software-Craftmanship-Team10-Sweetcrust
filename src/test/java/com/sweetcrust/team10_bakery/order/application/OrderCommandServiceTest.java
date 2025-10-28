package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
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
                Address address = Address.builder()
                                .setStreet("123 Sourdough Street")
                                .setCity("Bread City")
                                .setPostalCode("12345")
                                .setCountry("Baguette Kingdom")
                                .build();
                LocalDateTime futureDate = LocalDateTime.now().plusDays(2);
                CreateB2COrderCommand command = new CreateB2COrderCommand(
                                address,
                                futureDate,
                                customerId,
                                new CartId());

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
                Address address = Address.builder()
                                .setStreet("456 Baguette Boulevard")
                                .setCity("Croissant City")
                                .setPostalCode("67890")
                                .setCountry("Pastry Land")
                                .build();
                CreateB2COrderCommand command = new CreateB2COrderCommand(
                                address,
                                null,
                                new UserId(),
                                new CartId());

                // when
                OrderServiceException exception = assertThrows(OrderServiceException.class,
                                () -> orderCommandService.createB2COrder(command));

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
                CreateB2COrderCommand command = new CreateB2COrderCommand(
                                address,
                                pastDate,
                                new UserId(),
                                new CartId());

                // when
                OrderServiceException exception = assertThrows(OrderServiceException.class,
                                () -> orderCommandService.createB2COrder(command));

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
                CreateB2COrderCommand command = new CreateB2COrderCommand(
                                address,
                                futureDate,
                                customerId,
                                new CartId());

                when(userRepository.findById(customerId)).thenReturn(Optional.empty());

                // when
                OrderServiceException exception = assertThrows(OrderServiceException.class,
                                () -> orderCommandService.createB2COrder(command));

                // then
                assertEquals("customerId", exception.getField());
                assertEquals("User not found", exception.getMessage());
        }

        @Test
        void givenBakerRole_whenCreateB2COrder_thenExceptionIsThrown() {
                // given
                UserId bakerId = new UserId();
                Address address = Address.builder()
                                .setStreet("555 Rye Road")
                                .setCity("Wheat Village")
                                .setPostalCode("33333")
                                .setCountry("Grain Empire")
                                .build();
                LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
                CreateB2COrderCommand command = new CreateB2COrderCommand(
                                address,
                                futureDate,
                                bakerId,
                                new CartId());

                User baker = mock(User.class);
                when(baker.getRole()).thenReturn(UserRole.BAKER);
                when(userRepository.findById(bakerId)).thenReturn(Optional.of(baker));

                // when
                OrderServiceException exception = assertThrows(OrderServiceException.class,
                                () -> orderCommandService.createB2COrder(command));

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
                                bakerId,
                                new CartId());

                Shop shop = mock(Shop.class);
                Address shopAddress = Address.builder()
                                .setStreet("42 Flour Power Lane")
                                .setCity("Yeast Heights")
                                .setPostalCode("44444")
                                .setCountry("Levain Land")
                                .build();
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
                                new UserId(),
                                new CartId());

                // when
                OrderServiceException exception = assertThrows(OrderServiceException.class,
                                () -> orderCommandService.createB2BOrder(command));

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
                                () -> orderCommandService.createB2BOrder(command));

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
                                nonExistentUserId,
                                new CartId());

                Shop shop = mock(Shop.class);
                when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(shop));
                when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

                // when
                OrderServiceException exception = assertThrows(OrderServiceException.class,
                                () -> orderCommandService.createB2BOrder(command));

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
                                customerId,
                                new CartId());

                Shop shop = mock(Shop.class);
                when(shopRepository.findById(orderingShopId)).thenReturn(Optional.of(shop));

                User customer = mock(User.class);
                when(customer.getRole()).thenReturn(UserRole.CUSTOMER);
                when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));

                // when
                OrderServiceException exception = assertThrows(OrderServiceException.class,
                                () -> orderCommandService.createB2BOrder(command));

                // then
                assertEquals("userId", exception.getField());
                assertEquals("Only users with baker role can make B2B orders", exception.getMessage());
        }
}