package com.sweetcrust.team10_bakery.shared.domain.valueobjects;

import com.sweetcrust.team10_bakery.shared.domain.SharedDomainException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

  private String street;
  private String city;
  private String postalCode;
  private String country;

  protected Address() {}

  // BUILDER PATTERN
  private Address(AddressBuilder builder) {
    this.street = builder.street;
    this.city = builder.city;
    this.postalCode = builder.postalCode;
    this.country = builder.country;

    if (street == null || street.isBlank()) {
      throw new SharedDomainException("Address", "street should not be blank or null");
    }
    if (city == null || city.isBlank()) {
      throw new SharedDomainException("Address", "city should not be blank or null");
    }
    if (postalCode == null || postalCode.isBlank()) {
      throw new SharedDomainException("Address", "postalCode should not be blank or null");
    }
    if (country == null || country.isBlank()) {
      throw new SharedDomainException("Address", "country should not be blank or null");
    }
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCountry() {
    return country;
  }

  // BUILDER PATTERN
  public static class AddressBuilder {
    private String street;
    private String city;
    private String postalCode;
    private String country;

    public AddressBuilder setStreet(String street) {
      this.street = street;
      return this;
    }

    public AddressBuilder setCity(String city) {
      this.city = city;
      return this;
    }

    public AddressBuilder setPostalCode(String postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public AddressBuilder setCountry(String country) {
      this.country = country;
      return this;
    }

    public Address build() {
      return new Address(this);
    }
  }

  public static AddressBuilder builder() {
    return new AddressBuilder();
  }
}
