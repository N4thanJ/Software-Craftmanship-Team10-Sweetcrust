package com.sweetcrust.team10_bakery.shared.infrastructure;

import java.math.BigDecimal;

import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopAddress;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import org.springframework.stereotype.Component;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    public DbInitializer(ProductRepository productRepository,  ShopRepository shopRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    private void clearAll() {
        productRepository.deleteAll();
    }

    @PostConstruct
    public void init() {
        clearAll();

        ProductCategory productCategory = new ProductCategory("Cakes", "A variety of cakes");

        Product curryCake = new Product("Curry Cake Delux", "A very delicious originated from India dish",
                BigDecimal.valueOf(4.99), true,
                productCategory.getCategoryId());

        productRepository.save(curryCake);

        Shop indiaShop = new Shop("SweetCrust New Delhi", new ShopAddress("Currystreet 1", "New Delhi", "110001", "India"), "newdelhi@sweetcrust.com");
        shopRepository.save(indiaShop);
    }
}
