package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.exception.UserNotFoundException;
import com.africa.semicolon.emailService.model.*;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import com.africa.semicolon.emailService.repository.MessageRepository;
import com.africa.semicolon.emailService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailBoxesRepository mailBoxesRepository;




    @Override
    public Message sendMessage(CreateMessageDTO createMessageDTO) {
        Message message = Message.builder()
                .sender(createMessageDTO.getSender())
                .receiver(createMessageDTO.getReceiver())
                .localDateTime(createMessageDTO.getLocalDateTime())
                .msgBody(createMessageDTO.getMsgBody())
                .build();

        addMessageToReceiversInbox(createMessageDTO, message);

        if (!message.getSender().equals("mailSender")) {
            addMessageToSendersOutbox(createMessageDTO, message);
        }

        addNotification(createMessageDTO);

        return messageRepository.save(message);
    }

    private void addMessageToSendersOutbox(CreateMessageDTO createMessageDTO, Message message) {
        MailBoxes senderMailBox = mailBoxesRepository.findById(createMessageDTO.getSender()).orElseThrow(() -> {
            throw new UserNotFoundException("not found");
        });
        senderMailBox.getMailboxes().forEach((mailBox) -> {
            if (mailBox.getType().equals(MailBoxType.SENT)){
                mailBox.getMessages().add(message);
                mailBoxesRepository.save(senderMailBox);
            }
        });
    }

    private void addMessageToReceiversInbox(CreateMessageDTO createMessageDTO, Message message) {
        MailBoxes receiverMailBoxes = mailBoxesRepository.findById(createMessageDTO.getReceiver()).orElseThrow(()-> {throw new UserNotFoundException("not found");});
        receiverMailBoxes.getMailboxes().forEach((mailBox)->{
            if (mailBox.getType().equals(MailBoxType.INBOX)){
                mailBox.getMessages().add(message);
                mailBoxesRepository.save(receiverMailBoxes);
            }
        });
    }

    private void addNotification(CreateMessageDTO createMessageDTO) {
        Notifications notifications = new Notifications(createMessageDTO.getSender(), "Incoming message from "+ createMessageDTO.getSender(), createMessageDTO.getMsgBody());
        User user = userRepository.findByEmail(createMessageDTO.getReceiver()).orElseThrow(()-> {
            throw new UserNotFoundException("Not found");
        });
        user.getNotificationList().add(notifications);
        userRepository.save(user);
    }

}
