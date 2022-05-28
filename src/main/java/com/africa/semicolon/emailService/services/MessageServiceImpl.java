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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        MailBoxes receiversMailBoxes = mailBoxesRepository.findById(receiver.getEmail()).orElseThrow(()-> {
            throw new UserNotFoundException("Not found");
        });
        List<MailBox> mailBox= receiversMailBoxes.getMailboxes();
        Optional<MailBox> inboxes = mailBox.stream().filter(mail-> mail.getType() == MailBoxType.INBOX).findFirst();
        log.info("Here===>{}", inboxes.get().getMessages());
        inboxes.ifPresent(box -> box.getMessages().forEach((inbox) -> {
            if (inbox.getMsgId().equals(messageId)) {
                inbox.setRead(true);
            }
        }));


        MailBoxes sendersMailBoxes = mailBoxesRepository.findById(sender.getEmail()).orElseThrow(()-> {
            throw new UserNotFoundException("Not found");
        });
        List<MailBox> mailboxOfSender= sendersMailBoxes.getMailboxes();
        Optional<MailBox> outboxes = mailboxOfSender.stream().filter(mail-> mail.getType() == MailBoxType.SENT).findFirst();
        log.info("Here===>{}", outboxes.get().getMessages());

        outboxes.ifPresent(box -> box.getMessages().forEach((outbox) -> {
            log.info("getttetet");
            if (outbox.getMsgId().equals(messageId)) {
                log.info("I am in outboxoooooo===>>>>>>");
                outbox.setRead(true);

                System.out.println(outbox.isRead());
            }
        }));

//        receiversMailBoxes.getMailboxes().get(0).getMessages().forEach(message1 -> {
//            System.out.println(receiversMailBoxes.getMailboxes().get(0));
//            if (message1.getMsgId().equals(messageId)) message1.setRead(true);
//        });


        mailBoxesRepository.save(receiversMailBoxes);
        message.setRead(true);
        messageRepository.save(message);
        receiver.getNotificationList()
                .removeIf(notification -> notification.getMessage().equals(message.getMsgBody()));
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
