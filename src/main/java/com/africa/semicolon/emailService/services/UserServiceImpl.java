package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.dtos.CreateMessageDTO;
import com.africa.semicolon.emailService.dtos.LoginRequest;
import com.africa.semicolon.emailService.dtos.UserDto;
import com.africa.semicolon.emailService.exception.EmailalreadyExistsException;
import com.africa.semicolon.emailService.exception.UserNotFoundException;
import com.africa.semicolon.emailService.model.*;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import com.africa.semicolon.emailService.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    NotificationServiceImpl notificationService;


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
        userRepository.save(user);
//        mailBoxService.createMailBoxes(email);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public String sendMesage(CreateMessageDTO createMessageDTO) {
        MailBoxes receiverMailBox = mailBoxesRepository.findById(createMessageDTO.getReceiver()).orElseThrow(()-> {throw new UserNotFoundException("User not found");});
        MailBoxes senderMailBox = mailBoxesRepository.findById(createMessageDTO.getSender()).orElseThrow(()-> {throw new UserNotFoundException("User not found");});

        messageService.sendMessage(createMessageDTO);
        MailBox receiver = new MailBox();
        receiver.setType(MailBoxType.INBOX);
//        receiver.setMessages(List.of(message));

        MailBox sender = new MailBox();
//        sender.setMessages(List.of(message));
        sender.setType(MailBoxType.SENT);

        if (receiver.getType() == MailBoxType.INBOX) receiverMailBox.getMailboxes().add(receiver);
        if (sender.getType() == MailBoxType.SENT) senderMailBox.getMailboxes().add(sender);

        return "Message sent";
    }

    @Override
    public String login(LoginRequest loginResquest) {
        User user = userRepository.findByEmail(loginResquest.getEmail()).orElseThrow(()-> {throw new UserNotFoundException(("Not found"));});
        if (user.getPassword().equals(loginResquest.getPassword())) user.setLoggedIn(true);
        return "User with email " + loginResquest.getEmail() + " has been logged in";
    }

    @Override
    public void getNotifications(String email, Notifications notifications) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> {throw new UserNotFoundException(("Not found"));});
        user.getNotificationList().add(notifications);
        userRepository.save(user);
    }

}
