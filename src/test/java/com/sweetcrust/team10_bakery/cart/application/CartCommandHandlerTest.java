package com.sweetcrust.team10_bakery.cart.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sweetcrust.team10_bakery.cart.application.commands.AddItemToCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.RemoveItemFromCartCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartItemRepository;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductVariantRepository;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartCommandHandlerTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @InjectMocks
    private CartCommandHandler cartCommandHandler;

    private UserId userId;
    private VariantId variantId;

    @BeforeEach
    void setUp() {
        userId = new UserId();
        variantId = new VariantId();
    }

    @Test
    void givenValidCreateCommand_whenNoExistingCart_thenCartCreated() {
        // given
        CreateCartCommand command = new CreateCartCommand(variantId, 3, userId);

        Product product = mock(Product.class);
        when(product.isAvailable()).thenReturn(true);

        ProductVariant variant = mock(ProductVariant.class);
        when(variant.getVariantId()).thenReturn(variantId);
        when(variant.getPrice()).thenReturn(BigDecimal.valueOf(2));
        when(variant.getProduct()).thenReturn(product);

        when(productVariantRepository.findById(variantId)).thenReturn(Optional.of(variant));
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        ArgumentCaptor<CartItem> itemCaptor = ArgumentCaptor.forClass(CartItem.class);
        when(cartItemRepository.save(itemCaptor.capture())).thenAnswer(i -> i.getArguments()[0]);

        // when
        Cart cart = cartCommandHandler.createCart(command);

        // then
        assertEquals(userId, cart.getOwnerId());

        CartItem item = itemCaptor.getValue();
        assertEquals(variantId, item.getVariantId());
        assertEquals(3, item.getQuantity());

        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void givenValidCreateCommand_whenExistingCart_thenItemAdded() {
        // given
        CreateCartCommand command = new CreateCartCommand(variantId, 2, userId);

        Cart existingCart = mock(Cart.class);
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(existingCart)).thenReturn(existingCart);

        Product product = mock(Product.class);
        when(product.isAvailable()).thenReturn(true);

        ProductVariant variant = mock(ProductVariant.class);
        when(variant.getVariantId()).thenReturn(variantId);
        when(variant.getPrice()).thenReturn(BigDecimal.ONE);
        when(variant.getProduct()).thenReturn(product);

        when(productVariantRepository.findById(variantId)).thenReturn(Optional.of(variant));
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(i -> i.getArguments()[0]);


        // when
        cartCommandHandler.createCart(command);

        // then
        verify(cartItemRepository).save(any(CartItem.class));
        verify(cartRepository).save(existingCart);
    }

    @Test
    void givenAddItemCommand_whenCartExists_thenItemAdded() {
        // given
        AddItemToCartCommand command = new AddItemToCartCommand(variantId, 5, userId);

        Cart existingCart = mock(Cart.class);
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(existingCart)).thenReturn(existingCart);

        Product product = mock(Product.class);
        when(product.isAvailable()).thenReturn(true);

        ProductVariant variant = mock(ProductVariant.class);
        when(variant.getVariantId()).thenReturn(variantId);
        when(variant.getPrice()).thenReturn(BigDecimal.ONE);
        when(variant.getProduct()).thenReturn(product);

        when(productVariantRepository.findById(variantId)).thenReturn(Optional.of(variant));
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        cartCommandHandler.addItemToCart(command);

        // then
        verify(cartItemRepository).save(any(CartItem.class));
        verify(cartRepository).save(existingCart);
    }

    @Test
    void givenAddItemCommand_whenNoCart_thenExceptionThrown() {
        AddItemToCartCommand command = new AddItemToCartCommand(variantId, 5, userId);

        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.empty());

        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandHandler.addItemToCart(command));

        assertEquals("ownerId", exception.getField());
    }

    @Test
    void givenRemoveItemCommand_whenQuantityLessThanCartItem_thenQuantityDecreased() {
        CartItemId cartItemId = new CartItemId();
        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand(userId, 2);

        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getQuantity()).thenReturn(5);

        Cart cart = mock(Cart.class);

        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.getCartItemByCartItemId(cartItemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.findByCartId(cart.getCartId())).thenReturn(List.of(cartItem)); // Cart not empty
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        when(cartRepository.save(cart)).thenReturn(cart);

        // when
        cartCommandHandler.removeItemFromCart(command, cartItemId);

        // then
        verify(cartItem).decreaseQuantity(2);
        verify(cartItemRepository).save(cartItem);
        verify(cartRepository).save(cart);
        verify(cartItemRepository, never()).delete(any());
    }

    @Test
    void givenRemoveItemCommand_whenQuantityGreaterOrEqual_thenItemRemoved() {
        CartItemId cartItemId = new CartItemId();
        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand(userId, 5);

        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getQuantity()).thenReturn(5);

        Cart cart = mock(Cart.class);

        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.getCartItemByCartItemId(cartItemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.findByCartId(cart.getCartId())).thenReturn(List.of(mock(CartItem.class))); // Cart not empty
        when(cartRepository.save(cart)).thenReturn(cart);

        // when
        cartCommandHandler.removeItemFromCart(command, cartItemId);

        // then
        verify(cartItemRepository).delete(cartItem);
        verify(cartRepository).save(cart);
        verify(cartRepository, never()).delete(any());
    }

    @Test
    void givenRemoveItemCommand_whenCartBecomesEmpty_thenCartDeleted() {
        CartItemId cartItemId = new CartItemId();
        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand(userId, 5);

        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getQuantity()).thenReturn(5);

        Cart cart = mock(Cart.class);

        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.getCartItemByCartItemId(cartItemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.findByCartId(cart.getCartId())).thenReturn(List.of()); // Cart IS empty

        // when
        cartCommandHandler.removeItemFromCart(command, cartItemId);

        // then
        verify(cartItemRepository).delete(cartItem);
        verify(cartRepository).delete(cart);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void givenRemoveItemCommand_whenItemNotFound_thenExceptionThrown() {
        CartItemId cartItemId = new CartItemId();
        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand(userId, 5);

        Cart cart = mock(Cart.class);
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.getCartItemByCartItemId(cartItemId)).thenReturn(Optional.empty());

        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandHandler.removeItemFromCart(command, cartItemId));

        assertEquals("cartItemId", exception.getField());
    }
}