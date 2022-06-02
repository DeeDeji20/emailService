package com.africa.semicolon.emailService.controllers;

import com.africa.semicolon.emailService.services.MailBoxesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/emailService/mailBoxes")
@Slf4j
public class MailBoxController {
    @Autowired
    MailBoxesService mailBoxesService;

    @GetMapping("/get/inboxes/{email}")
    public ResponseEntity<?> viewAllInboxes(@PathVariable String email){
        return new ResponseEntity<>(mailBoxesService.viewAllInboxes(email), HttpStatus.OK);
    }

    @GetMapping("/get/sent/{email}")
    public ResponseEntity<?> viewAllSent(@PathVariable String email){
        return new ResponseEntity<>(mailBoxesService.viewAllSent(email), HttpStatus.OK);
    }

}
