package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.model.MailBox;
import com.africa.semicolon.emailService.model.MailBoxes;


public interface MailBoxesService {
    MailBoxes createMailBoxes(String email);
}
