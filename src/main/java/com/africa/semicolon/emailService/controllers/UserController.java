package com.africa.semicolon.emailService.controllers;

import com.africa.semicolon.emailService.dtos.LoginRequest;
import com.africa.semicolon.emailService.dtos.UserDto;
import com.africa.semicolon.emailService.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/emailService")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestParam @Valid @NotNull @NotBlank String email, @RequestParam @Valid @NotNull @NotBlank  String password){
        log.info("email->{},password->{}", email, password);
        UserDto userDto = userService.createAccount(email, password);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String forwardMessage(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

}
