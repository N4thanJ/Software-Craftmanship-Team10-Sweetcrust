package com.sweetcrust.team10_bakery.cart.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;

@ExtendWith(MockitoExtension.class)
public class CartQueryServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartQueryService cartQueryService;

    @Test
    void givenGetAllCarts_whenGettingAllCarts_thenAllCartsAreReturned() {
        // given
        Cart crustyCroissantCart = mock(Cart.class);
        List<Cart> allCarts = List.of(crustyCroissantCart);
        when(cartRepository.findAll()).thenReturn(allCarts);

        // when
        List<Cart> carts = cartQueryService.getAllCarts();

        // then
        assertNotNull(carts);
        assertEquals(1, carts.size());
    }
}
