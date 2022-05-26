package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.dtos.LoginRequest;
import com.africa.semicolon.emailService.dtos.UserDto;
import com.africa.semicolon.emailService.model.Notifications;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    UserDto createAccount(String email, String password);
//    String sendMesage(CreateMessageDTO createMessageDTO);
    String login(LoginRequest loginResquest);
//    void getNotifications(String email, Notifications notifications);
}
