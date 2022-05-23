package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.model.Message;

public interface MessageService {
    Message sendMessage(CreateMessageDTO message);
//    MessageDTO sendMessageToUser(CreateMessageDTO message);
}
