package com.sweetcrust.team10_bakery.shared.application.email;

// ADAPTER
public interface EmailService {
  void sendEmail(String to, String subject, String body);
}
