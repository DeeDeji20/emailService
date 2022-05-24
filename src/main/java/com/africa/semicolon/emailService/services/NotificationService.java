package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.model.Notifications;

public interface NotificationService {
    void addNotification(String receiverEmail, Notifications notifications);
}
