package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.dtos.LoginRequest;
import com.africa.semicolon.emailService.dtos.UserDto;
import com.africa.semicolon.emailService.model.Notifications;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    UserDto createAccount(String email, String password);
    String login(LoginRequest loginResquest);
}
