package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.model.MailBox;
import com.africa.semicolon.emailService.model.MailBoxType;
import com.africa.semicolon.emailService.model.MailBoxes;
import com.africa.semicolon.emailService.model.Message;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MailBoxServiceImpl implements MailBoxesService {
    @Autowired
    private MailBoxesRepository mailBoxesRepository;

    @Autowired
    private MessageService messageService;

    public MailBoxServiceImpl(MailBoxesRepository mailBoxesRepository) {
        this.mailBoxesRepository = mailBoxesRepository;
    }


    @Override
    public MailBoxes createMailBoxes(String email) {
        MailBoxes mailBoxes = new MailBoxes();
        mailBoxes.setEmail(email);

        MailBox inbox = new MailBox();
        inbox.setType(MailBoxType.INBOX);

        CreateMessageDTO createMessageDTO =new CreateMessageDTO("mailSender", email,"Welcome to mail service");
        log.info("---> senders email {}",email);
        Message creationMsg = messageService.sendMessage(createMessageDTO);
        inbox.setMessages(List.of(creationMsg));
        mailBoxes.getMailboxes().add(inbox);

        MailBox outbox = new MailBox();
        outbox.setType(MailBoxType.SENT);
        mailBoxes.getMailboxes().add(outbox);

        mailBoxesRepository.save(mailBoxes);
        return mailBoxes;
    }
}
