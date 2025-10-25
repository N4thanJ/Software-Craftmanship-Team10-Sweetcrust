package com.sweetcrust.team10_bakery.product.presentation;

import com.sweetcrust.team10_bakery.product.application.ProductCommandService;
import com.sweetcrust.team10_bakery.product.application.ProductQueryService;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody AddProductCommand addProductCommand) {
        Product product = productCommandService.createProduct(addProductCommand);
        return ResponseEntity.ok(product);
    }


}
