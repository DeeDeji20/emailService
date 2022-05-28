package com.africa.semicolon.emailService.repository;

import com.africa.semicolon.emailService.model.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notifications, String> {

    Optional<Notifications> findNotificationsByMessage(String s);
}
