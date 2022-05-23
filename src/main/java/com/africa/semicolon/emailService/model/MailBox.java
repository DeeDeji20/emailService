package com.africa.semicolon.emailService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class MailBox {
    @Id
    private String email;
    private List<Message> messages;
    private MailBoxType type;

    public MailBox(String email, MailBoxType type) {
        this.email = email;
        this.type = type;
    }
}
