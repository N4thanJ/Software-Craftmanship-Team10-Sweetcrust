package com.sweetcrust.team10_bakery.product.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import org.junit.jupiter.api.Test;

public class ProductCategoryTest {

  private void expectFieldError(String message, Runnable action) {
    ProductDomainException ex = assertThrows(ProductDomainException.class, action::run);
    assertEquals("category", ex.getField());
    assertEquals(message, ex.getMessage());
  }

  @Test
  void givenValidData_whenCreatingCategory_thenCategoryIsCreated() {
    // given
    String name = "Dessert Kingdom";
    String description = "All the sweet treats you could ever want";

    // when
    ProductCategory category = new ProductCategory(name, description);

    // then
    assertNotNull(category);
    assertNotNull(category.getCategoryId());
    assertEquals(name, category.getName());
    assertEquals(description, category.getDescription());
  }

  @Test
  void givenNullName_whenCreating_thenThrowsException() {
    expectFieldError("name should not be null or blank", () -> new ProductCategory(null, "desc"));
  }

  @Test
  void givenBlankName_whenCreating_thenThrowsException() {
    expectFieldError("name should not be null or blank", () -> new ProductCategory("   ", "desc"));
  }

  @Test
  void givenNullDescription_whenCreating_thenThrowsException() {
    expectFieldError(
        "description should not be null or blank", () -> new ProductCategory("Name", null));
  }

  @Test
  void givenBlankDescription_whenCreating_thenThrowsException() {
    expectFieldError(
        "description should not be null or blank", () -> new ProductCategory("Name", "   "));
  }
}
