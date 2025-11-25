package com.sweetcrust.team10_bakery.shop.presentation;

import com.sweetcrust.team10_bakery.shop.application.ShopCommandHandler;
import com.sweetcrust.team10_bakery.shop.application.ShopQueryHandler;
import com.sweetcrust.team10_bakery.shop.application.commands.AddShopCommand;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
@Tag(name = "Shop Management", description = "Endpoints related to shop management and fetching")
public class ShopRestController {

  private final ShopCommandHandler shopCommandHandler;
  private final ShopQueryHandler shopQueryHandler;

  public ShopRestController(
      ShopCommandHandler shopCommandHandler, ShopQueryHandler shopQueryHandler) {
    this.shopCommandHandler = shopCommandHandler;
    this.shopQueryHandler = shopQueryHandler;
  }

  @GetMapping
  public ResponseEntity<Iterable<Shop>> findAll() {
    List<Shop> shops = shopQueryHandler.getAllShops();
    return ResponseEntity.ok(shops);
  }

  @GetMapping("/{shopId}")
  public ResponseEntity<Shop> getShopById(@PathVariable UUID shopId) {
    Shop shop = shopQueryHandler.getShopById(new ShopId(shopId));
    return ResponseEntity.ok(shop);
  }

  @PostMapping
  public ResponseEntity<Shop> createShop(@RequestBody AddShopCommand addShopCommand) {
    Shop shop = shopCommandHandler.createShop(addShopCommand);
    return ResponseEntity.ok(shop);
  }
}
