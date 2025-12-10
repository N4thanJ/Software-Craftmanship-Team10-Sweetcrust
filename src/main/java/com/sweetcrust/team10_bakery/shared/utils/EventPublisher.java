package com.sweetcrust.team10_bakery.shared.utils;

public interface EventPublisher {
  public void publish(Event event);

  public void subscribe(Observer observer);

  public void unsubscribe(Observer observer);
}
