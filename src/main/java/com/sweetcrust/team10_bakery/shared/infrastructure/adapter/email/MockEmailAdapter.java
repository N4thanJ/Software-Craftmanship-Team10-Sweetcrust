package com.sweetcrust.team10_bakery.shared.infrastructure.adapter.email;

import com.sweetcrust.team10_bakery.shared.application.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// ADAPTER
@Component
public class MockEmailAdapter implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockEmailAdapter.class);

    @Override
    public void sendEmail(String to, String subject, String body) {
        LOGGER.info(
                """
                        
                        ===== MOCK EMAIL =====\
                        
                        To: {}\
                        
                        Subject: {}\
                        
                        Body:
                        {}\
                        
                        =====================""",
                to, subject, body
        );
    }
}
