package com.sweetcrust.team10_bakery.order.application.events;

import com.sweetcrust.team10_bakery.shared.utils.Event;
import com.sweetcrust.team10_bakery.shared.utils.EventPublisher;
import com.sweetcrust.team10_bakery.shared.utils.Observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderEventPublisher implements EventPublisher {
    private final List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void publish(Event event) {
        for (Observer observer : observers) {
            observer.onEvent(event);
        }
    }
    
}