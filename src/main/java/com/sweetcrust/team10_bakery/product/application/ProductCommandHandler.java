package com.sweetcrust.team10_bakery.product.application;

import com.sweetcrust.team10_bakery.order.application.OrderServiceException;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductVariantCommand;
import com.sweetcrust.team10_bakery.product.application.commands.MarkProductAvailableCommand;
import com.sweetcrust.team10_bakery.product.application.commands.MarkProductUnavailableCommand;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import com.sweetcrust.team10_bakery.user.application.UserServiceException;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandHandler {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductCommandHandler(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void createProduct(AddProductCommand addProductCommand) {

        User user = userRepository.findById(addProductCommand.userId())
                .orElseThrow(() -> new UserServiceException("userId", "User not found"));

        if (user.getRole() != UserRole.BAKER && user.getRole() != UserRole.ADMIN) {
            throw new ProductServiceException("role", "Only users with BAKER or ADMIN role can create products");
        }
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

        productRepository.save(product);
    }

    public void addVariantToProduct(ProductId productId, AddProductVariantCommand addProductVariantCommand) {
        User user = userRepository.findById(addProductVariantCommand.userId())
                .orElseThrow(() -> new UserServiceException("userId", "User not found"));

        if (user.getRole() != UserRole.BAKER && user.getRole() != UserRole.ADMIN) {
            throw new ProductServiceException("role", "Only users with BAKER or ADMIN role can add product variants");
        }

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

        productRepository.save(product);
    }

    public void markProductUnavailable(MarkProductUnavailableCommand markProductUnavailableCommand) {
        Product product = productRepository.findById(markProductUnavailableCommand.productId())
                .orElseThrow(() -> new ProductServiceException("product", "product not found"));

        User user = userRepository.findById(markProductUnavailableCommand.userId())
                .orElseThrow(() -> new ProductServiceException("userId", "User not found"));

        if (user.getRole() != UserRole.BAKER && user.getRole() != UserRole.ADMIN) {
            throw new ProductServiceException("role", "Only users with BAKER or ADMIN role can change product availability");
        }

        if (!product.isAvailable()) {
            throw new ProductServiceException("product", "product is already unavailable");
        }

        product.markUnavailable();
        productRepository.save(product);
    }

    public void markProductAvailable(MarkProductAvailableCommand markProductAvailableCommand) {
        Product product = productRepository.findById(markProductAvailableCommand.productId())
                .orElseThrow(() -> new ProductServiceException("product", "product not found"));

        User user = userRepository.findById(markProductAvailableCommand.userId())
                .orElseThrow(() -> new ProductServiceException("userId", "User not found"));

        if (user.getRole() != UserRole.BAKER && user.getRole() != UserRole.ADMIN) {
            throw new ProductServiceException("role", "Only users with BAKER or ADMIN role can change product availability");
        }

        if (product.isAvailable()) {
            throw new ProductServiceException("product", "product is already available");
        }

        product.markAvailable();
        productRepository.save(product);
    }
}
