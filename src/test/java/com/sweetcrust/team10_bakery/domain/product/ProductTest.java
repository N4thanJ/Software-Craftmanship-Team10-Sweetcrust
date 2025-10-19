package com.sweetcrust.team10_bakery.domain.product;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void givenValidData_whenCreatingProduct_thenProductIsCreated() {
        // given
        String name = "Sassy Cinnamon Roll";
        String description = "A cinnamon roll with attitude";
        BigDecimal price = BigDecimal.valueOf(4.20);
        CategoryId categoryId = new CategoryId();

        // when
        Product product = new Product(name, description, price, categoryId);

        // then
        assertNotNull(product);
        assertNotNull(product.getProductId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getBasePrice());
        assertTrue(product.isAvailable());
        assertEquals(categoryId, product.getCategoryId());
    }

    @Test
    void givenNullName_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(3.50);
        CategoryId categoryId = new CategoryId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product(null, "Magically delicious", price, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("name should not be blank or null", exception.getMessage());
    }

    @Test
    void givenBlankName_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(3.50);
        CategoryId categoryId = new CategoryId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("   ", "Magically delicious", price, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("name should not be blank or null", exception.getMessage());
    }

    @Test
    void givenNullDescription_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(5.00);
        CategoryId categoryId = new CategoryId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Funky Monkey Muffin", null, price, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("description should not be blank or null", exception.getMessage());
    }

    @Test
    void givenBlankDescription_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(5.00);
        CategoryId categoryId = new CategoryId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Funky Monkey Muffin", "   ", price, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("description should not be blank or null", exception.getMessage());
    }

    @Test
    void givenNullBasePrice_whenCreatingProduct_thenThrowsException() {
        // given
        CategoryId categoryId = new CategoryId();

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Glazed Unicorn Donut", "Sparkly and magical", null, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("basePrice should not be null", exception.getMessage());
    }

    @Test
    void givenZeroBasePrice_whenCreatingProduct_thenThrowsException() {
        // given
        CategoryId categoryId = new CategoryId();
        BigDecimal price = BigDecimal.ZERO;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Baguette of Doom", "Seriously dangerous carbs", price, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("basePrice should be greater than 0", exception.getMessage());
    }

    @Test
    void givenNegativeBasePrice_whenCreatingProduct_thenThrowsException() {
        // given
        CategoryId categoryId = new CategoryId();
        BigDecimal price = BigDecimal.valueOf(-2.50);

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Eclair of Mystery", "Sweet, but suspicious", price, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("basePrice should be greater than 0", exception.getMessage());
    }

    @Test
    void givenNullCategoryId_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(3.75);

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Croissantzilla", "Monster-sized buttery delight", price, null));

        // then
        assertEquals("product", exception.getField());
        assertEquals("categoryId should not be null", exception.getMessage());
    }
}
