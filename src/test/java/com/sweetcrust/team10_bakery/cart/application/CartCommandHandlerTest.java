package com.sweetcrust.team10_bakery.cart.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sweetcrust.team10_bakery.cart.application.commands.AddItemToCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.RemoveItemFromCartCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductVariantRepository;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartCommandHandlerTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

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

        // when
        Cart cart = cartCommandHandler.createCart(command);

        // then
        assertEquals(userId, cart.getOwnerId());
        assertEquals(1, cart.getCartItems().size());
        CartItem item = cart.getCartItems().getFirst();
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

        // when
        cartCommandHandler.createCart(command);

        // then
        verify(existingCart).addCartItem(any(CartItem.class));
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

        // when
        cartCommandHandler.addItemToCart(command);

        // then
        verify(existingCart).addCartItem(any(CartItem.class));
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
        when(cartItem.getCartItemId()).thenReturn(cartItemId);
        when(cartItem.getQuantity()).thenReturn(5);

        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(List.of(cartItem));
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cart.isEmpty()).thenReturn(false);

        cartCommandHandler.removeItemFromCart(command, cartItemId);

        verify(cartItem).decreaseQuantity(2);
        verify(cartRepository).save(cart);
    }

    @Test
    void givenRemoveItemCommand_whenQuantityGreaterOrEqual_thenItemRemoved() {
        CartItemId cartItemId = new CartItemId();
        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand(userId, 5);

        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getCartItemId()).thenReturn(cartItemId);
        when(cartItem.getQuantity()).thenReturn(5);

        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(List.of(cartItem));
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));
        when(cart.isEmpty()).thenReturn(false);
        when(cartRepository.save(cart)).thenReturn(cart);

        cartCommandHandler.removeItemFromCart(command, cartItemId);

        verify(cart).removeCartItem(cartItemId);
        verify(cartRepository).save(cart);
    }

    @Test
    void givenRemoveItemCommand_whenCartBecomesEmpty_thenCartDeleted() {
        CartItemId cartItemId = new CartItemId();
        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand(userId, 5);

        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getCartItemId()).thenReturn(cartItemId);
        when(cartItem.getQuantity()).thenReturn(5);

        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(List.of(cartItem));
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));
        when(cart.isEmpty()).thenReturn(true);

        cartCommandHandler.removeItemFromCart(command, cartItemId);

        verify(cart).removeCartItem(cartItemId);
        verify(cartRepository).delete(cart);
    }

    @Test
    void givenRemoveItemCommand_whenItemNotFound_thenExceptionThrown() {
        CartItemId cartItemId = new CartItemId();
        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand(userId, 5);

        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(List.of());
        when(cartRepository.findByOwnerId(userId)).thenReturn(Optional.of(cart));

        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandHandler.removeItemFromCart(command, cartItemId));

        assertEquals("cartItemId", exception.getField());
    }
}
