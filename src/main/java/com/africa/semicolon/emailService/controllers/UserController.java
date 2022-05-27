package com.africa.semicolon.emailService.controllers;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.dtos.UserDto;
import com.africa.semicolon.emailService.model.Message;
import com.africa.semicolon.emailService.services.MessageService;
import com.africa.semicolon.emailService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/emailService")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestParam @Valid @NotNull @NotBlank String email, @RequestParam @Valid @NotNull @NotBlank  String password){
        UserDto userDto = userService.createAccount(email, password);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

}
