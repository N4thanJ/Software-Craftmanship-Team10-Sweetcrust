package com.sweetcrust.team10_bakery.shop.domain;

import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    @Test
    void givenValidData_whenCreatingShop_thenShopIsCreated() {
        // given
        String name = "The Rolling Scones";
        Address address = new Address("42 Doughnut Drive", "Crumbsville", "12345", "Bakeland");
        String email = "amsterdam@sweetcrust.nl";

        // when
        Shop shop = new Shop(name, address, email);

        // then
        assertNotNull(shop);
        assertNotNull(shop.getShopId());
        assertEquals(name, shop.getName());
        assertEquals(address, shop.getAddress());
        assertEquals(email, shop.getEmail());
    }

    @Test
    void givenNullName_whenCreatingShop_thenThrowsException() {
        // given
        Address address = new Address("10 Croissant Crescent", "Pastrytown", "54321", "Bakeland");
        String email = "rome@sweetcrust.it";

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new Shop(null, address, email));

        // then
        assertEquals("shopName", exception.getField());
        assertEquals("name should not be null", exception.getMessage());
    }

    @Test
    void givenBlankName_whenCreatingShop_thenThrowsException() {
        // given
        Address address = new Address("9 Pretzel Place", "Twistopolis", "67890", "Bakeland");
        String email = "athens@sweetcrust.gr";

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new Shop("   ", address, email));

        // then
        assertEquals("shopName", exception.getField());
        assertEquals("name should not be null", exception.getMessage());
    }

    @Test
    void givenNullAddress_whenCreatingShop_thenThrowsException() {
        // given
        String name = "Bun Intended";
        String email = "stockholm@sweetcrust.se";

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new Shop(name, null, email));

        // then
        assertEquals("shopAddress", exception.getField());
        assertEquals("address should not be null", exception.getMessage());
    }

    @Test
    void givenNullEmail_whenCreatingShop_thenThrowsException() {
        // given
        Address address = new Address("7 Bagel Boulevard", "Yeast City", "99999", "Bakeland");

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new Shop("Bready or Not", address, null));

        // then
        assertEquals("email", exception.getField());
        assertEquals("email should not be null", exception.getMessage());
    }

    @Test
    void givenBlankEmail_whenCreatingShop_thenThrowsException() {
        // given
        Address address = new Address("13 Cruller Court", "Dough City", "11223", "Bakeland");

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new Shop("Bake It Easy", address, "   "));

        // then
        assertEquals("email", exception.getField());
        assertEquals("email should not be null", exception.getMessage());
    }

    @Test
    void givenInvalidEmailWithoutAtSymbol_whenCreatingShop_thenThrowsException() {
        // given
        Address address = new Address("15 Biscuit Boulevard", "Butterburg", "45678", "Bakeland");

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new Shop("The Bread Awakens", address, "berlinsweetcrust.de"));

        // then
        assertEquals("email", exception.getField());
        assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void givenInvalidEmailWithoutDomain_whenCreatingShop_thenThrowsException() {
        // given
        Address address = new Address("21 Scone Street", "Jamestown", "55555", "Bakeland");

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new Shop("Planet of the Crepes", address, "crepes@"));

        // then
        assertEquals("email", exception.getField());
        assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void givenValidEmail_whenCreatingShop_thenShopIsCreated() {
        // given
        Address address = new Address("88 Tart Terrace", "Pufftown", "20202", "Bakeland");
        String email = "barcelona@sweetcrust.es";

        // when
        Shop shop = new Shop("Flour Power", address, email);

        // then
        assertNotNull(shop);
        assertEquals(email, shop.getEmail());
    }

    @Test
    void givenValidCountryCode_whenSettingCountryCode_thenCountryCodeIsSet() {
        // given
        Address address = new Address("100 Donut Drive", "Sprinkleville", "60606", "Bakeland");
        Shop shop = new Shop("Holy Sheet Cakes", address, "newdelhi@sweetcrust.in");
        CountryCode countryCode = new CountryCode("US");

        // when
        shop.setCountryCode(countryCode);

        // then
        assertEquals(countryCode, shop.getCountryCode());
    }

    @Test
    void givenNullCountryCode_whenSettingCountryCode_thenThrowsException() {
        // given
        Address address = new Address("22 Muffin Mile", "Cupcake City", "80808", "Bakeland");
        Shop shop = new Shop("Dough My Gosh", address, "miami@sweetcrust.us");

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> shop.setCountryCode(null));

        // then
        assertEquals("countryCode", exception.getField());
        assertEquals("countryCode should not be null", exception.getMessage());
    }

    @Test
    void givenShopWithValidAddress_whenAccessingValues_thenTheyMatch() {
        // given
        Address address = new Address("123 Cupcake Avenue", "Frostville", "34567", "Bakeland");
        Shop shop = new Shop("Bake Street Boys", address, "leuven@sweetcrust.be");

        // then
        assertEquals("123 Cupcake Avenue", shop.getAddress().street());
        assertEquals("Frostville", shop.getAddress().city());
        assertEquals("34567", shop.getAddress().postalCode());
        assertEquals("Bakeland", shop.getAddress().country());
    }
}
