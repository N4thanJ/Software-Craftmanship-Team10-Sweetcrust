package com.sweetcrust.team10_bakery.shared.infrastructure;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {
    private final ProductRepository productRepository;

    public DbInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void clearAll() {
        productRepository.deleteAll();
    }

    @PostConstruct
    public void init() {
        clearAll();

        ProductCategory productCategory = new ProductCategory("Cakes", "A variety of cakes");
        ProductVariant productVariant = new ProductVariant("Small", BigDecimal.valueOf(.3));

        Product curryCake = new Product(
                "Curry Cake Delux",
                "A very delicious originated from India dish",
                BigDecimal.valueOf(4.99),
                true,
                productVariant.getVariantId(),
                productCategory.getCategoryId());

        productRepository.save(curryCake);
    }
}
