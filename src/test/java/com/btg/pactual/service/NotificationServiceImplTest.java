package com.btg.pactual.service;

import com.btg.pactual.service.notification.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificationServiceImplTest {
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl();
    }

    @Test
    void testSendNotify_email() {
        notificationService.sendNotify("test@mail.com", "123456", "EMAIL", "Fund1");
    }

    @Test
    void testSendNotify_sms() {
        notificationService.sendNotify("test@mail.com", "123456", "SMS", "Fund1");
    }

    @Test
    void testSendNotify_invalidType() {
        notificationService.sendNotify("test@mail.com", "123456", "WHATSAPP", "Fund1");
    }
}
