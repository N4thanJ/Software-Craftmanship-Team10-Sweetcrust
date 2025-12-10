package com.sweetcrust.team10_bakery.product.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;
import com.sweetcrust.team10_bakery.product.presentation.dto.ProductResponseDto;
import com.sweetcrust.team10_bakery.product.presentation.dto.ProductVariantResponseDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductQueryHandlerTest {

  @Mock private ProductRepository productRepository;

  @InjectMocks private ProductQueryHandler productQueryHandler;

  @Test
  void givenGetAllProducts_whenGettingAllProducts_thenAllProductsAreReturned() {
    // given
    ProductCategory pastryCategory = new ProductCategory("Koffiekoeken", "Chocokoek heaven");

    Product product =
        new Product(
            "Choco Croissant",
            "Fluffy, buttery, chocolate-filled pastry",
            BigDecimal.valueOf(2.99),
            true,
            pastryCategory.getCategoryId());

    ProductVariant variant =
        new ProductVariant(
            ProductSize.REGULAR, "Mini Choco", BigDecimal.valueOf(0.5), product.getProductId());
    product.addVariant(variant);

    when(productRepository.findAll()).thenReturn(List.of(product));

    // when
    List<ProductResponseDto> productDtos = productQueryHandler.getAllProducts();

    // then
    assertNotNull(productDtos);
    assertEquals(1, productDtos.size());

    ProductResponseDto dto = productDtos.get(0);
    assertEquals(product.getProductId(), dto.productId());
    assertEquals("Choco Croissant", dto.name());
    assertEquals("Fluffy, buttery, chocolate-filled pastry", dto.description());
    assertEquals(BigDecimal.valueOf(2.99), dto.basePrice());
    assertTrue(dto.available());
    assertEquals(pastryCategory.getCategoryId(), dto.categoryId());

    // variants
    assertNotNull(dto.variants());
    assertEquals(1, dto.variants().size());
    ProductVariantResponseDto variantDto = dto.variants().get(0);
    assertEquals(variant.getVariantId(), variantDto.variantId());
    assertEquals(ProductSize.REGULAR, variantDto.size());
    assertEquals("Mini Choco", variantDto.variantName());
    assertEquals(BigDecimal.valueOf(0.5), variantDto.priceModifier());
  }

  @Test
  void givenGetProductById_whenProductExists_thenProductIsReturned() {
    // given
    ProductCategory pastryCategory = new ProductCategory("Koffiekoeken", "Chocokoek heaven");

    Product product =
        new Product(
            "Choco Pain Au Chocolat",
            "Buttery, chocolate-filled delight",
            BigDecimal.valueOf(3.5),
            true,
            pastryCategory.getCategoryId());

    ProductVariant variant =
        new ProductVariant(
            ProductSize.LARGE, "Choco XL", BigDecimal.valueOf(1.0), product.getProductId());
    product.addVariant(variant);

    when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

    // when
    ProductResponseDto dto = productQueryHandler.getProductById(product.getProductId());

    // then
    assertNotNull(dto);
    assertEquals(product.getProductId(), dto.productId());
    assertEquals("Choco Pain Au Chocolat", dto.name());
    assertEquals(1, dto.variants().size());

    ProductVariantResponseDto variantDto = dto.variants().get(0);
    assertEquals(variant.getVariantId(), variantDto.variantId());
    assertEquals(ProductSize.LARGE, variantDto.size());
    assertEquals("Choco XL", variantDto.variantName());
  }

  @Test
  void givenGetProductById_whenProductDoesNotExist_thenThrowsException() {
    // given
    ProductId nonExistingId = new ProductId();
    when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

    // when / then
    Exception ex =
        assertThrows(Exception.class, () -> productQueryHandler.getProductById(nonExistingId));

    assertTrue(ex.getMessage().contains("product not found"));
  }
}
