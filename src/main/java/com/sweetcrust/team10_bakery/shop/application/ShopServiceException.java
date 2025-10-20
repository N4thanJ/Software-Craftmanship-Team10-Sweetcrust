package com.sweetcrust.team10_bakery.shop.application;

public class ShopServiceException extends RuntimeException {

    private final String field;

    public ShopServiceException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}
