package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.dtos.UserDto;
import com.africa.semicolon.emailService.exception.EmailalreadyExistsException;
import com.africa.semicolon.emailService.exception.UserNotFoundException;
import com.africa.semicolon.emailService.model.*;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import com.africa.semicolon.emailService.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    MailBoxServiceImpl mailBoxService;

    @Autowired
    MessageServiceImpl messageService;

    @Autowired
    MailBoxesRepository mailBoxesRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper =new ModelMapper();
    }

    @Override
    public UserDto createAccount(String email, String password) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if(foundUser.isPresent()) throw new EmailalreadyExistsException("Email already exists");
        User user = User.builder()
                .email(email)
                .password(password)
                .build();
        mailBoxService.createMailBoxes(email);

        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public String sendMesage(CreateMessageDTO createMessageDTO) {
        MailBoxes receiverMailBox = mailBoxesRepository.findById(createMessageDTO.getReceiver()).orElseThrow(()-> {throw new UserNotFoundException("User not found");});
        MailBoxes senderMailBox = mailBoxesRepository.findById(createMessageDTO.getSender()).orElseThrow(()-> {throw new UserNotFoundException("User not found");});

//        Message message= messageService.sendMessage(createMessageDTO);
//        MailBox receiver = new MailBox();
//        receiver.setMessages(message);
//        receiver.setType(MailBoxType.INBOX);
//
//        MailBox sender = new MailBox();
//        sender.setMessages(message);
//        sender.setType(MailBoxType.SENT);

//        receiverMailBox.getMailboxes().add(receiver);
//        senderMailBox.getMailboxes().add(sender);

        return "Message sent";
    }


}
