package com.africa.semicolon.emailService.repository;

import com.africa.semicolon.emailService.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findMessageByReceiver(String email);
}
