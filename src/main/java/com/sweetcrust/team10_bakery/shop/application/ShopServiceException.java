package com.sweetcrust.team10_bakery.shop.application;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;

public class ShopServiceException extends ServiceException {

    public ShopServiceException(String field, String message) {
        super(field, message);
    }
}
