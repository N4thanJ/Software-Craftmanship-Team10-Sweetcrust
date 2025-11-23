package com.sweetcrust.team10_bakery.shop.application;

import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.application.commands.AddShopCommand;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShopCommandHandlerTest {

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShopCommandHandler shopCommandHandler;

    @Test
    void givenValidAdminUser_whenCreateShop_thenShopIsCreated() {
        // given
        UserId userId = new UserId();
        User adminUser = new User("baker-betty", "SweetDough123!", "betty@bakery.com", UserRole.ADMIN);
        Address address = Address.builder()
                .setStreet("123 Sourdough Street")
                .setCity("Bread City")
                .setPostalCode("12345")
                .setCountry("Baguette Kingdom")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(shopRepository.existsByName("The Rolling Scones")).thenReturn(false);

        AddShopCommand addShopCommand = new AddShopCommand(
                "The Rolling Scones",
                address,
                "hello@rollingscones.com",
                new CountryCode("GB"),
                userId
        );

        // when
        shopCommandHandler.createShop(addShopCommand);

        // then
        verify(shopRepository, times(1)).save(any(Shop.class));
    }

    @Test
    void givenNonexistentUser_whenCreateShop_thenOrderServiceExceptionIsThrown() {
        // given
        UserId userId = new UserId();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Address address = Address.builder()
                .setStreet("123 Sourdough Street")
                .setCity("Bread City")
                .setPostalCode("12345")
                .setCountry("Baguette Kingdom")
                .build();

        AddShopCommand addShopCommand = new AddShopCommand(
                "Crust Almighty",
                address,
                "contact@crustalmighty.com",
                new CountryCode("GB"),
                userId
        );

        // when
        ShopServiceException exception = assertThrows(ShopServiceException.class,
                () -> shopCommandHandler.createShop(addShopCommand));

        // then
        assertEquals("userId", exception.getField());
        assertEquals("User not found", exception.getMessage());
        verify(shopRepository, never()).save(any());
    }

    @Test
    void givenExistingShopName_whenCreateShop_thenShopServiceExceptionIsThrown() {
        // given
        UserId userId = new UserId();
        User adminUser = new User("baker-betty", "SweetDough123!", "betty@bakery.com", UserRole.ADMIN);
        Address address = Address.builder()
                .setStreet("123 Sourdough Street")
                .setCity("Bread City")
                .setPostalCode("12345")
                .setCountry("Baguette Kingdom")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));
        when(shopRepository.existsByName("Dough or Die")).thenReturn(true);

        AddShopCommand addShopCommand = new AddShopCommand(
                "Dough or Die",
                address,
                "info@doughordie.com",
                new CountryCode("US"),
                userId
        );

        // when
        ShopServiceException exception = assertThrows(ShopServiceException.class,
                () -> shopCommandHandler.createShop(addShopCommand));

        // then
        assertEquals("name", exception.getField());
        assertEquals("Shop already exists", exception.getMessage());
        verify(shopRepository, never()).save(any());
    }

    @Test
    void givenNonAdminUser_whenCreateShop_thenShopServiceExceptionIsThrown() {
        // given
        UserId userId = new UserId();
        User regularUser = new User("bread-enthusiast", "ILoveBread123!", "breadlover@bakery.com", UserRole.BAKER);
        Address address = Address.builder()
                .setStreet("123 Sourdough Street")
                .setCity("Bread City")
                .setPostalCode("12345")
                .setCountry("Baguette Kingdom")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(regularUser));
        when(shopRepository.existsByName("Bake Street Boys")).thenReturn(false);

        AddShopCommand addShopCommand = new AddShopCommand(
                "Bake Street Boys",
                address,
                "hello@bakeboys.com",
                new CountryCode("US"),
                userId
        );

        // when
        ShopServiceException exception = assertThrows(ShopServiceException.class,
                () -> shopCommandHandler.createShop(addShopCommand));

        // then
        assertEquals("userRole", exception.getField());
        assertEquals("only admins can create a new shop", exception.getMessage());
        verify(shopRepository, never()).save(any());
    }
}
