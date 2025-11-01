package com.sweetcrust.team10_bakery.product.presentation;

import com.sweetcrust.team10_bakery.product.application.ProductCommandService;
import com.sweetcrust.team10_bakery.product.application.ProductQueryService;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
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
    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    public ProductRestController(ProductQueryService productQueryService, ProductCommandService productCommandService) {
        this.productQueryService = productQueryService;
        this.productCommandService = productCommandService;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        List<Product> products = productQueryService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID productId) {
        Product product = productQueryService.getProductById(new ProductId(productId));
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody AddProductCommand addProductCommand) {
        Product product = productCommandService.createProduct(addProductCommand);
        return ResponseEntity.ok(product);
    }
}
