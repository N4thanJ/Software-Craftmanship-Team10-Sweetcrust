package com.sweetcrust.team10_bakery.domain.product;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCategoryTest {

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
    void givenNullName_whenCreatingCategory_thenThrowsException() {
        // given
        String description = "Fluffy and sweet pastries";

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductCategory(null, description));

        // then
        assertEquals("category", exception.getField());
        assertEquals("name should not be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankName_whenCreatingCategory_thenThrowsException() {
        // given
        String description = "Fluffy and sweet pastries";

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductCategory("   ", description));

        // then
        assertEquals("category", exception.getField());
        assertEquals("name should not be null or blank", exception.getMessage());
    }

    @Test
    void givenNullDescription_whenCreatingCategory_thenThrowsException() {
        // given
        String name = "Breadtopia";

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductCategory(name, null));

        // then
        assertEquals("category", exception.getField());
        assertEquals("description should not be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankDescription_whenCreatingCategory_thenThrowsException() {
        // given
        String name = "Breadtopia";

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new ProductCategory(name, "   "));

        // then
        assertEquals("category", exception.getField());
        assertEquals("description should not be null or blank", exception.getMessage());
    }
}
