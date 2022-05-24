package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.model.Notifications;
import com.africa.semicolon.emailService.model.User;
import com.africa.semicolon.emailService.repository.NotificationRepository;
import com.africa.semicolon.emailService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public void addNotification(String recceiverEmail,Notifications notifications) {
        User user = userRepository.findByEmail(recceiverEmail).orElseThrow(()-> {throw new IllegalArgumentException("Not found");});
        user.getNotificationList().add(notifications);
        notificationRepository.save(notifications);
    }
}
