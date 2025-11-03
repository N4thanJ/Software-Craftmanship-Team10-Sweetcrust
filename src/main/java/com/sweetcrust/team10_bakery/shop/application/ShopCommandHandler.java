package com.sweetcrust.team10_bakery.shop.application;

import com.sweetcrust.team10_bakery.order.application.OrderServiceException;
import com.sweetcrust.team10_bakery.shop.application.commands.AddShopCommand;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopCommandHandler {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ShopCommandHandler(ShopRepository shopRepository,  UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    public Shop createShop(AddShopCommand addShopCommand) {
        User user = userRepository.findById(addShopCommand.userId())
                .orElseThrow(() -> new ShopServiceException("userId", "User not found"));

        boolean existsByName = shopRepository.existsByName(addShopCommand.name());
        if (existsByName) {
            throw new ShopServiceException("name", "Shop already exists");
        }

        if (user.getRole() != UserRole.ADMIN) {
            throw new ShopServiceException("userRole", "only admins can create a new shop");
        }

        Shop shop = new Shop(
                addShopCommand.name(),
                addShopCommand.shopAddress(),
                addShopCommand.email(),
                addShopCommand.countryCode());

        return shopRepository.save(shop);
    }
}
