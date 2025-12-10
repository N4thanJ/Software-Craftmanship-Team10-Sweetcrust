package com.sweetcrust.team10_bakery.product.presentation;

import com.sweetcrust.team10_bakery.product.application.ProductCommandHandler;
import com.sweetcrust.team10_bakery.product.application.ProductQueryHandler;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductCommand;
import com.sweetcrust.team10_bakery.product.application.commands.AddProductVariantCommand;
import com.sweetcrust.team10_bakery.product.application.commands.MarkProductAvailableCommand;
import com.sweetcrust.team10_bakery.product.application.commands.MarkProductUnavailableCommand;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.presentation.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@Tag(
    name = "Product Management",
    description = "Endpoints related to product management and fetching")
public class ProductRestController {
  private final ProductQueryHandler productQueryHandler;
  private final ProductCommandHandler productCommandHandler;

  public ProductRestController(
      ProductQueryHandler productQueryHandler, ProductCommandHandler productCommandHandler) {
    this.productQueryHandler = productQueryHandler;
    this.productCommandHandler = productCommandHandler;
  }

  @GetMapping()
  public ResponseEntity<Iterable<ProductResponseDto>> getAllProducts() {
    List<ProductResponseDto> products = productQueryHandler.getAllProducts();
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID productId) {
    ProductResponseDto product = productQueryHandler.getProductById(new ProductId(productId));
    return ResponseEntity.ok(product);
  }

  @PostMapping()
  public ResponseEntity<Void> createProduct(@RequestBody AddProductCommand addProductCommand) {
    productCommandHandler.createProduct(addProductCommand);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{productId}")
  public ResponseEntity<Void> addVariantToProduct(
      @PathVariable UUID productId,
      @RequestBody AddProductVariantCommand addProductVariantCommand) {
    productCommandHandler.addVariantToProduct(new ProductId(productId), addProductVariantCommand);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/available")
  public ResponseEntity<Void> markAvailable(
      @RequestBody MarkProductAvailableCommand markProductAvailableCommand) {
    productCommandHandler.markProductAvailable(markProductAvailableCommand);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/unavailable")
  public ResponseEntity<Void> markUnavailable(
      @RequestBody MarkProductUnavailableCommand markProductUnavailableCommand) {
    productCommandHandler.markProductUnavailable(markProductUnavailableCommand);
    return ResponseEntity.noContent().build();
  }
}
