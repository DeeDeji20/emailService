package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.model.MailBox;
import com.africa.semicolon.emailService.model.MailBoxes;


public interface MailBoxesService {
    MailBoxes createMailBoxes(String email);
    void addMessageToMailBox(CreateMessageDTO createMessageDTO);
}
