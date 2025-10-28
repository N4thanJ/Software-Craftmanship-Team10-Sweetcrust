package com.sweetcrust.team10_bakery.cart.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sweetcrust.team10_bakery.cart.application.commands.AddCartItemCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.DeleteCartItemCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.order.application.OrderServiceException;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CartCommandServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartCommandService cartCommandService;

    @Test
    void givenValidData_whenCreatingCart_thenCartIsCreated() {
        // given
        VariantId variantId = new VariantId();
        CategoryId categoryId = new CategoryId();
        Product tiramisu = new Product(
                "Tiramisu Dream",
                "Italian coffee-soaked layers of pure bliss - pick-me-up in dessert form",
                BigDecimal.valueOf(8.49),
                true,
                variantId,
                categoryId);

        CreateCartCommand command = new CreateCartCommand(tiramisu.getProductId(), 1);

        when(productRepository.findById(tiramisu.getProductId())).thenReturn(Optional.of(tiramisu));

        // when
        cartCommandService.createCart(command);

        // then
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void givenInvalidProductId_whenCreatingCart_thenExceptionIsThrown() throws Exception {
        // given
        CreateCartCommand command = new CreateCartCommand(null, 1);

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.createCart(command));

        // then
        assertEquals("productId", exception.getField());
        assertEquals("Product id cannot be null", exception.getMessage());
    }

    @Test
    void givenInvalidQuantity_whenCreatingCart_thenExceptionIsThrown() throws Exception {
        // given
        CreateCartCommand command = new CreateCartCommand(new ProductId(), 0);

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.createCart(command));

        // then
        assertEquals("quantity", exception.getField());
        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    void givenProductIdThatDoesNotExist_whenCreatingCart_thenExceptionIsThrown() throws Exception {
        // given
        ProductId productId = new ProductId();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        CreateCartCommand command = new CreateCartCommand(productId, 1);

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.createCart(command));

        // then
        assertEquals("product", exception.getField());
        assertEquals("Product with id " + productId + " could not be found", exception.getMessage());
    }

    @Test
    void givenValidData_whenAddingItemToCart_thenCartWithItemIsReturned() {
        // given
        VariantId variantId = new VariantId();
        CategoryId categoryId = new CategoryId();
        Product tiramisu = new Product(
                "Tiramisu Dream",
                "Italian coffee-soaked layers of pure bliss - pick-me-up in dessert form",
                BigDecimal.valueOf(8.49),
                true,
                variantId,
                categoryId);

        AddCartItemCommand command = new AddCartItemCommand(tiramisu.getProductId(), 1);

        Cart cart = new Cart(LocalDateTime.now());

        when(productRepository.findById(tiramisu.getProductId())).thenReturn(Optional.of(tiramisu));
        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));

        // when
        cartCommandService.addCardItem(cart.getCartId(), command);

        // then
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void givenInvalidCartId_whenAdddingItemToCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        AddCartItemCommand command = new AddCartItemCommand(new ProductId(), 1);

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.addCardItem(null, command));

        // then
        assertEquals("cartId", exception.getField());
        assertEquals("cart id cannot be null", exception.getMessage());
    }

    @Test
    void givenInvalidProductId_whenAdddingItemToCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        AddCartItemCommand command = new AddCartItemCommand(null, 1);

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.addCardItem(new CartId(), command));

        // then
        assertEquals("productId", exception.getField());
        assertEquals("Product id cannot be null", exception.getMessage());
    }

    @Test
    void givenInvalidQuantity_whenAdddingItemToCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        AddCartItemCommand command = new AddCartItemCommand(new ProductId(), 0);

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.addCardItem(new CartId(), command));

        // then
        assertEquals("quantity", exception.getField());
        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    void givenProductIdThatDoesNotExist_whenAdddingItemToCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        ProductId productId = new ProductId();
        AddCartItemCommand command = new AddCartItemCommand(productId, 1);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.addCardItem(new CartId(), command));

        // then
        assertEquals("product", exception.getField());
        assertEquals("Product with id " + productId + " could not be found", exception.getMessage());
    }

    @Test
    void givenCartIdThatDoesNotExist_whenAdddingItemToCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        VariantId variantId = new VariantId();
        CategoryId categoryId = new CategoryId();
        Product tiramisu = new Product(
                "Tiramisu Dream",
                "Italian coffee-soaked layers of pure bliss - pick-me-up in dessert form",
                BigDecimal.valueOf(8.49),
                true,
                variantId,
                categoryId);

        CartId cartId = new CartId();

        AddCartItemCommand command = new AddCartItemCommand(tiramisu.getProductId(), 1);

        when(productRepository.findById(tiramisu.getProductId())).thenReturn(Optional.of(tiramisu));
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.addCardItem(cartId, command));

        // then
        assertEquals("cart", exception.getField());
        assertEquals("Cart with id " + cartId + " could not be found", exception.getMessage());
    }

    @Test
    void givenValidData_whenRemovingItemFromCartThatHasMoreThanOneItem_thenNothingIsReturnedAndItemIsRemoved() {
        // given
        VariantId variantId = new VariantId();
        CategoryId categoryId = new CategoryId();
        Product tiramisu = new Product(
                "Tiramisu Dream",
                "Italian coffee-soaked layers of pure bliss - pick-me-up in dessert form",
                BigDecimal.valueOf(8.49),
                true,
                variantId,
                categoryId);

        Product bostonCreamDonut = new Product(
                "Boston Cream Dream",
                "Cream-filled heaven with chocolate ganache - double the indulgence",
                BigDecimal.valueOf(3.29),
                true,
                variantId,
                categoryId);

        DeleteCartItemCommand command = new DeleteCartItemCommand(tiramisu.getProductId());

        Cart cart = new Cart(LocalDateTime.now());
        cart.addCartItem(new CartItem(
                tiramisu.getProductId(),
                tiramisu.getVariantId(),
                1,
                tiramisu.getBasePrice()));
        cart.addCartItem(new CartItem(
                bostonCreamDonut.getProductId(),
                bostonCreamDonut.getVariantId(),
                1,
                bostonCreamDonut.getBasePrice()));

        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));

        // when
        cartCommandService.removeCardItem(cart.getCartId(), command);

        // then
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void givenValidData_whenRemovingItemFromCartThatOnlyHasOneItem_thenNothingIsReturnedAndCartIsDeleted() {
        // given
        VariantId variantId = new VariantId();
        CategoryId categoryId = new CategoryId();
        Product tiramisu = new Product(
                "Tiramisu Dream",
                "Italian coffee-soaked layers of pure bliss - pick-me-up in dessert form",
                BigDecimal.valueOf(8.49),
                true,
                variantId,
                categoryId);

        DeleteCartItemCommand command = new DeleteCartItemCommand(tiramisu.getProductId());

        Cart cart = new Cart(LocalDateTime.now());
        cart.addCartItem(new CartItem(
                tiramisu.getProductId(),
                tiramisu.getVariantId(),
                1,
                tiramisu.getBasePrice()));

        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));

        // when
        cartCommandService.removeCardItem(cart.getCartId(), command);

        // then
        verify(cartRepository, times(1)).delete(any(Cart.class));
    }

    @Test
    void givenInvalidCartId_whenRemovingItemFromCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        DeleteCartItemCommand command = new DeleteCartItemCommand(new ProductId());

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.removeCardItem(null, command));

        // then
        assertEquals("cartId", exception.getField());
        assertEquals("cart id cannot be null", exception.getMessage());
    }

    @Test
    void givenInvalidProductId_whenRemovingItemFromCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        DeleteCartItemCommand command = new DeleteCartItemCommand(null);

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.removeCardItem(new CartId(), command));

        // then
        assertEquals("productId", exception.getField());
        assertEquals("product id cannot be null", exception.getMessage());
    }

    @Test
    void givenCardIdThatDoesNotExist_whenRemovingItemFromCart_thenExceptionIsThrown() throws CartServiceException {
        // given
        CartId cartId = new CartId();
        DeleteCartItemCommand command = new DeleteCartItemCommand(new ProductId());
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.removeCardItem(cartId, command));

        // then
        assertEquals("cart", exception.getField());
        assertEquals("Cart with id " + cartId + " could not be found", exception.getMessage());
    }

    @Test
    void givenProductIdThatDoesNotExistInCart_whenRemovingItemFromCart_thenExceptionIsThrown()
            throws CartServiceException {
        // given
        Cart cart = new Cart(LocalDateTime.now());
        ProductId productId = new ProductId();
        DeleteCartItemCommand command = new DeleteCartItemCommand(productId);
        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));

        // when
        CartServiceException exception = assertThrows(CartServiceException.class,
                () -> cartCommandService.removeCardItem(cart.getCartId(), command));

        // then
        assertEquals("productId", exception.getField());
        assertEquals("Item not found in cart", exception.getMessage());
    }

}
