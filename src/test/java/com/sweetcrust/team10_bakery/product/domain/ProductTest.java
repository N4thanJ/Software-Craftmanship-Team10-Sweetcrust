package com.sweetcrust.team10_bakery.product.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ProductTest {

  private void expectFieldError(String field, String message, Runnable action) {
    ProductDomainException ex = assertThrows(ProductDomainException.class, action::run);
    assertEquals(field, ex.getField());
    assertEquals(message, ex.getMessage());
  }

  private Product createValidProduct() {
    return new Product(
        "Sassy Cinnamon Roll",
        "A cinnamon roll with attitude",
        BigDecimal.valueOf(4.20),
        true,
        new CategoryId());
  }

  @Test
  void givenValidData_whenCreatingProduct_thenProductIsCreated() {
    Product product = createValidProduct();

    assertNotNull(product);
    assertNotNull(product.getProductId());
    assertEquals("Sassy Cinnamon Roll", product.getName());
    assertEquals("A cinnamon roll with attitude", product.getDescription());
    assertEquals(BigDecimal.valueOf(4.20), product.getBasePrice());
    assertTrue(product.isAvailable());
    assertNotNull(product.getCategoryId());
  }

  @Test
  void givenNullName_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "name should not be blank or null",
        () -> new Product(null, "desc", BigDecimal.ONE, true, new CategoryId()));
  }

  @Test
  void givenBlankName_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "name should not be blank or null",
        () -> new Product("   ", "desc", BigDecimal.ONE, true, new CategoryId()));
  }

  @Test
  void givenNullDescription_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "description should not be blank or null",
        () -> new Product("Prod", null, BigDecimal.ONE, true, new CategoryId()));
  }

  @Test
  void givenBlankDescription_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "description should not be blank or null",
        () -> new Product("Prod", "   ", BigDecimal.ONE, true, new CategoryId()));
  }

  @Test
  void givenNullBasePrice_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "basePrice should not be null",
        () -> new Product("Prod", "desc", null, true, new CategoryId()));
  }

  @Test
  void givenZeroBasePrice_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "basePrice should be greater than 0",
        () -> new Product("Prod", "desc", BigDecimal.ZERO, true, new CategoryId()));
  }

  @Test
  void givenNegativeBasePrice_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "basePrice should be greater than 0",
        () -> new Product("Prod", "desc", BigDecimal.valueOf(-2.50), true, new CategoryId()));
  }

  @Test
  void givenNullCategoryId_whenCreating_thenThrowsException() {
    expectFieldError(
        "product",
        "categoryId should not be null",
        () -> new Product("Prod", "desc", BigDecimal.ONE, true, null));
  }

  @Test
  void givenValidVariant_whenAddingVariant_thenVariantIsAdded() {
    Product product = createValidProduct();

    ProductVariant variant =
        new ProductVariant(
            ProductSize.REGULAR, "Standard Pack", BigDecimal.ZERO, product.getProductId());

    product.addVariant(variant);

    assertEquals(1, product.getVariants().size());
    assertTrue(product.getVariants().contains(variant));
  }

  @Test
  void givenNullVariant_whenAddingVariant_thenThrowsException() {
    Product product = createValidProduct();

    expectFieldError("variant", "variant cannot be null", () -> product.addVariant(null));
  }
}
