package com.sweetcrust.team10_bakery.shop.application;

import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShopQueryHandler {

  private final ShopRepository shopRepository;

  public ShopQueryHandler(ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }

  public List<Shop> getAllShops() {
    return shopRepository.findAll();
  }

  public Shop getShopById(ShopId shopId) {
    return shopRepository
        .findById(shopId)
        .orElseThrow(() -> new ShopServiceException("shop", "shop not found"));
  }
}
