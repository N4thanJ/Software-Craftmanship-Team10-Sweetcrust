package com.sweetcrust.team10_bakery.product.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void givenValidData_whenCreatingProduct_thenProductIsCreated() {
        // given
        String name = "Sassy Cinnamon Roll";
        String description = "A cinnamon roll with attitude";
        BigDecimal price = BigDecimal.valueOf(4.20);
        CategoryId categoryId = new CategoryId();
        boolean available = true;

        // when
        Product product = new Product(name, description, price, available, categoryId);

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
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product(null, "Magically delicious", price, available, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("name should not be blank or null", exception.getMessage());
    }

    @Test
    void givenBlankName_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(3.50);
        CategoryId categoryId = new CategoryId();
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("   ", "Magically delicious", price, available, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("name should not be blank or null", exception.getMessage());
    }

    @Test
    void givenNullDescription_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(5.00);
        CategoryId categoryId = new CategoryId();
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Funky Monkey Muffin", null, price, available, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("description should not be blank or null", exception.getMessage());
    }

    @Test
    void givenBlankDescription_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(5.00);
        CategoryId categoryId = new CategoryId();
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Funky Monkey Muffin", "   ", price, available, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("description should not be blank or null", exception.getMessage());
    }

    @Test
    void givenNullBasePrice_whenCreatingProduct_thenThrowsException() {
        // given
        CategoryId categoryId = new CategoryId();
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Glazed Unicorn Donut", "Sparkly and magical", null, available, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("basePrice should not be null", exception.getMessage());
    }

    @Test
    void givenZeroBasePrice_whenCreatingProduct_thenThrowsException() {
        // given
        CategoryId categoryId = new CategoryId();
        BigDecimal price = BigDecimal.ZERO;
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Baguette of Doom", "Seriously dangerous carbs", price, available, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("basePrice should be greater than 0", exception.getMessage());
    }

    @Test
    void givenNegativeBasePrice_whenCreatingProduct_thenThrowsException() {
        // given
        CategoryId categoryId = new CategoryId();
        BigDecimal price = BigDecimal.valueOf(-2.50);
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Eclair of Mystery", "Sweet, but suspicious", price, available, categoryId));

        // then
        assertEquals("product", exception.getField());
        assertEquals("basePrice should be greater than 0", exception.getMessage());
    }

    @Test
    void givenNullCategoryId_whenCreatingProduct_thenThrowsException() {
        // given
        BigDecimal price = BigDecimal.valueOf(3.75);
        boolean available = true;

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> new Product("Croissantzilla", "Monster-sized buttery delight", price, available, null));

        // then
        assertEquals("product", exception.getField());
        assertEquals("categoryId should not be null", exception.getMessage());
    }

    @Test
    void givenValidVariant_whenAddingVariant_thenVariantIsAdded() {
        // given
        Product product = new Product(
                "Chocolate Chip Cookie",
                "Crispy edges and chewy center",
                BigDecimal.valueOf(2.50),
                true,
                new CategoryId()
        );

        ProductVariant variant = new ProductVariant(
                ProductSize.REGULAR,
                "Standard Pack",
                BigDecimal.valueOf(0),
                product.getProductId()
        );

        // when
        product.addVariant(variant);

        // then
        assertEquals(1, product.getVariants().size());
        assertTrue(product.getVariants().contains(variant));
    }

    @Test
    void givenNullVariant_whenAddingVariant_thenThrowsException() {
        // given
        Product product = new Product(
                "Vanilla Cupcake",
                "Simple but elegant",
                BigDecimal.valueOf(3.00),
                true,
                new CategoryId()
        );

        // when
        ProductDomainException exception = assertThrows(ProductDomainException.class,
                () -> product.addVariant(null));

        // then
        assertEquals("variant", exception.getField());
        assertEquals("variant cannot be null", exception.getMessage());
    }
}
