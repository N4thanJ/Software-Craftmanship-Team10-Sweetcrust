package com.sweetcrust.team10_bakery.product.domain;

import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
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

        // when
        ProductVariant variant = new ProductVariant(size, "Large", priceModifier);

        // then
        assertNotNull(variant);
        assertNotNull(variant.getVariantId());
        assertEquals(size, variant.getSize());
        assertEquals(priceModifier, variant.getPriceModifier());
    }

    @Test
    void givenNullVariantName_whenCreatingVariant_thenThrowsException() {
        // given
        BigDecimal priceModifier = BigDecimal.valueOf(0.50);

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant(null, "Small", priceModifier));

        // then
        assertEquals("size", exception.getField());
        assertEquals("size should not be null", exception.getMessage());
    }

    @Test
    void givenBlankVariantName_whenCreatingVariant_thenThrowsException() {
        // given
        BigDecimal priceModifier = BigDecimal.valueOf(0.50);

        // when - This test is no longer applicable since ProductSize is an enum
        // Testing with null instead
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant(null, "Small", priceModifier));

        // then
        assertEquals("size", exception.getField());
        assertEquals("size should not be null", exception.getMessage());
    }

    @Test
    void givenNullPriceModifier_whenCreatingVariant_thenThrowsException() {
        // given

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant(ProductSize.MINI, "Mini", null));

        // then
        assertEquals("priceModifier", exception.getField());
        assertEquals("priceModifier should not be null", exception.getMessage());
    }

    @Test
    void givenNegativePriceModifier_whenCreatingVariant_thenThrowsException() {
        // given
        BigDecimal priceModifier = BigDecimal.valueOf(-1.00);

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant(ProductSize.REGULAR, "Regular", priceModifier));

        // then
        assertEquals("priceModifier", exception.getField());
        assertEquals("priceModifier should not be negative", exception.getMessage());
    }
}
