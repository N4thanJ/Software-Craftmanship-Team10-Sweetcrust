package com.sweetcrust.team10_bakery.shop.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import org.junit.jupiter.api.Test;

public class ShopTest {

  @Test
  void givenValidData_whenCreatingShop_thenShopIsCreated() {
    // given
    String name = "The Rolling Scones";
    Address address =
        Address.builder()
            .setStreet("42 Doughnut Drive")
            .setCity("Crumbsville")
            .setPostalCode("12345")
            .setCountry("Bakeland")
            .build();
    String email = "amsterdam@sweetcrust.nl";
    CountryCode countryCode = new CountryCode("FR");

    // when
    Shop shop = new Shop(name, address, email, countryCode);

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
    Address address =
        Address.builder()
            .setStreet("10 Croissant Crescent")
            .setCity("Pastrytown")
            .setPostalCode("54321")
            .setCountry("Bakeland")
            .build();
    String email = "rome@sweetcrust.it";
    CountryCode countryCode = new CountryCode("FR");

    // when
    ShopDomainException exception =
        assertThrows(ShopDomainException.class, () -> new Shop(null, address, email, countryCode));

    // then
    assertEquals("shopName", exception.getField());
    assertEquals("name should not be null", exception.getMessage());
  }

  @Test
  void givenBlankName_whenCreatingShop_thenThrowsException() {
    // given
    Address address =
        Address.builder()
            .setStreet("9 Pretzel Place")
            .setCity("Twistopolis")
            .setPostalCode("67890")
            .setCountry("Bakeland")
            .build();
    String email = "athens@sweetcrust.gr";
    CountryCode countryCode = new CountryCode("FR");

    // when
    ShopDomainException exception =
        assertThrows(ShopDomainException.class, () -> new Shop("   ", address, email, countryCode));

    // then
    assertEquals("shopName", exception.getField());
    assertEquals("name should not be null", exception.getMessage());
  }

  @Test
  void givenNullAddress_whenCreatingShop_thenThrowsException() {
    // given
    String name = "Bun Intended";
    String email = "stockholm@sweetcrust.se";
    CountryCode countryCode = new CountryCode("FR");

    // when
    ShopDomainException exception =
        assertThrows(ShopDomainException.class, () -> new Shop(name, null, email, countryCode));

    // then
    assertEquals("shopAddress", exception.getField());
    assertEquals("address should not be null", exception.getMessage());
  }

  @Test
  void givenNullEmail_whenCreatingShop_thenThrowsException() {
    // given
    Address address =
        Address.builder()
            .setStreet("7 Bagel Boulevard")
            .setCity("Yeast City")
            .setPostalCode("99999")
            .setCountry("Bakeland")
            .build();
    CountryCode countryCode = new CountryCode("FR");

    // when
    ShopDomainException exception =
        assertThrows(
            ShopDomainException.class, () -> new Shop("Bready or Not", address, null, countryCode));

    // then
    assertEquals("email", exception.getField());
    assertEquals("email should not be null", exception.getMessage());
  }

  @Test
  void givenBlankEmail_whenCreatingShop_thenThrowsException() {
    // given
    Address address =
        Address.builder()
            .setStreet("13 Cruller Court")
            .setCity("Dough City")
            .setPostalCode("11223")
            .setCountry("Bakeland")
            .build();
    CountryCode countryCode = new CountryCode("FR");

    // when
    ShopDomainException exception =
        assertThrows(
            ShopDomainException.class, () -> new Shop("Bake It Easy", address, "   ", countryCode));

    // then
    assertEquals("email", exception.getField());
    assertEquals("email should not be null", exception.getMessage());
  }

  @Test
  void givenInvalidEmailWithoutAtSymbol_whenCreatingShop_thenThrowsException() {
    // given
    Address address =
        Address.builder()
            .setStreet("15 Biscuit Boulevard")
            .setCity("Butterburg")
            .setPostalCode("45678")
            .setCountry("Bakeland")
            .build();
    CountryCode countryCode = new CountryCode("FR");

    // when
    ShopDomainException exception =
        assertThrows(
            ShopDomainException.class,
            () -> new Shop("The Bread Awakens", address, "berlinsweetcrust.de", countryCode));

    // then
    assertEquals("email", exception.getField());
    assertEquals("invalid email", exception.getMessage());
  }

  @Test
  void givenInvalidEmailWithoutDomain_whenCreatingShop_thenThrowsException() {
    // given
    Address address =
        Address.builder()
            .setStreet("21 Scone Street")
            .setCity("Jamestown")
            .setPostalCode("55555")
            .setCountry("Bakeland")
            .build();
    CountryCode countryCode = new CountryCode("FR");

    // when
    ShopDomainException exception =
        assertThrows(
            ShopDomainException.class,
            () -> new Shop("Planet of the Crepes", address, "crepes@", countryCode));

    // then
    assertEquals("email", exception.getField());
    assertEquals("invalid email", exception.getMessage());
  }

  @Test
  void givenValidEmail_whenCreatingShop_thenShopIsCreated() {
    // given
    Address address =
        Address.builder()
            .setStreet("88 Tart Terrace")
            .setCity("Pufftown")
            .setPostalCode("20202")
            .setCountry("Bakeland")
            .build();
    String email = "barcelona@sweetcrust.es";
    CountryCode countryCode = new CountryCode("FR");

    // when
    Shop shop = new Shop("Flour Power", address, email, countryCode);

    // then
    assertNotNull(shop);
    assertEquals(email, shop.getEmail());
  }

  @Test
  void givenNullCountryCode_whenSettingCountryCode_thenThrowsException() {
    // given
    Address address =
        Address.builder()
            .setStreet("22 Muffin Mile")
            .setCity("Cupcake City")
            .setPostalCode("80808")
            .setCountry("Bakeland")
            .build();

    // when
    ShopDomainException exception =
        assertThrows(
            ShopDomainException.class,
            () -> new Shop("Dough My Gosh", address, "miami@sweetcrust.us", null));

    // then
    assertEquals("countryCode", exception.getField());
    assertEquals("countryCode should not be null", exception.getMessage());
  }

  @Test
  void givenShopWithValidAddress_whenAccessingValues_thenTheyMatch() {
    // given
    Address address =
        Address.builder()
            .setStreet("123 Cupcake Avenue")
            .setCity("Frostville")
            .setPostalCode("34567")
            .setCountry("Bakeland")
            .build();
    CountryCode countryCode = new CountryCode("FR");
    Shop shop = new Shop("Bake Street Boys", address, "leuven@sweetcrust.be", countryCode);

    // then
    assertEquals("123 Cupcake Avenue", shop.getAddress().getStreet());
    assertEquals("Frostville", shop.getAddress().getCity());
    assertEquals("34567", shop.getAddress().getPostalCode());
    assertEquals("Bakeland", shop.getAddress().getCountry());
  }
}
