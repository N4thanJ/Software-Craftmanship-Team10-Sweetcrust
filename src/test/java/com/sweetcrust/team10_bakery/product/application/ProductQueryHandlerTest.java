package com.sweetcrust.team10_bakery.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductQueryHandlerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductQueryHandler productQueryHandler;

    @Test
    void givenGetAllProducts_whenGettingAllProducts_thenAllProductsAreReturned() {
        // given
        ProductCategory productCategory = new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");

        Product product = new Product(
                "bread-sheeran",
                "BreadS123!",
                BigDecimal.valueOf(0.5),
                false,
                productCategory.getCategoryId()
        );

        ProductVariant productVariant = new ProductVariant(
                ProductSize.REGULAR,
                "Regular",
                BigDecimal.valueOf(0.3),
                product.getProductId()
        );
        product.addVariant(productVariant);

        List<Product> allProducts = List.of(product);

        when(productRepository.findAll()).thenReturn(allProducts);

        // when
        List<Product> products = productQueryHandler.getAllProducts();

        // then
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("bread-sheeran", products.get(0).getName());
        assertEquals(1, products.get(0).getVariants().size());
        assertEquals(productCategory.getCategoryId(), products.get(0).getCategoryId());
    }
}
