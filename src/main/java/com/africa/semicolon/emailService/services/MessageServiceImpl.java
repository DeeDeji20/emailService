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

        MailBoxes receiverMailBoxes = mailBoxesRepository.findById(createMessageDTO.getReceiver()).orElseThrow(()-> {throw new UserNotFoundException("not found");});
        receiverMailBoxes.getMailboxes().forEach((mailBox)->{
            if (mailBox.getType().equals(MailBoxType.INBOX)){
                mailBox.getMessages().add(message);
                mailBoxesRepository.save(receiverMailBoxes);
            }
        });

        if (!message.getSender().equals("mailSender")) {
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

        addNotification(createMessageDTO);

        return messageRepository.save(message);
    }

    private void addNotification(CreateMessageDTO createMessageDTO) {
        Notifications notifications = new Notifications(createMessageDTO.getSender(), "Incoming message from "+ createMessageDTO.getSender(), createMessageDTO.getMsgBody());
        User user = userRepository.findByEmail(createMessageDTO.getReceiver()).orElseThrow(()-> {
            throw new UserNotFoundException("Not found");
        });
        user.getNotificationList().add(notifications);
        userRepository.save(user);
    }
//
//            log.info("------> senders email from msg service{}",createMessageDTO.getReceiver());
//    User recipient = userRepository.findByEmail(createMessageDTO.getReceiver()).orElseThrow(()-> new UserNotFoundException("Receiver not found"));
//    Notifications notifications = Notifications.builder()
//            .id(message.getMsgId())
//            .senderEmail(createMessageDTO.getSender())
//            .title("New message alert")
//            .message(createMessageDTO.getMsgBody())
//            .build();
//        recipient.getNotificationList().add(notifications);
//        userRepository.save(recipient);
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
