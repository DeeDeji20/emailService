package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.exception.MessageNotAvailable;
import com.africa.semicolon.emailService.exception.UserNotFoundException;
import com.africa.semicolon.emailService.model.*;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import com.africa.semicolon.emailService.repository.MessageRepository;
import com.africa.semicolon.emailService.repository.NotificationRepository;
import com.africa.semicolon.emailService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailBoxesRepository mailBoxesRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public String sendMessage(CreateMessageDTO createMessageDTO) {
        Message message = Message.builder()
                .sender(createMessageDTO.getSender())
                .receiver(createMessageDTO.getReceiver())
                .localDateTime(createMessageDTO.getLocalDateTime())
                .msgBody(createMessageDTO.getMsgBody())
                .build();

        messageRepository.save(message);
        addMessageToReceiversInbox(createMessageDTO, message);

        if (!message.getSender().equals("mailSender")) {
            addMessageToSendersOutbox(createMessageDTO, message);
        }
        addNotification(createMessageDTO);
        return "Message sent to " + createMessageDTO.getReceiver();
    }

    @Override
    public void readMessage(String messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(()-> {throw new MessageNotAvailable("Message is unavailable");});
        User receiver =  userRepository.findByEmail(message.getReceiver()).orElseThrow(()-> {throw new UserNotFoundException("Not found");});
        User sender =  userRepository.findByEmail(message.getSender()).orElseThrow(()-> {throw new UserNotFoundException("Not found");});

        removeNotification(message, receiver);
        receiversMailIsRead(messageId, receiver);

        sendersMailIsRead(messageId, sender);

        messageIsRead(message);
//        receiver.getNotificationList().forEach(notifications -> {
//            if (notifications.getMessage().equals(message.getMsgBody())) {
//                Notifications notification = notificationRepository.findNotificationsByMessage(message.getMsgBody()).orElseThrow(()-> new MessageNotAvailable("Not available"));
//                notification.setRead(true);
//                log.info("notification ===> {}", notification.isRead());
//                notificationRepository.save(notification);
//            }
//        });

    }

    @Override
    public List<Message> findMessage(String email, String msgBody) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("Not found"));
//        String[] splitText = msgBody.split(" ");
//        List<Message> message = messageRepository.findMessageByReceiver(email);
//        message.stream().filter((mess)->{
//            mess.getMsgBody().
//        })
        List<Message> foundMessages = new ArrayList<>();
//        List<Message>messages= messageRepository.findMessageByReceiver(email);
//        messages.forEach(message -> {
//            if (Objects.equals(message.getMsgBody(), msgBody)){
//                foundMessages.add(message);
//            }
//        });
        Optional<MailBoxes> mailBoxes = mailBoxesRepository.findById(email);
        List<MailBox>mailBox= mailBoxes.get().getMailboxes();
        mailBox.forEach(mailBox1 -> {
            if (mailBox1.getType().equals(MailBoxType.INBOX)){

            }
        });
        return foundMessages;
    }

    private void removeNotification(Message message, User receiver) {
        receiver.getNotificationList()
                .removeIf(notification -> notification.getMessage().equals(message.getMsgBody()));


    }

    private void messageIsRead(Message message) {
        message.setRead(true);
        messageRepository.save(message);
    }

    private void sendersMailIsRead(String messageId, User sender) {
        MailBoxes sendersMailBoxes = mailBoxesRepository.findById(sender.getEmail()).orElseThrow(()-> {throw new UserNotFoundException("Not found");});
        List<MailBox> mailboxOfSender= sendersMailBoxes.getMailboxes();
        Optional<MailBox> outboxes = mailboxOfSender.stream().filter(mail-> mail.getType() == MailBoxType.SENT).findFirst();
        log.info("Here===>{}", outboxes.get().getMessages().toString());
        outboxes.ifPresent(box -> box.getMessages().forEach((outbox) -> {
            if (outbox.getMsgId().equals(messageId)) outbox.setRead(true);
        }));
        mailBoxesRepository.save(sendersMailBoxes);
        userRepository.save(sender);
    }

    private void receiversMailIsRead(String messageId, User receiver) {
        MailBoxes receiversMailBoxes = mailBoxesRepository.findById(receiver.getEmail()).orElseThrow(()-> {throw new UserNotFoundException("Not found");});
        List<MailBox> mailBox= receiversMailBoxes.getMailboxes();
        Optional<MailBox> inboxes = mailBox.stream().filter(mail-> mail.getType() == MailBoxType.INBOX).findFirst();
        inboxes.ifPresent(box -> box.getMessages().forEach((inbox) -> {
            if (inbox.getMsgId().equals(messageId)) inbox.setRead(true);
        }));
        mailBoxesRepository.save(receiversMailBoxes);
        userRepository.save(receiver);
    }

    private void addMessageToSendersOutbox(CreateMessageDTO createMessageDTO, Message message) {
        MailBoxes senderMailBox = mailBoxesRepository.findById(createMessageDTO.getSender()).orElseThrow(() -> {
//            Notifications notifications = new Notifications(createMessageDTO.getSender(), "Incoming message from "+ createMessageDTO.getSender(), createMessageDTO.getMsgBody());
            throw new UserNotFoundException("not found");
        });
        Notifications notifications = new Notifications(createMessageDTO.getSender(), "Incoming message from "+ createMessageDTO.getSender(), createMessageDTO.getMsgBody());

        senderMailBox.getMailboxes().forEach((mailBox) -> {
            if (mailBox.getType().equals(MailBoxType.SENT)){
                mailBox.getMessages().add(message);
                mailBoxesRepository.save(senderMailBox);
            }
        });
    }

    private void addMessageToReceiversInbox(CreateMessageDTO createMessageDTO, Message message) {
        MailBoxes receiverMailBoxes = mailBoxesRepository.findById(createMessageDTO.getReceiver()).orElseThrow(()->new UserNotFoundException("not found"));
        receiverMailBoxes.getMailboxes().forEach((mailBox)->{
            if (mailBox.getType().equals(MailBoxType.INBOX)){
                mailBox.getMessages().add(message);
                mailBoxesRepository.save(receiverMailBoxes);
            }
        });
    }

    private void addNotification(CreateMessageDTO createMessageDTO) {
        Notifications notifications = new Notifications(createMessageDTO.getSender(), "Incoming message from "+ createMessageDTO.getSender(), createMessageDTO.getMsgBody());
        log.info("email in msg service->{}", createMessageDTO.getReceiver());
        User user = userRepository.findByEmail(createMessageDTO.getReceiver()).orElseThrow(()-> {
            throw new UserNotFoundException("Not found");
        });
        user.getNotificationList().add(notifications);
        notificationRepository.save(notifications);
        userRepository.save(user);
    }

}
