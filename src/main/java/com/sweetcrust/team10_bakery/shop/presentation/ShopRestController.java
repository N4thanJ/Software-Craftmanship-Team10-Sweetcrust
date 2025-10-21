package com.sweetcrust.team10_bakery.shop.presentation;

import com.sweetcrust.team10_bakery.shop.application.ShopCommandService;
import com.sweetcrust.team10_bakery.shop.application.ShopQueryService;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
public class ShopRestController {
    private final ShopCommandService shopCommandService;
    private final ShopQueryService shopQueryService;

    public ShopRestController(ShopCommandService shopCommandService, ShopQueryService shopQueryService) {
        this.shopCommandService = shopCommandService;
        this.shopQueryService = shopQueryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Shop>> findAll() {
        List<Shop> shops = shopQueryService.getAllShops();
        return ResponseEntity.ok(shops);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<Shop> getShopById(@PathVariable UUID shopId) {
        Shop shop = shopQueryService.getShopById(new ShopId(shopId));
        return ResponseEntity.ok(shop);
    }
}
