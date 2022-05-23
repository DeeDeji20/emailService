package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.model.Message;
import com.africa.semicolon.emailService.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Message sendMessage(CreateMessageDTO createMessageDTO) {
        Message message = Message.builder()
                .sender(createMessageDTO.getSender())
                .receiver(createMessageDTO.getReceiver())
                .localDateTime(createMessageDTO.getLocalDateTime())
                .msgBody(createMessageDTO.getMsgBody())
                .build();
        return messageRepository.save(message);
    }

//    @Override
//    public MessageDTO sendMessageToUser(CreateMessageDTO createMessageDTO) {
//        Message message = Message.builder()
//                .sender(createMessageDTO.getSender())
//                .receiver(createMessageDTO.getReceiver())
//                .localDateTime(createMessageDTO.getLocalDateTime())
//                .msgBody(createMessageDTO.getMsgBody())
//                .build();
//        return null;
//    }
}
