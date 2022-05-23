package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.dtos.UserDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    UserDto createAccount(String email, String password);
    String sendMesage(CreateMessageDTO createMessageDTO);
}
