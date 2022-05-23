package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.model.MailBox;
import com.africa.semicolon.emailService.model.MailBoxType;
import com.africa.semicolon.emailService.model.MailBoxes;
import com.africa.semicolon.emailService.model.Message;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Message creationMsg = messageService.sendMessage(createMessageDTO);
        inbox.setMessages(List.of(creationMsg));
        mailBoxes.getMailboxes().add(inbox);

        mailBoxesRepository.save(mailBoxes);
        return mailBoxes;
    }
}
