package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderQueryServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderQueryService orderQueryService;

    @Test
    void givenGetAllOrders_whenGettingAllOrders_thenAllOrdersAreReturned() {
        // given
        Order crustyCroissantOrder = mock(Order.class);
        Order butteryBaguetteOrder = mock(Order.class);
        List<Order> allOrders = List.of(crustyCroissantOrder, butteryBaguetteOrder);
        when(orderRepository.findAll()).thenReturn(allOrders);

        // when
        List<Order> orders = orderQueryService.getAllOrders();

        // then
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    @Test
    void givenExistingOrderId_whenGetById_thenOrderWithIdIsReturned() {
        // given
        OrderId orderId = new OrderId(UUID.randomUUID());
        Order sourdoughOrder = mock(Order.class);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(sourdoughOrder));

        // when
        Order foundOrder = orderQueryService.getOrderById(orderId);

        // then
        assertEquals(sourdoughOrder, foundOrder);
    }

    @Test
    void givenInexistingOrderId_whenGetById_thenExceptionIsThrown() {
        // given
        OrderId orderId = new OrderId(UUID.randomUUID());

        // when
        OrderServiceException exception = assertThrows(OrderServiceException.class, () -> orderQueryService.getOrderById(orderId));

        // then
        assertEquals("order", exception.getField());
        assertEquals("order not found", exception.getMessage());
    }
}