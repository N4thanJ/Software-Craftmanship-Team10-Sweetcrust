package com.sweetcrust.team10_bakery.product.application;

import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandService(ProductRepository productRepository) {
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
            product.addVariant(productVariant);
        });

        return productRepository.save(product);
    }
}
