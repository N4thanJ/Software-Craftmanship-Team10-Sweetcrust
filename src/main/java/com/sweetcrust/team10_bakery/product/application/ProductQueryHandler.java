package com.sweetcrust.team10_bakery.product.application;

import com.sweetcrust.team10_bakery.order.application.OrderServiceException;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import com.sweetcrust.team10_bakery.product.presentation.dto.ProductResponseDto;
import com.sweetcrust.team10_bakery.product.presentation.dto.ProductVariantResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryHandler {
    private final ProductRepository productRepository;

    public ProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::toProductResponseDto)
                .toList();
    }

    public ProductResponseDto getProductById(ProductId productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new OrderServiceException("product", "product not found"));

        return toProductResponseDto(product);
    }

    // Mapper
    private ProductResponseDto toProductResponseDto(Product product) {
        List<ProductVariantResponseDto> variants = product.getVariants().stream()
                .map(v -> new ProductVariantResponseDto(
                        v.getVariantId(),
                        v.getSize(),
                        v.getVariantName(),
                        v.getPriceModifier()
                )).toList();

        return new ProductResponseDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getBasePrice(),
                product.isAvailable(),
                product.getCategoryId(),
                variants
        );
    }
}
