package com.sweetcrust.team10_bakery.shared.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.entities.OrderItem;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.DeliveryAddress;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopAddress;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import org.springframework.stereotype.Component;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public DbInitializer(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository, ShopRepository shopRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
    }

    private void clearAll() {
        productRepository.deleteAll();
    }

    @PostConstruct
    public void init() {
        clearAll();


        ProductCategory productCategory = new ProductCategory("Cakes", "A variety of cakes");

        Product curryCake = new Product("Curry Cake Delux", "A very delicious originated from India dish", BigDecimal.valueOf(4.99), true, productCategory.getCategoryId());

        productRepository.save(curryCake);

        User user1 = new User("Curry Lover", "IloveCurry123!", "curry.lover@sweetcrust.in", UserRole.CUSTOMER);

        userRepository.save(user1);

        Shop indiaShop = new Shop("SweetCrust New Delhi", new ShopAddress("Currystreet 1", "New Delhi", "110001", "India"), "newdelhi@sweetcrust.com");
        Shop deutscheShop = new Shop("SweetCrust Berlin", new ShopAddress("Brandenburg 1", "Berlin", "10117", "Germany"), "berlin@sweetcrust.com");

        shopRepository.save(indiaShop);
        shopRepository.save(deutscheShop);

        Order b2cOrder = Order.createB2C(OrderType.B2C, new DeliveryAddress("CurryStreet", "CurryCity", "12345", "CurryCountry"), LocalDateTime.now().plusDays(2), user1.getUserId());
        Order b2bOrder = Order.createB2B(OrderType.B2B, LocalDateTime.now().plusDays(5), indiaShop.getShopId(), deutscheShop.getShopId());

        OrderItem orderItem1 = new OrderItem(curryCake.getProductId(), null, 2, curryCake.getPrice());

        b2bOrder.addOrderItem(curryCake.getProductId(), 100);

        orderRepository.save(b2cOrder);
        orderRepository.save(b2bOrder);


    }
}
