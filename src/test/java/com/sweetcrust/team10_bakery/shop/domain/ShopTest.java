package com.sweetcrust.team10_bakery.shop.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopTest {

  private Address defaultAddress;
  private CountryCode defaultCountryCode;
  private UserId defaultOwnerId;
  private String defaultName;
  private String defaultEmail;

  @BeforeEach
  void setup() {
    defaultAddress =
        Address.builder()
            .setStreet("Default Street")
            .setCity("Default City")
            .setPostalCode("00000")
            .setCountry("Defaultland")
            .build();

    defaultCountryCode = new CountryCode("FR");
    defaultOwnerId = new UserId();
    defaultName = "Default Shop";
    defaultEmail = "default@sweetcrust.com";
  }

  private Shop createShop() {
    return new Shop(defaultName, defaultAddress, defaultEmail, defaultCountryCode, defaultOwnerId);
  }

  private void expectFieldError(String field, String message, Runnable action) {
    ShopDomainException ex = assertThrows(ShopDomainException.class, action::run);
    assertEquals(field, ex.getField());
    assertEquals(message, ex.getMessage());
  }

  @Test
  void givenValidData_whenCreatingShop_thenShopIsCreated() {
    // when
    Shop shop = createShop();

    // then
    assertNotNull(shop);
    assertNotNull(shop.getShopId());
    assertEquals(defaultName, shop.getName());
    assertEquals(defaultAddress, shop.getAddress());
    assertEquals(defaultEmail, shop.getEmail());
  }

  @Test
  void givenNullName_whenCreatingShop_thenThrowsException() {
    defaultName = null;

    expectFieldError("shopName", "name should not be null", this::createShop);
  }

  @Test
  void givenBlankName_whenCreatingShop_thenThrowsException() {
    defaultName = "   ";

    expectFieldError("shopName", "name should not be null", this::createShop);
  }

  @Test
  void givenNullAddress_whenCreatingShop_thenThrowsException() {
    defaultAddress = null;

    expectFieldError("shopAddress", "address should not be null", this::createShop);
  }

  @Test
  void givenNullEmail_whenCreatingShop_thenThrowsException() {
    defaultEmail = null;

    expectFieldError("email", "email should not be null", this::createShop);
  }

  @Test
  void givenBlankEmail_whenCreatingShop_thenThrowsException() {
    defaultEmail = "   ";

    expectFieldError("email", "email should not be null", this::createShop);
  }

  @Test
  void givenEmailWithoutAtSymbol_whenCreatingShop_thenThrowsException() {
    defaultEmail = "invalidemail.com";

    expectFieldError("email", "invalid email", this::createShop);
  }

  @Test
  void givenEmailWithoutDomain_whenCreatingShop_thenThrowsException() {
    defaultEmail = "test@";

    expectFieldError("email", "invalid email", this::createShop);
  }

  @Test
  void givenNullCountryCode_whenCreatingShop_thenThrowsException() {
    defaultCountryCode = null;

    expectFieldError("countryCode", "countryCode should not be null", this::createShop);
  }

  @Test
  void givenNullOwnerId_whenCreatingShop_thenThrowsException() {
    defaultOwnerId = null;

    expectFieldError("ownerId", "ownerId should not be null", this::createShop);
  }

  @Test
  void givenShopWithValidAddress_whenAccessingValues_thenTheyMatch() {
    // given
    defaultAddress =
        Address.builder()
            .setStreet("123 Cupcake Avenue")
            .setCity("Frostville")
            .setPostalCode("34567")
            .setCountry("Bakeland")
            .build();

    // when
    Shop shop = createShop();

    // then
    assertEquals("123 Cupcake Avenue", shop.getAddress().getStreet());
    assertEquals("Frostville", shop.getAddress().getCity());
    assertEquals("34567", shop.getAddress().getPostalCode());
    assertEquals("Bakeland", shop.getAddress().getCountry());
  }
}
