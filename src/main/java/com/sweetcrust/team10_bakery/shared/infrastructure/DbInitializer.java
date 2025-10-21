package com.sweetcrust.team10_bakery.shared.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.DeliveryAddress;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
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

    public DbInitializer(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    private void clearAll() {
        productRepository.deleteAll();
    }

    @PostConstruct
    public void init() {
        clearAll();


        ProductCategory productCategory = new ProductCategory("Cakes", "A variety of cakes");

        Product curryCake = new Product("Curry Cake Delux", "A very delicious originated from India dish",
                BigDecimal.valueOf(4.99), true,
                productCategory.getCategoryId());

        productRepository.save(curryCake);

        User user1 = new User("Curry Lover", "IloveCurry123!", "curry.lover@sweetcrust.in", UserRole.CUSTOMER);

        userRepository.save(user1);

        Order b2cOrder = Order.createB2C(OrderType.B2C, new DeliveryAddress("CurryStreet", "CurryCity", "12345", "CurryCountry"), LocalDateTime.now().plusDays(2), user1.getUserId());
        Order b2bOrder = Order.createB2B(OrderType.B2B, LocalDateTime.now().plusDays(5), null, null);


    }
}
