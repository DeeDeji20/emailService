package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.model.Message;

import java.util.List;

public interface MessageService {
    String sendMessage(CreateMessageDTO message);
    void readMessage(String messageId);
    List<Message> findMessage(String email, String msgBody);
}
