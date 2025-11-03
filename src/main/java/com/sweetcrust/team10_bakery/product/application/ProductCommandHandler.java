package com.sweetcrust.team10_bakery.product.application;

import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductVariantCommand;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandHandler {

    private final ProductRepository productRepository;

    public ProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(AddProductCommand addProductCommand) {
        boolean existsByName = productRepository.existsByName(addProductCommand.name());

        if (existsByName) {
            throw new ProductServiceException("name", "product with name already exists");
        }

        Product product = new Product(
                addProductCommand.name(),
                addProductCommand.description(),
                addProductCommand.basePrice(),
                addProductCommand.available(),
                addProductCommand.categoryId()
        );

        addProductCommand.variants().forEach(variantCmd -> {
            ProductVariant productVariant = new ProductVariant(
                    variantCmd.size(),
                    variantCmd.variantName(),
                    variantCmd.priceModifier(),
                    product.getProductId()
            );
            productVariant.setProduct(product);
            product.addVariant(productVariant);
        });

        return productRepository.save(product);
    }

    public Product addVariantToProduct(ProductId productId, AddProductVariantCommand addProductVariantCommand) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException("product", "product not found"));

        boolean exists = product.getVariants().stream()
                .anyMatch(variant -> variant.getSize() == addProductVariantCommand.size() &&
                        variant.getVariantName().equals(addProductVariantCommand.variantName()));
        if (exists) {
            throw new ProductServiceException("variant", "variant with same size and name already exists");
        }

        ProductVariant variant = new ProductVariant(
                addProductVariantCommand.size(),
                addProductVariantCommand.variantName(),
                addProductVariantCommand.priceModifier(),
                product.getProductId()
        );
        variant.setProduct(product);
        product.addVariant(variant);

        return productRepository.save(product);
    }
}
