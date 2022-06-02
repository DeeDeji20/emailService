package com.africa.semicolon.emailService.controllers;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;

import com.africa.semicolon.emailService.model.Message;
import com.africa.semicolon.emailService.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/emailService/message")
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

    @PostMapping("/forward")
    public String forwardMessage(@RequestBody CreateMessageDTO createMessageDTO){
        return messageService.sendMessage(createMessageDTO);
    }

    @GetMapping("/get/{id}")
    public Message findMessage(@PathVariable String id){
        return messageService.findMessage(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@PathVariable String id){
        messageService.deleteMessage(id);
    }
}
