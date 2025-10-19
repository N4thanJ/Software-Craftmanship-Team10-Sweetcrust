package com.sweetcrust.team10_bakery.domain.product;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductVariantTest {

    @Test
    void givenValidData_whenCreatingVariant_thenVariantIsCreated() {
        // given
        String variantName = "Extra Cheesy";
        BigDecimal priceModifier = BigDecimal.valueOf(1.25);
        ProductId productId = new ProductId();

        // when
        ProductVariant variant = new ProductVariant(variantName, priceModifier, productId);

        // then
        assertNotNull(variant);
        assertNotNull(variant.getVariantId());
        assertEquals(variantName, variant.getVariantName());
        assertEquals(priceModifier, variant.getPriceModifier());
        assertEquals(productId, variant.getProductId());
    }

    @Test
    void givenNullVariantName_whenCreatingVariant_thenThrowsException() {
        // given
        BigDecimal priceModifier = BigDecimal.valueOf(0.50);
        ProductId productId = new ProductId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant(null, priceModifier, productId));

        // then
        assertEquals("variant", exception.getField());
        assertEquals("variantName should not be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankVariantName_whenCreatingVariant_thenThrowsException() {
        // given
        BigDecimal priceModifier = BigDecimal.valueOf(0.50);
        ProductId productId = new ProductId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant("   ", priceModifier, productId));

        // then
        assertEquals("variant", exception.getField());
        assertEquals("variantName should not be null or blank", exception.getMessage());
    }

    @Test
    void givenNullPriceModifier_whenCreatingVariant_thenThrowsException() {
        // given
        ProductId productId = new ProductId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant("Spicy Surprise", null, productId));

        // then
        assertEquals("priceModifier", exception.getField());
        assertEquals("priceModifier should not be null", exception.getMessage());
    }

    @Test
    void givenNegativePriceModifier_whenCreatingVariant_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        BigDecimal priceModifier = BigDecimal.valueOf(-1.00);

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant("Mega Marshmallow", priceModifier, productId));

        // then
        assertEquals("priceModifier", exception.getField());
        assertEquals("priceModifier should not be negative", exception.getMessage());
    }

    @Test
    void givenNullProductId_whenCreatingVariant_thenThrowsException() {
        // given
        BigDecimal priceModifier = BigDecimal.valueOf(0.75);

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductVariant("Caramel Crunch", priceModifier, null));

        // then
        assertEquals("variant", exception.getField());
        assertEquals("productId should not be null", exception.getMessage());
    }
}
