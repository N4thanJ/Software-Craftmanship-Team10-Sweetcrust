package com.sweetcrust.team10_bakery.product.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ProductVariantTest {

  private void expectFieldError(String field, String message, Runnable action) {
    ProductDomainException ex = assertThrows(ProductDomainException.class, action::run);
    assertEquals(field, ex.getField());
    assertEquals(message, ex.getMessage());
  }

  @Test
  void givenValidData_whenCreatingVariant_thenVariantIsCreated() {
    ProductSize size = ProductSize.LARGE;
    BigDecimal priceModifier = BigDecimal.valueOf(1.25);
    ProductId productId = new ProductId();

    ProductVariant variant = new ProductVariant(size, "Large", priceModifier, productId);

    assertNotNull(variant);
    assertNotNull(variant.getVariantId());
    assertEquals(size, variant.getSize());
    assertEquals("Large", variant.getVariantName());
    assertEquals(priceModifier, variant.getPriceModifier());
    assertEquals(productId, variant.getProductId());
  }

  @Test
  void givenNullSize_whenCreating_thenThrowsException() {
    expectFieldError(
        "size",
        "size should not be null",
        () -> new ProductVariant(null, "Small", BigDecimal.valueOf(0.50), new ProductId()));
  }

  @Test
  void givenNullVariantName_whenCreating_thenThrowsException() {
    expectFieldError(
        "variantName",
        "variantName should not be null or blank",
        () ->
            new ProductVariant(ProductSize.MINI, null, BigDecimal.valueOf(0.50), new ProductId()));
  }

  @Test
  void givenBlankVariantName_whenCreating_thenThrowsException() {
    expectFieldError(
        "variantName",
        "variantName should not be null or blank",
        () ->
            new ProductVariant(ProductSize.MINI, "   ", BigDecimal.valueOf(0.50), new ProductId()));
  }

  @Test
  void givenNullPriceModifier_whenCreating_thenThrowsException() {
    expectFieldError(
        "priceModifier",
        "priceModifier should not be null",
        () -> new ProductVariant(ProductSize.MINI, "Mini", null, new ProductId()));
  }

  @Test
  void givenNegativePriceModifier_whenCreating_thenThrowsException() {
    expectFieldError(
        "priceModifier",
        "priceModifier should not be negative",
        () ->
            new ProductVariant(
                ProductSize.REGULAR, "Regular", BigDecimal.valueOf(-1.00), new ProductId()));
  }

  @Test
  void givenNullProductId_whenCreating_thenThrowsException() {
    expectFieldError(
        "productId",
        "productId should not be null",
        () -> new ProductVariant(ProductSize.REGULAR, "Regular", BigDecimal.valueOf(1.00), null));
  }
}
