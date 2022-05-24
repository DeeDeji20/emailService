package com.africa.semicolon.emailService.repository;

import com.africa.semicolon.emailService.model.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notifications, String> {
}
