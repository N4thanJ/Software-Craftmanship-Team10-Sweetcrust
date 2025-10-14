package be.ucll.team10_bakery.order.application;

public class OrderServiceException extends RuntimeException {
    public OrderServiceException(String message) {
        super(message);
    }
}
