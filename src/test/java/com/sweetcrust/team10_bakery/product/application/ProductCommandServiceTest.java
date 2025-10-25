package com.sweetcrust.team10_bakery.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
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

        ProductVariant productVariant = new ProductVariant(ProductSize.REGULAR, "Regular", BigDecimal.valueOf(.3));
        ProductCategory productCategory = new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");
        AddProductCommand addProductCommand = new AddProductCommand(
                "Chocokoeken",
                "Een klassieke koffiekoek met chocolade tussen het broodje",
                BigDecimal.valueOf(2.99),
                true,
                productVariant.getVariantId(),
                productCategory.getCategoryId());

        // when
        productCommandService.createProduct(addProductCommand);

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void givenAlreadyCreatedProductName_whenCreatingProduct_thenExceptionIsThrown() {
        // given
        ProductVariant productVariant = new ProductVariant(ProductSize.REGULAR, "Regular", BigDecimal.valueOf(.3));
        ProductCategory productCategory = new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");
        AddProductCommand addProductCommand = new AddProductCommand(
                "Chocokoeken",
                "Een klassieke koffiekoek met chocolade tussen het broodje",
                BigDecimal.valueOf(2.99),
                true,
                productVariant.getVariantId(),
                productCategory.getCategoryId());

        when(productRepository.existsByName(addProductCommand.name())).thenReturn(true);

        // when
        ProductServiceException exception = assertThrows(ProductServiceException.class,
                () -> productCommandService.createProduct(addProductCommand));

        // then
        assertEquals("name", exception.getField());
        assertEquals("product with name already exists", exception.getMessage());
    }
}
