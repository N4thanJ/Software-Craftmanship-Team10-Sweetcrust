package com.sweetcrust.team10_bakery.shared.application.email;

// PATTERN: ADAPTER
// PATTERN: SINGLETON
public interface EmailService {
  void sendEmail(String to, String subject, String body);
}
