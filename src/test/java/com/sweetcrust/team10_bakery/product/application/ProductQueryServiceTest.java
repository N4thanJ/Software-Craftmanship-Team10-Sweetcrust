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
public class ProductQueryServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductQueryService productQueryService;

    @Test
    void givenGetAllProducts_whenGettingAllProducts_thenAlProductsAreReturned() {
        // given
        ProductVariant productVariant = new ProductVariant(ProductSize.REGULAR, "Regular", BigDecimal.valueOf(.3));
        ProductCategory productCategory = new ProductCategory("Koffiekoeken", "Het hele assortiment van chocokoeken");

        List<Product> allProducts = List.of(
                new Product("bread-sheeran",
                        "BreadS123!",
                        BigDecimal.valueOf(.5),
                        false,
                        productVariant.getVariantId(),
                        productCategory.getCategoryId()));

        when(productRepository.findAll()).thenReturn(allProducts);

        // when
        List<Product> products = productQueryService.getAllProducts();

        // then
        assertNotNull(products);
        assertEquals(1, products.size());
    }
}
