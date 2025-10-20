package com.sweetcrust.team10_bakery.shop.domain;

public class ShopDomainException extends RuntimeException {

    private final String field;

    public ShopDomainException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}
