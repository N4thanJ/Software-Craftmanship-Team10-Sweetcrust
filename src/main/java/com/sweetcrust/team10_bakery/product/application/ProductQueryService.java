package com.sweetcrust.team10_bakery.product.application;

import com.sweetcrust.team10_bakery.order.application.OrderServiceException;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {
    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(ProductId productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new OrderServiceException("product", "product not found"));
    }
}
