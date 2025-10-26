package com.sweetcrust.team10_bakery.order.application;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;

public class OrderServiceException extends ServiceException {

    public OrderServiceException(String field, String message) {
        super(field, message);
    }
}
