package com.btg.pactual.service.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements INotificationService {
    @Override
    public void sendNotify(String email, String phone, String type, String fundName) {
        if ("EMAIL".equalsIgnoreCase(type)) {
            System.out.println("Sending EMAIL to " + email + " for fund " + fundName);
        } else if ("SMS".equalsIgnoreCase(type)) {
            System.out.println("Sending SMS to " + phone + " for fund " + fundName);
        }
    }
}
