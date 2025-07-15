package com.btg.pactual.service.notification;

public interface INotificationService {
    void sendNotify(String email, String phone, String type, String fundName);
}
