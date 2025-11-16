package com.sweetcrust.team10_bakery.product.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.sweetcrust.team10_bakery.product.application.commands.*;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductCommandHandlerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductCommandHandler productCommandHandler;

    private ProductCategory pastryCategory;
    private Product chocolateCroissant;
    private User baker;
    private User admin;
    private User customer;

    @BeforeEach
    void setup() {
        pastryCategory = new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");
        chocolateCroissant = new Product(
                "Choco Croissant",
                "Fluffy, buttery, chocolate-filled pastry",
                BigDecimal.valueOf(2.99),
                true,
                pastryCategory.getCategoryId()
        );

        baker = new User("baker123", "Bakery123!", "baker@sweetcrust.com", UserRole.BAKER);
        admin = new User("admin007", "Admin123!", "admin@sweetcrust.com", UserRole.ADMIN);
        customer = new User("sleepy", "SleepyJoe123!", "sleepy@sweetcrust.com", UserRole.CUSTOMER);
    }

    @Test
    void givenUniqueProduct_whenCreating_thenProductIsSaved() {
        AddProductCommand command = new AddProductCommand(
                "Chocokoek",
                "A classic chocolate pastry",
                BigDecimal.valueOf(2.99),
                true,
                pastryCategory.getCategoryId(),
                List.of(),
                baker.getUserId()
        );

        when(userRepository.findById(baker.getUserId())).thenReturn(Optional.of(baker));

        when(productRepository.existsByName("Chocokoek")).thenReturn(false);

        productCommandHandler.createProduct(command);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void givenDuplicateProductName_whenCreating_thenExceptionThrown() {
        AddProductCommand command = new AddProductCommand(
                "Choco Croissant",
                "Duplicate delight",
                BigDecimal.valueOf(2.99),
                true,
                pastryCategory.getCategoryId(),
                List.of(),
                baker.getUserId()
        );

        when(userRepository.findById(baker.getUserId())).thenReturn(Optional.of(baker));

        when(productRepository.existsByName("Choco Croissant")).thenReturn(true);

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.createProduct(command)
        );

        assertEquals("name", ex.getField());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenUnauthorizedUser_whenCreating_thenExceptionIsThrown() {
        AddProductCommand command = new AddProductCommand(
                "Chocokoek",
                "A classic chocolate pastry",
                BigDecimal.valueOf(2.99),
                true,
                pastryCategory.getCategoryId(),
                List.of(),
                customer.getUserId()
        );

        when(userRepository.findById(customer.getUserId())).thenReturn(Optional.of(customer));

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.createProduct(command)
        );
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void givenNewVariant_whenAdding_thenVariantIsSaved() {
        AddProductVariantCommand variantCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Choco Mini",
                BigDecimal.valueOf(0.3),
                baker.getUserId()
        );

        when(userRepository.findById(baker.getUserId())).thenReturn(Optional.of(baker));

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));

        productCommandHandler.addVariantToProduct(chocolateCroissant.getProductId(), variantCommand);

        assertEquals(1, chocolateCroissant.getVariants().size());
        verify(productRepository, times(1)).save(chocolateCroissant);
    }

    @Test
    void givenDuplicateVariant_whenAdding_thenExceptionThrown() {
        ProductVariant existingVariant = new ProductVariant(
                ProductSize.REGULAR,
                "Choco Mini",
                BigDecimal.valueOf(0.3),
                chocolateCroissant.getProductId()
        );
        chocolateCroissant.addVariant(existingVariant);

        AddProductVariantCommand duplicateCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Choco Mini",
                BigDecimal.valueOf(0.3),
                baker.getUserId()
        );

        when(userRepository.findById(baker.getUserId())).thenReturn(Optional.of(baker));

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.addVariantToProduct(chocolateCroissant.getProductId(), duplicateCommand)
        );

        assertEquals("variant", ex.getField());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenUnauthorizedUser_whenAdding_thenVariantIsSaved() {
        AddProductVariantCommand variantCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Choco Mini",
                BigDecimal.valueOf(0.3),
                customer.getUserId()
        );

        when(userRepository.findById(customer.getUserId())).thenReturn(Optional.of(customer));

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.addVariantToProduct(chocolateCroissant.getProductId(), variantCommand)
        );

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void givenUnavailableProduct_whenBakerMarksAvailable_thenProductIsAvailable() {
        chocolateCroissant.markUnavailable();
        MarkProductAvailableCommand command = new MarkProductAvailableCommand(
                chocolateCroissant.getProductId(),
                baker.getUserId()
        );

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));
        when(userRepository.findById(baker.getUserId())).thenReturn(Optional.of(baker));

        productCommandHandler.markProductAvailable(command);

        assertTrue(chocolateCroissant.isAvailable());
        verify(productRepository, times(1)).save(chocolateCroissant);
    }

    @Test
    void givenAlreadyAvailableProduct_whenMarkAvailable_thenExceptionThrown() {
        MarkProductAvailableCommand command = new MarkProductAvailableCommand(
                chocolateCroissant.getProductId(),
                baker.getUserId()
        );

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));
        when(userRepository.findById(baker.getUserId())).thenReturn(Optional.of(baker));

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.markProductAvailable(command)
        );

        assertEquals("product", ex.getField());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenUnauthorizedUser_whenMarkAvailable_thenExceptionThrown() {
        MarkProductAvailableCommand command = new MarkProductAvailableCommand(
                chocolateCroissant.getProductId(),
                customer.getUserId()
        );

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));
        when(userRepository.findById(customer.getUserId())).thenReturn(Optional.of(customer));

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.markProductAvailable(command)
        );

        assertEquals("role", ex.getField());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenAvailableProduct_whenAdminMarksUnavailable_thenProductIsUnavailable() {
        MarkProductUnavailableCommand command = new MarkProductUnavailableCommand(
                chocolateCroissant.getProductId(),
                admin.getUserId()
        );

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));
        when(userRepository.findById(admin.getUserId())).thenReturn(Optional.of(admin));

        productCommandHandler.markProductUnavailable(command);

        assertFalse(chocolateCroissant.isAvailable());
        verify(productRepository, times(1)).save(chocolateCroissant);
    }

    @Test
    void givenAlreadyUnavailableProduct_whenMarkUnavailable_thenExceptionThrown() {
        chocolateCroissant.markUnavailable();
        MarkProductUnavailableCommand command = new MarkProductUnavailableCommand(
                chocolateCroissant.getProductId(),
                admin.getUserId()
        );

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));
        when(userRepository.findById(admin.getUserId())).thenReturn(Optional.of(admin));

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.markProductUnavailable(command)
        );

        assertEquals("product", ex.getField());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenUnauthorizedUser_whenMarkUnavailable_thenExceptionThrown() {
        MarkProductUnavailableCommand command = new MarkProductUnavailableCommand(
                chocolateCroissant.getProductId(),
                customer.getUserId()
        );

        when(productRepository.findById(chocolateCroissant.getProductId())).thenReturn(Optional.of(chocolateCroissant));
        when(userRepository.findById(customer.getUserId())).thenReturn(Optional.of(customer));

        ProductServiceException ex = assertThrows(
                ProductServiceException.class,
                () -> productCommandHandler.markProductUnavailable(command)
        );

        assertEquals("role", ex.getField());
        verify(productRepository, never()).save(any());
    }
}
