package com.sweetcrust.team10_bakery.product.presentation;

import com.sweetcrust.team10_bakery.product.application.ProductCommandHandler;
import com.sweetcrust.team10_bakery.product.application.ProductQueryHandler;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductVariantCommand;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@Tag(name = "Product Management", description = "Endpoints related to product management and fetching")
public class ProductRestController {
    private final ProductQueryHandler productQueryHandler;
    private final ProductCommandHandler productCommandHandler;

    public ProductRestController(ProductQueryHandler productQueryHandler, ProductCommandHandler productCommandHandler) {
        this.productQueryHandler = productQueryHandler;
        this.productCommandHandler = productCommandHandler;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        List<Product> products = productQueryHandler.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID productId) {
        Product product = productQueryHandler.getProductById(new ProductId(productId));
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody AddProductCommand addProductCommand) {
        Product product = productCommandHandler.createProduct(addProductCommand);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Product> addVariantToProduct(@PathVariable UUID productId, @RequestBody AddProductVariantCommand addProductVariantCommand) {
        Product product = productCommandHandler.addVariantToProduct(new ProductId(productId), addProductVariantCommand);
        return ResponseEntity.ok(product);
    }
}
