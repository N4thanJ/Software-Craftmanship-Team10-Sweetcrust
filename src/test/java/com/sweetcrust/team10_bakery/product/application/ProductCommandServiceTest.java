package com.sweetcrust.team10_bakery.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductVariantCommand;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductCommandServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductCommandService productCommandService;

    @Test
    void givenValidData_whenCreatingProduct_thenProductIsCreated() {
        // given
        ProductCategory productCategory =
                new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");

        AddProductVariantCommand variantCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Regular",
                BigDecimal.valueOf(0.3)
        );

        AddProductCommand addProductCommand = new AddProductCommand(
                "Chocokoeken",
                "Een klassieke koffiekoek met chocolade tussen het broodje",
                BigDecimal.valueOf(2.99),
                true,
                productCategory.getCategoryId(),
                List.of(variantCommand)
        );

        // when
        productCommandService.createProduct(addProductCommand);

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void givenAlreadyCreatedProductName_whenCreatingProduct_thenExceptionIsThrown() {
        // given
        ProductCategory productCategory =
                new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");

        AddProductVariantCommand variantCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Regular",
                BigDecimal.valueOf(0.3)
        );

        AddProductCommand addProductCommand = new AddProductCommand(
                "Chocokoeken",
                "Een klassieke koffiekoek met chocolade tussen het broodje",
                BigDecimal.valueOf(2.99),
                true,
                productCategory.getCategoryId(),
                List.of(variantCommand)
        );

        when(productRepository.existsByName(addProductCommand.name())).thenReturn(true);

        // when
        ProductServiceException exception = assertThrows(
                ProductServiceException.class,
                () -> productCommandService.createProduct(addProductCommand)
        );

        // then
        assertEquals("name", exception.getField());
        assertEquals("product with name already exists", exception.getMessage());
    }

    @Test
    void givenValidVariant_whenAddingToExistingProduct_thenVariantIsAdded() {
        // given
        ProductCategory productCategory =
                new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");

        Product product = new Product(
                "Chocokoeken",
                "Een klassieke koffiekoek met chocolade tussen het broodje",
                BigDecimal.valueOf(2.99),
                true,
                productCategory.getCategoryId()
        );

        AddProductVariantCommand variantCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Regular",
                BigDecimal.valueOf(0.3)
        );

        when(productRepository.findById(product.getProductId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Product updatedProduct = productCommandService.addVariantToProduct(product.getProductId(), variantCommand);

        // then
        assertEquals(1, updatedProduct.getVariants().size());
        assertEquals("Regular", updatedProduct.getVariants().get(0).getVariantName());
        assertEquals(ProductSize.REGULAR, updatedProduct.getVariants().get(0).getSize());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void givenDuplicateVariant_whenAddingToProduct_thenExceptionIsThrown() {
        // given
        ProductCategory productCategory =
                new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");

        Product product = new Product(
                "Chocokoeken",
                "Een klassieke koffiekoek met chocolade tussen het broodje",
                BigDecimal.valueOf(2.99),
                true,
                productCategory.getCategoryId()
        );

        AddProductVariantCommand variantCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Regular",
                BigDecimal.valueOf(0.3)
        );

        // Add existing variant
        product.addVariant(new ProductVariant(
                ProductSize.REGULAR,
                "Regular",
                BigDecimal.valueOf(0.3),
                product.getProductId()
        ));

        when(productRepository.findById(product.getProductId())).thenReturn(java.util.Optional.of(product));

        // when
        ProductServiceException exception = assertThrows(
                ProductServiceException.class,
                () -> productCommandService.addVariantToProduct(product.getProductId(), variantCommand)
        );

        // then
        assertEquals("variant", exception.getField());
        assertEquals("variant with same size and name already exists", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void givenNonExistingProduct_whenAddingVariant_thenExceptionIsThrown() {
        // given
        AddProductVariantCommand variantCommand = new AddProductVariantCommand(
                ProductSize.REGULAR,
                "Regular",
                BigDecimal.valueOf(0.3)
        );

        ProductId nonExistingProductId = new ProductId();

        when(productRepository.findById(nonExistingProductId)).thenReturn(java.util.Optional.empty());

        // when
        ProductServiceException exception = assertThrows(
                ProductServiceException.class,
                () -> productCommandService.addVariantToProduct(nonExistingProductId, variantCommand)
        );

        // then
        assertEquals("product", exception.getField());
        assertEquals("product not found", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

}
