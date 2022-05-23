package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.exception.UserNotFoundException;
import com.africa.semicolon.emailService.model.Message;
import com.africa.semicolon.emailService.model.Notifications;
import com.africa.semicolon.emailService.model.User;
import com.africa.semicolon.emailService.repository.MessageRepository;
import com.africa.semicolon.emailService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Message sendMessage(CreateMessageDTO createMessageDTO) {
        Message message = Message.builder()
                .sender(createMessageDTO.getSender())
                .receiver(createMessageDTO.getReceiver())
                .localDateTime(createMessageDTO.getLocalDateTime())
                .msgBody(createMessageDTO.getMsgBody())
                .build();
        User recipient = userRepository.findByEmail(createMessageDTO.getReceiver()).orElseThrow(()-> new UserNotFoundException("Receiver not found"));
        Notifications notifications = Notifications.builder()
                .id(message.getMsgId())
                .senderEmail(createMessageDTO.getSender())
                .title("New message alert")
                .message(createMessageDTO.getMsgBody())
                .build();
        recipient.getNotificationList().add(notifications);
        userRepository.save(recipient);

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
