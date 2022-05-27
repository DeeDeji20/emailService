package com.africa.semicolon.emailService.services;

import com.africa.semicolon.emailService.model.MailBoxes;
import com.africa.semicolon.emailService.repository.MailBoxesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MailBoxServiceImplTest {


//    private MailBoxServiceImpl mailBoxServiceImpl;


    @Mock
    MailBoxesRepository mailBoxesRepository;

//    @InjectMocks
    private MailBoxesService mailBoxesService;

//    @Captor
//    ArgumentCaptor<MailBoxes> mailBoxesCaptor;

    @BeforeEach
    void setUp() {
        this.mailBoxesService = new MailBoxServiceImpl(mailBoxesRepository);
        MockitoAnnotations.openMocks(mailBoxesService);

    }

    @Test
    void testThatMailBoxcCanBeCreated(){
        MailBoxes mailBoxes = new MailBoxes();
        when(mailBoxesRepository.save(any(MailBoxes.class))).thenReturn(mailBoxes);

       var returned = mailBoxesService.createMailBoxes("deolaDeji@gmail.com");
        System.out.println(returned);

//        verify(mailBoxesRepository, times(1)).save(mailBoxesCaptor.capture());
//        MailBoxes mailBoxes=mailBoxesCaptor.getValue();
//        assertThat(mailBoxes.getMailbox().size()).isEqualTo(2);


    }
}