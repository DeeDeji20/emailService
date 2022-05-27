package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.model.MailBox;
import com.africa.semicolon.emailService.model.MailBoxes;

import java.util.List;


public interface MailBoxesService {
    MailBoxes createMailBoxes(String email);
//    void addMessageToMailBox(CreateMessageDTO createMessageDTO);
    List<MailBox> viewAllInboxes(String email);
    List<MailBox> viewallSent(String email);

}
