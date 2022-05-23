package com.africa.semicolon.emailService.repository;

import com.africa.semicolon.emailService.model.MailBox;
import com.africa.semicolon.emailService.model.MailBoxes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface MailBoxesRepository extends MongoRepository<MailBoxes, String> {
}
