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
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
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


        ProductCategory productCategory1 = new ProductCategory("Cakes", "A variety of cakes");
        ProductCategory productCategory2 = new ProductCategory("Candies", "Sweet candies");

        ProductVariant productVariant1 = new ProductVariant("Small", BigDecimal.valueOf(.3));
        ProductVariant productVariant2 = new ProductVariant("Medium", BigDecimal.valueOf(.5));

        Product curryCake = new Product(
                "Curry Cake Delux",
                "A very delicious originated from India dish",
                BigDecimal.valueOf(4.99),
                true,
                productVariant1.getVariantId(),
                productCategory1.getCategoryId());

        Product candyBag = new Product("Candy Bag", "A bag full of assorted candies", BigDecimal.valueOf(2.99), true, productVariant2.getVariantId(), productCategory2.getCategoryId());

        productRepository.save(curryCake);
        productRepository.save(candyBag);

        User user1 = new User("Curry Lover", "IloveCurry123!", "curry.lover@sweetcrust.in", UserRole.CUSTOMER);
        User user2 = new User("Candy Lover", "IloveCandy123!", "candy.lover@sweetcrust.in", UserRole.CUSTOMER);

        userRepository.save(user1);
        userRepository.save(user2);

        Shop indiaShop = new Shop("SweetCrust New Delhi", new ShopAddress("Currystreet 1", "New Delhi", "110001", "India"), "newdelhi@sweetcrust.com");
        Shop deutscheShop = new Shop("SweetCrust Berlin", new ShopAddress("Brandenburg 1", "Berlin", "10117", "Germany"), "berlin@sweetcrust.com");

        shopRepository.save(indiaShop);
        shopRepository.save(deutscheShop);

        Order b2cOrder = Order.createB2C(OrderType.B2C, new DeliveryAddress("CurryStreet", "CurryCity", "12345", "CurryCountry"), LocalDateTime.now().plusDays(2), user1.getUserId());
        Order b2bOrder = Order.createB2B(OrderType.B2B, LocalDateTime.now().plusDays(5), indiaShop.getShopId(), deutscheShop.getShopId());

        OrderItem b2cOrderItem = new OrderItem(curryCake.getProductId(), productVariant1.getVariantId(), 2, BigDecimal.valueOf(4.99));
        OrderItem b2bOrderItem = new OrderItem(candyBag.getProductId(), productVariant2.getVariantId(), 5, BigDecimal.valueOf(2.99));

        b2cOrder.addOrderItem(b2cOrderItem);
        b2bOrder.addOrderItem(b2bOrderItem);

        orderRepository.save(b2cOrder);
        orderRepository.save(b2bOrder);
    }
}
