package com.africa.semicolon.emailService.controllers;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.dtos.UserDto;
import com.africa.semicolon.emailService.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/emailService")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody CreateMessageDTO createMessageDTO){
        return messageService.sendMessage(createMessageDTO);
    }

    @PatchMapping("/read/{messageId}")
    public void readMessage(@PathVariable String messageId){
        messageService.readMessage(messageId);
    }
}
