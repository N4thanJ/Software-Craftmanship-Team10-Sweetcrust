package com.sweetcrust.team10_bakery.shop.domain.entities;

import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.ShopDomainException;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "shops")
public class Shop {

    @EmbeddedId
    private ShopId shopId;

    private String name;

    @Embedded
    private Address address;

    private String email;

    @Embedded
    private CountryCode countryCode;

    protected Shop() {
    }

    public Shop(String name, Address address, String email, CountryCode countryCode) {
        this.shopId = new ShopId();
        setName(name);
        setAddress(address);
        setEmail(email);
        setCountryCode(countryCode);
    }

    public ShopId getShopId() {
        return shopId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ShopDomainException("shopName", "name should not be null");
        }
        this.name = name;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new ShopDomainException("shopAddress", "address should not be null");
        }
        this.address = address;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ShopDomainException("email", "email should not be null");
        }
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ShopDomainException("email", "invalid email");
        }
        this.email = email;
    }

    public void setCountryCode(CountryCode countryCode) {
        if (countryCode == null) {
            throw new ShopDomainException("countryCode", "countryCode should not be null");
        }
        this.countryCode = countryCode;
    }
}
