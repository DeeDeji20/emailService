package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.model.User;
import com.africa.semicolon.emailService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.userService = new UserServiceImpl(userRepository);
    }


    @Test
    void testThatAUserCanBeCreated(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(new User());
        userService.createAccount("testemai@gmail.com", "12345");
        verify(userRepository,times(1)).findByEmail(anyString());
        verify(userRepository,times(1)).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertThat(user.getEmail()).isEqualTo("testemai@gmail.com");
        assertThat(user.getPassword()).isEqualTo("12345");

    }
}
