package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.exception.UserNotFoundException;
import com.africa.semicolon.emailService.model.*;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import com.africa.semicolon.emailService.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    public MailBoxServiceImpl(MailBoxesRepository mailBoxesRepository) {
        this.mailBoxesRepository = mailBoxesRepository;
    }


    @Override
    public MailBoxes createMailBoxes(String email) {
        MailBoxes mailBoxes = new MailBoxes();
        mailBoxes.setEmail(email);

        MailBox inbox = new MailBox();
        inbox.setType(MailBoxType.INBOX);
        mailBoxes.getMailboxes().add(inbox);

        MailBox outbox = new MailBox();
        outbox.setType(MailBoxType.SENT);
        mailBoxes.getMailboxes().add(outbox);

        mailBoxesRepository.save(mailBoxes);
        CreateMessageDTO createMessageDTO =new CreateMessageDTO("mailSender", email,"Welcome to mail service");
        log.info("---> receivers email {}",email);
        messageService.sendMessage(createMessageDTO);

        return mailBoxes;
    }


//
//    @Override
//    public void addMessageToMailBox(CreateMessageDTO createMessageDTO) {
//        messageService.sendMessage(createMessageDTO);
//    }

    @Override
    public List<MailBox> viewAllInboxes(String email) {
        MailBoxes mailBoxes = mailBoxesRepository.findById(email).orElseThrow(()-> {throw new UserNotFoundException(("Not found"));});
        List<MailBox> allMailBox =mailBoxes.getMailboxes();
        return allMailBox.stream().filter(eachMailBox -> eachMailBox.getType().equals(MailBoxType.INBOX)).toList();
    }

    @Override
    public List<MailBox> viewallSent(String email) {
        MailBoxes mailBoxes = mailBoxesRepository.findById(email).orElseThrow(()-> {throw new UserNotFoundException(("Not found"));});
        List<MailBox> allMailBox =mailBoxes.getMailboxes();
        return allMailBox.stream().filter(eachMailBox -> eachMailBox.getType().equals(MailBoxType.SENT)).toList();
    }
}
