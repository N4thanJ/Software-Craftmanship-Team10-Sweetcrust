package com.sweetcrust.team10_bakery.product.application;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;

public class ProductServiceException extends ServiceException {

    public ProductServiceException(String field, String message) {
        super(field, message);
    }
}
