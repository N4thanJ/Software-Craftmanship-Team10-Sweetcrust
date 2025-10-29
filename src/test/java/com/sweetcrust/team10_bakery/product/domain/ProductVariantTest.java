package com.sweetcrust.team10_bakery.product.domain;

import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductVariantTest {

    @Test
    void givenValidData_whenCreatingVariant_thenVariantIsCreated() {
        // given
        ProductSize size = ProductSize.LARGE;
        BigDecimal priceModifier = BigDecimal.valueOf(1.25);
        ProductId productId = new ProductId();

        // when
        ProductVariant variant = new ProductVariant(size, "Large", priceModifier, productId);

        // then
        assertNotNull(variant);
        assertNotNull(variant.getVariantId());
        assertEquals(size, variant.getSize());
        assertEquals("Large", variant.getVariantName());
        assertEquals(priceModifier, variant.getPriceModifier());
        assertEquals(productId, variant.getProductId());
    }

    @Test
    void givenNullSize_whenCreatingVariant_thenThrowsException() {
        ProductId productId = new ProductId();

        ProductDomainException exception = assertThrows(
                ProductDomainException.class,
                () -> new ProductVariant(null, "Small", BigDecimal.valueOf(0.50), productId)
        );

        assertEquals("size", exception.getField());
        assertEquals("size should not be null", exception.getMessage());
    }

    @Test
    void givenNullVariantName_whenCreatingVariant_thenThrowsException() {
        ProductId productId = new ProductId();

        ProductDomainException exception = assertThrows(
                ProductDomainException.class,
                () -> new ProductVariant(ProductSize.MINI, null, BigDecimal.valueOf(0.50), productId)
        );

        assertEquals("variantName", exception.getField());
        assertEquals("variantName should not be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankVariantName_whenCreatingVariant_thenThrowsException() {
        ProductId productId = new ProductId();

        ProductDomainException exception = assertThrows(
                ProductDomainException.class,
                () -> new ProductVariant(ProductSize.MINI, "   ", BigDecimal.valueOf(0.50), productId)
        );

        assertEquals("variantName", exception.getField());
        assertEquals("variantName should not be null or blank", exception.getMessage());
    }

    @Test
    void givenNullPriceModifier_whenCreatingVariant_thenThrowsException() {
        ProductId productId = new ProductId();

        ProductDomainException exception = assertThrows(
                ProductDomainException.class,
                () -> new ProductVariant(ProductSize.MINI, "Mini", null, productId)
        );

        assertEquals("priceModifier", exception.getField());
        assertEquals("priceModifier should not be null", exception.getMessage());
    }

    @Test
    void givenNegativePriceModifier_whenCreatingVariant_thenThrowsException() {
        ProductId productId = new ProductId();

        ProductDomainException exception = assertThrows(
                ProductDomainException.class,
                () -> new ProductVariant(ProductSize.REGULAR, "Regular", BigDecimal.valueOf(-1.00), productId)
        );

        assertEquals("priceModifier", exception.getField());
        assertEquals("priceModifier should not be negative", exception.getMessage());
    }

    @Test
    void givenNullProductId_whenCreatingVariant_thenThrowsException() {
        ProductDomainException exception = assertThrows(
                ProductDomainException.class,
                () -> new ProductVariant(ProductSize.REGULAR, "Regular", BigDecimal.valueOf(1.00), null)
        );

        assertEquals("productId", exception.getField());
        assertEquals("productId should not be null", exception.getMessage());
    }
}
